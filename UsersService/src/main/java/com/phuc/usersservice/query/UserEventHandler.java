package com.phuc.usersservice.query;

import com.phuc.core.model.PaymentDetails;
import com.phuc.core.model.User;
import com.phuc.core.query.FetchUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserEventHandler {

    @QueryHandler
    public User findByUserPaymentDetails(FetchUserPaymentDetailsQuery query) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("Phuc")
                .validUntilMonth(12)
                .validUntilYear(30).build();

        User user = User.builder()
                .firstName("Sergey")
                .lastName("Kargopolov")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();

        return user;
    }
}
