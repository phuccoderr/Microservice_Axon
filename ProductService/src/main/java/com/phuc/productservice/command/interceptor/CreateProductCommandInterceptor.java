package com.phuc.productservice.command.interceptor;

import com.phuc.productservice.command.CreateProductCommand;
import com.phuc.productservice.core.data.ProductLookupEntity;
import com.phuc.productservice.core.data.ProductLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductLookupRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    public CreateProductCommandInterceptor(ProductLookupRepository repository) {
        this.repository = repository;
    }


    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            @Nonnull List<? extends CommandMessage<?>> messages) {
        return (index,command) -> {

            LOGGER.info("Intercepted command: " + command.getPayloadType());

            if(CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                ProductLookupEntity productLookupEntity = repository.findByProductIdOrTitle(
                        createProductCommand.getProductId(),
                        createProductCommand.getTitle());

                if (productLookupEntity != null) {
                    throw new IllegalArgumentException("Product is already");
                }
            }

            return command;
        };
    }
}
