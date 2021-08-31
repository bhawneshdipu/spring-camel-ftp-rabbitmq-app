package com.dipu.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring-apache-camel-context.xml")
public class CamelRabbitMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamelRabbitMQApplication.class, args);
    }
}
