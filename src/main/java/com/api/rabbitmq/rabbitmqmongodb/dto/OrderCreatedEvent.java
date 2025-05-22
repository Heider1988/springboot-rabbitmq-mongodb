package com.api.rabbitmq.rabbitmqmongodb.dto;

import java.util.List;

public record OrderCreatedEvent(Long codigoPedido,
                                Long codigoCliente,
                                List<OrderItemEvent> itens
) {

}
