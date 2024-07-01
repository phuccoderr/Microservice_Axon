package com.phuc.ordersservice.saga;

import com.phuc.core.commands.ReserveProductCommand;
import com.phuc.core.events.ProductReservedEvent;
import com.phuc.core.model.User;
import com.phuc.core.query.FetchUserPaymentDetailsQuery;
import com.phuc.ordersservice.core.event.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@Saga
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();

        LOGGER.info("Order Created Event handle for orderId: " + reserveProductCommand.getOrderId());

        commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {

            @Override
            public void onResult(@Nonnull CommandMessage<? extends ReserveProductCommand> commandMessage,
                                 @Nonnull CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()) {
                    // Start a compensating transaction
                }
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        // Process user payment
        LOGGER.info("Product ReservedEvent is called for product Id: " + productReservedEvent.getProductId() +
                " and Order Id: " + productReservedEvent.getOrderId());

        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new
                FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());

        User userPaymentDetails;
        try {
            userPaymentDetails = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());

            // Start compensating transaction
            return;
        }

        if (userPaymentDetails == null) {
            // Start compensating transaction
            return;
        }

        LOGGER.info("Successfully fetched user payment details for user " + userPaymentDetails.getFirstName());
    }
}
