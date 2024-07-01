package com.phuc.productservice.command.rest;

import com.phuc.productservice.command.CreateProductCommand;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {

    private final Environment environment;
    private final CommandGateway commandGateway;


    public ProductsCommandController(Environment environment, CommandGateway commandGateway) {
        this.environment = environment;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@RequestBody @Valid CreateProductRestModel createProductRestModel) {

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .title(createProductRestModel.getTitle())
                .quantity(createProductRestModel.getQuantity())
                .productId(UUID.randomUUID().toString())
                .build();
        String returnValue;
        returnValue = commandGateway.sendAndWait(createProductCommand);
//        try {
//            returnValue = commandGateway.sendAndWait(createProductCommand);
//        } catch (Exception ex) {
//            returnValue = ex.getLocalizedMessage();
//        }
        return returnValue;
    }
}
