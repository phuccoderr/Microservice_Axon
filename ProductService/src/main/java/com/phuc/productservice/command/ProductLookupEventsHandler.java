package com.phuc.productservice.command;

import com.phuc.productservice.core.data.ProductLookupEntity;
import com.phuc.productservice.core.data.ProductLookupRepository;
import com.phuc.productservice.core.event.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

    private final ProductLookupRepository repository;

    public ProductLookupEventsHandler(ProductLookupRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(),event.getTitle());
        repository.save(productLookupEntity);
    }
}
