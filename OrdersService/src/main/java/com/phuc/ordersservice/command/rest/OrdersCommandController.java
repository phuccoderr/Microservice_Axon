package com.phuc.ordersservice.command.rest;

import com.phuc.ordersservice.command.CreateOrderCommand;
import com.phuc.ordersservice.core.model.OrderStatus;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {

    private final CommandGateway commandGateway;

    public OrdersCommandController(Environment environment, CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@RequestBody @Valid CreateOrderRestModel createOrderRestModel) {
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .productId(createOrderRestModel.getProductId())
                .quantity(createOrderRestModel.getQuantity())
                .addressId(createOrderRestModel.getAddressId())
                .orderId(UUID.randomUUID().toString())
                .userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
                .orderStatus(OrderStatus.CREATED).build();

        return commandGateway.sendAndWait(createOrderCommand);
    }
}
