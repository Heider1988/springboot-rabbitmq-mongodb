package com.api.rabbitmq.rabbitmqmongodb.controller;

import com.api.rabbitmq.rabbitmqmongodb.doc.OrderControllerDoc;
import com.api.rabbitmq.rabbitmqmongodb.dto.response.ApiResponse;
import com.api.rabbitmq.rabbitmqmongodb.dto.response.OrderResponse;
import com.api.rabbitmq.rabbitmqmongodb.dto.response.PaginationResponse;
import com.api.rabbitmq.rabbitmqmongodb.service.OrderService;
import com.api.rabbitmq.rabbitmqmongodb.service.impl.OrderServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController implements OrderControllerDoc {

    final OrderService orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customs/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(
        @RequestParam(name = "page", defaultValue = "0") Integer page,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, 
        @PathVariable Long customerId) {

        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        var totalOrders = orderService.totalOnOrdersByCustomerId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));

    }


}
