package com.api.rabbitmq.rabbitmqmongodb.listener;

import com.api.rabbitmq.rabbitmqmongodb.config.RabbitMqConfig;
import com.api.rabbitmq.rabbitmqmongodb.dto.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME_1)
    public void listenOrderCreated(Message<OrderCreatedEvent> message) {
        logger.info("Message received: {}", message);
    }

}
