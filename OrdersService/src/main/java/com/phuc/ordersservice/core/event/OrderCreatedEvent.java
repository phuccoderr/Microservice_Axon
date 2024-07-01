package com.phuc.ordersservice.core.event;

import com.phuc.ordersservice.core.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    public  String orderId;
    private  String userId;
    private  String productId;
    private  int quantity;
    private  String addressId;
    private OrderStatus orderStatus;
}
