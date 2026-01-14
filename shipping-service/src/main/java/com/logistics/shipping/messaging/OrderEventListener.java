package com.logistics.shipping.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderEventListener {

    @Bean
    public Consumer<String> order() {
        return message -> {
            System.out.println("[DATA CIRCULATION] Received order event: " + message);
        };
    }
}
