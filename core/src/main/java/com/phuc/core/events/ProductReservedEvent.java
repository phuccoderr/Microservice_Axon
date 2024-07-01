package com.phuc.core.events;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductReservedEvent {
    private final String productId;
    private final int quantity;
    private final String orderId;
    private final String userId;
}
