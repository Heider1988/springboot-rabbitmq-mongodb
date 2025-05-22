package com.api.rabbitmq.rabbitmqmongodb.doc;

import com.api.rabbitmq.rabbitmqmongodb.dto.response.ApiResponse;
import com.api.rabbitmq.rabbitmqmongodb.dto.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Orders", description = "Order management APIs")
public interface OrderControllerDoc {

    @Operation(
        summary = "List orders by customer ID",
        description = "Retrieves a paginated list of orders for a specific customer"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved orders",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", 
            description = "Customer not found"
        )
    })
    ResponseEntity<ApiResponse<OrderResponse>> listOrders(
        @Parameter(description = "Page number (zero-based)", example = "0") Integer page,
        @Parameter(description = "Number of items per page", example = "10") Integer pageSize,
        @Parameter(description = "ID of the customer to retrieve orders for", required = true, example = "1") Long customerId
    );
}
