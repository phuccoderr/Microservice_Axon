package com.phuc.productservice.command.rest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreateProductRestModel {
//    @NotBlank(message = "Product title is a required field")
    private String title;

    @Min(value = 1, message = "Price cannot be lower than 1")
    private BigDecimal price;

    @Min(value = 1, message = "Quantity cannot be lower than 1")
    private Integer quantity;

}
