package com.api.rabbitmq.rabbitmqmongodb.domain.repository;

import com.api.rabbitmq.rabbitmqmongodb.domain.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
}
