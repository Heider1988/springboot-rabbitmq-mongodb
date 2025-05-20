package com.api.rabbitmq.rabbitmqmongodb.service;

import com.api.rabbitmq.rabbitmqmongodb.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

public interface OrderService {
    Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest);
    BigDecimal totalOnOrdersByCustomerId(Long customerId);
}
