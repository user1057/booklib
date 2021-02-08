package com.jhipster.booklib.service.custom;

import com.jhipster.booklib.config.KafkaProperties;
import com.jhipster.booklib.domain.Book;
import com.jhipster.booklib.domain.BookContent;
import com.jhipster.booklib.domain.PageContent;
import com.jhipster.booklib.repository.BookContentRepository;
import com.jhipster.booklib.repository.BookRepository;
import com.jhipster.booklib.repository.PageContentRepository;
import com.jhipster.booklib.service.BookService;
import com.jhipster.booklib.service.dto.BookCriteria;
import com.jhipster.booklib.service.dto.BookDTO;
import com.spire.pdf.PdfDocument;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class BookProcessing {
    private final Logger log = LoggerFactory.getLogger(BookProcessing.class);

    private final KafkaProperties kafkaProperties;
    private KafkaConsumer<String, String> kafkaConsumer;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final BookRepository bookRepository;
    private final BookCustomService bookCustomService;
    private final BookExtendedQueryService bookExtendedQueryService;
    private final PageContentRepository pageContentRepository;
    private final BookContentRepository bookContentRepository;
    private final BookService bookService;

    public BookProcessing(KafkaProperties kafkaProperties, BookRepository bookRepository, BookCustomService bookCustomService, BookExtendedQueryService bookExtendedQueryService, PageContentRepository pageContentRepository, BookContentRepository bookContentRepository, BookService bookService) {
        this.kafkaProperties = kafkaProperties;
        this.bookRepository = bookRepository;
        this.bookCustomService = bookCustomService;

        this.bookExtendedQueryService = bookExtendedQueryService;
        this.pageContentRepository = pageContentRepository;
        this.bookContentRepository = bookContentRepository;
        this.bookService = bookService;
    }

    @PostConstruct
    public void start() {
        this.kafkaConsumer = new KafkaConsumer<>(kafkaProperties.getConsumerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        kafkaConsumer.subscribe(Collections.singletonList(KafkaProperties.bookTopic));

        executorService.execute(() -> {
            String threadName = Thread.currentThread().getName();
            try {
                while (!closed.get()) {
                    log.info("[{}] Pooling with consumer...", threadName);
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
                    for (ConsumerRecord<String, String> record : records) {
                        log.info("[{}] Book isbn to process: {}", threadName, record.value());
                        String isbn = record.value();

                        BookCriteria criteria = BookExtendedQueryService.criteriaIsbn(isbn);
                        List<Book> books = bookExtendedQueryService.findBook(criteria);

                        if(books.size() == 1){
                            try {
                                log.info("[{}] Valid - processing book with isbn: {}", threadName, isbn);
                                Long bookId = books.get(0).getId();
                                byte[] bookData = bookCustomService.findBookContentByBookId(bookId);
                                PdfDocument doc = new PdfDocument();
                                doc.loadFromBytes(bookData);
                                BufferedImage image;
                                for (int i = 0; i < doc.getPages().getCount(); i++) {
                                    log.info("[{}] Processing page {} for isbn {}", threadName, i, isbn);

                                    image = doc.saveAsImage(i);

                                    int w = image.getWidth();
                                    int h = image.getHeight();
                                    BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                                    int[] rgb = image.getRGB(0, 0, w, h, null, 0, w);
                                    newImage.setRGB(0, 0, w, h, rgb, 0, w);

                                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                                    try {
                                        ImageIO.write(newImage, "jpg", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    PageContent pageContent = new PageContent();
                                    pageContent.setIsbn(isbn);
                                    pageContent.setPage(i);
                                    pageContent.setData(os.toByteArray());
                                    pageContent.setDataContentType(MediaType.IMAGE_JPEG_VALUE);

                                    pageContentRepository.save(pageContent);
                                }
                                Integer pageCount = doc.getPages().getCount();
                                doc.close();

                                bookCustomService.updateBookStatus(bookId, true, pageCount);
                                log.info("[{}] Book processing done for isbn: {}", threadName, isbn);
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        }else {
                            log.error("[{}] Multiple or none books with ISBN found", threadName);
                        }
                    }
                    kafkaConsumer.commitSync();
                }
            } catch (WakeupException e) {
                // Ignore exception if closing
                if (!closed.get()) throw e;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                log.info("[{}] Kafka consumer close", threadName);
                kafkaConsumer.close();
            }
        });

        executorService.shutdown();
    }

    public void shutdown() {
        log.info("Shutdown kafka consumer");
        closed.set(true);
        kafkaConsumer.wakeup();
    }
}
