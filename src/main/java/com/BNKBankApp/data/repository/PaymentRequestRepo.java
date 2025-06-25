package com.BNKBankApp.data.repository;
import com.BNKBankApp.data.model.PaymentRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRequestRepo extends MongoRepository<PaymentRequest, String> {

    Optional<PaymentRequest> findByCardNumber(String cardNumber);


}
