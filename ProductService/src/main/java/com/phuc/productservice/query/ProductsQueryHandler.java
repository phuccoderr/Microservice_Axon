package com.phuc.productservice.query;

import com.phuc.productservice.core.data.ProductEntity;
import com.phuc.productservice.core.data.ProductRepository;
import com.phuc.productservice.query.rest.ProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductsQueryHandler {
    private final ProductRepository repository;

    public ProductsQueryHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery query) {
        List<ProductRestModel> productRest = new ArrayList<>();
        List<ProductEntity> storedProducts = repository.findAll();

        storedProducts.forEach(p -> {
            ProductRestModel productRestModel = new ProductRestModel();
            BeanUtils.copyProperties(p,productRestModel);
            productRest.add(productRestModel);
        });

        return productRest;
    }
}
