package com.api.rabbitmq.rabbitmqmongodb.service.impl;

import com.api.rabbitmq.rabbitmqmongodb.domain.entity.OrderEntity;
import com.api.rabbitmq.rabbitmqmongodb.domain.entity.OrderItemEntity;
import com.api.rabbitmq.rabbitmqmongodb.domain.repository.OrderRepository;
import com.api.rabbitmq.rabbitmqmongodb.dto.OrderCreatedEvent;
import com.api.rabbitmq.rabbitmqmongodb.dto.response.OrderResponse;
import com.api.rabbitmq.rabbitmqmongodb.service.OrderService;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderServiceImpl implements OrderService {

    public static final String TOTAL = "total";
    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        var orders = orderRepository.findAllByCustomerId(customerId, pageRequest);

        return orders.map(OrderResponse::fromEntity);

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

    @Override
    public BigDecimal totalOnOrdersByCustomerId(Long customerId) {
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum(TOTAL).as(TOTAL)
        );

        var response = mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);

        return Optional.ofNullable(response.getUniqueMappedResult())
                .map(doc -> doc.get(TOTAL))
                .map(Object::toString)
                .map(BigDecimal::new)
                .orElse(BigDecimal.ZERO);
    }


}
