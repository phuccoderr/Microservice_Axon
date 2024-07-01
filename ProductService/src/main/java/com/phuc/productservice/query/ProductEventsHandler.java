package com.phuc.productservice.query;

import com.phuc.core.events.ProductReservedEvent;
import com.phuc.productservice.core.data.ProductEntity;
import com.phuc.productservice.core.data.ProductRepository;
import com.phuc.productservice.core.event.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private final ProductRepository repository;

    public ProductEventsHandler(ProductRepository repository) {
        this.repository = repository;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsHandler.class);

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception ex) {
        // Log error message
    }

    // chi xu ly trong class nay neu co exception, khac voi controller advice la global
    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException ex) {
        // Log error message
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event,productEntity);

        try {
            repository.save(productEntity);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) {
        ProductEntity productEntity = repository.findByProductId(productReservedEvent.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
        repository.save(productEntity);

        LOGGER.info("ProductReservedEvent is called for productId: " + productReservedEvent.getProductId() +
                " and orderId: " + productReservedEvent.getOrderId());
    }
}
