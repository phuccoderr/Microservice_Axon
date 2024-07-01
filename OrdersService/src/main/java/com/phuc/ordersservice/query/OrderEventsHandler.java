package com.phuc.ordersservice.query;

import com.phuc.ordersservice.core.data.OrderEntity;
import com.phuc.ordersservice.core.data.OrderRepository;
import com.phuc.ordersservice.core.event.OrderCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {
    private final OrderRepository repository;

    public OrderEventsHandler(OrderRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event,orderEntity);

        try {
            repository.save(orderEntity);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }
}
