package com.BNKBankApp.data.model;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Getter
@Setter
@Component
@Document(collection = "PaymentRequest")
public class PaymentRequest {

    @Id
    private String id;

    @NotNull(message = "This field cannot be empty")
    private String cardNumber;

    @NotNull(message = "This field cannot be empty")
    private double amount;

    @NotNull(message = "This field cannot be empty")
    private String cvv;

    @NotNull(message = "This field cannot be empty")
    private String expiryDate;

    @NotNull(message = "This field cannot be empty")
    private String currency;

    @NotNull(message = "This field cannot be empty")
    private String transactionId;

    private Account account;

    public PaymentRequest(String cardNumber, double amount, String cvv, String expiryDate, String currency, Account account, String transactionId) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.account = account;
        this.currency = currency;
        this.transactionId = transactionId;

    }

}