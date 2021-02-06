package com.jhipster.booklib.web.rest.custom;

import com.jhipster.booklib.web.api.UrlApiDelegate;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class UrlApiDelegateImpl implements UrlApiDelegate {
    private final Logger log = LoggerFactory.getLogger(UrlApiDelegateImpl.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NativeWebRequest request;

    public UrlApiDelegateImpl(NativeWebRequest request){
        this.request = request;
    }

    @Override
    public ResponseEntity<Resource> getPageImage(String isbn, MultipartFile file) {


        //provjeri isbn
        //samo spremi u bazu
        //nakon spremanja u bazu posalji u kafku isbn knjige koju treba obradit

        PdfDocument doc = new PdfDocument();
        try {
            doc.loadFromStream(file.getInputStream()); // .loadFromFile("Sample.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage image = doc.saveAsImage(0);


        /*File filetemp = new File( String.format("ToImage-img-%d.png", 0));
        try {
            ImageIO.write(image, "JPG", filetemp);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int[] rgb = image.getRGB(0, 0, w, h, null, 0, w);
        newImage.setRGB(0, 0, w, h, rgb, 0, w);


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(newImage,  "jpg", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = new ByteArrayInputStream(os.toByteArray());

/*        //save every PDF to .png image
        BufferedImage image;
        for (int i = 0; i < doc.getPages().getCount(); i++) {
            image = doc.saveAsImage(i);
            File filetemp = new File( String.format("ToImage-img-%d.png", i));
            ImageIO.write(image, "PNG", file);
        }*/

        doc.close();

        InputStreamResource resource = new InputStreamResource(is);
/*        return ResponseEntity.ok()
            //.headers(headers)
            .contentLength(file.getSize())
            .contentType(MediaType.IMAGE_JPEG)
            .body(resource);*/

        HttpHeaders headers = new HttpHeaders();
        //Resource resource = new ServletContextResource(servletContext, "/WEB-INF/images/image-example.jpg");
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
}
