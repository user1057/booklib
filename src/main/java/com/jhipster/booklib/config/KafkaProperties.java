package com.jhipster.booklib.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private String bootStrapServers = "localhost:9092";

    public static String bookTopic = "BOOK_TOPIC";

    private Map<String, String> consumer = new HashMap<>();

    private Map<String, String> producer = new HashMap<>();

    public String getBootStrapServers() {
        return bootStrapServers;
    }

    public void setBootStrapServers(String bootStrapServers) {
        this.bootStrapServers = bootStrapServers;
    }

    public Map<String, Object> getConsumerProps() {
        Map<String, Object> properties = new HashMap<>(this.consumer);
        if (!properties.containsKey("bootstrap.servers")) {
            properties.put("bootstrap.servers", this.bootStrapServers);
            properties.put("max.poll.interval.ms", 300000);
            properties.put("max.poll.records", 1);
        }
        return properties;
    }

    public void setConsumer(Map<String, String> consumer) {
        this.consumer = consumer;
    }

    public Map<String, Object> getProducerProps() {
        Map<String, Object> properties = new HashMap<>(this.producer);
        if (!properties.containsKey("bootstrap.servers")) {
            properties.put("bootstrap.servers", this.bootStrapServers);
        }
        return properties;
    }

    public void setProducer(Map<String, String> producer) {
        this.producer = producer;
    }

}
