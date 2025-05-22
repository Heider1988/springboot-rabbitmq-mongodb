package com.api.rabbitmq.rabbitmqmongodb.config;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

     public static final String QUEUE_NAME_1 = "myFirstQueue";


     @Bean
     Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
          return new Jackson2JsonMessageConverter();
     }

     @Bean
     public Declarable orderCreatedQueue() {
          return new Queue(QUEUE_NAME_1);
     }

}
