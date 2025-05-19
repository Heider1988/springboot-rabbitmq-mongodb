package com.api.rabbitmq.rabbitmqmongodb.service.impl;

import com.api.rabbitmq.rabbitmqmongodb.domain.entity.OrderEntity;
import com.api.rabbitmq.rabbitmqmongodb.domain.entity.OrderItemEntity;
import com.api.rabbitmq.rabbitmqmongodb.domain.repository.OrderRepository;
import com.api.rabbitmq.rabbitmqmongodb.dto.OrderCreatedEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(OrderCreatedEvent event) {
        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setTotal(getTotal(event));

        getOrderItens(event, entity);


        orderRepository.save(entity);
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens().stream().
                map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade()))).
                reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void getOrderItens(OrderCreatedEvent event, OrderEntity entity) {
        entity.setItem(event.itens().stream().
                map(item -> new OrderItemEntity(item.produto(), item.quantidade(), item.preco())).toList());
    }

}
