package com.api.rabbitmq.rabbitmqmongodb.listener;

import com.api.rabbitmq.rabbitmqmongodb.config.RabbitMqConfig;
import com.api.rabbitmq.rabbitmqmongodb.dto.OrderCreatedEvent;
import com.api.rabbitmq.rabbitmqmongodb.service.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderServiceImpl orderService;

    public OrderCreatedListener(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME_1)
    public void listenOrderCreated(Message<OrderCreatedEvent> message) {
        logger.info("Message received: {}", message);
        orderService.saveOrder(message.getPayload());
    }

}
