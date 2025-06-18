package com.BNKBankApp.data.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Getter
@Setter
@Component
public class PaymentRequest {

    private String cardNumber;
    private double amount;
    private String cvv;
    private String expiryDate;
    private String currency;

    public PaymentRequest(String cardNumber, double amount, String cvv, String expiryDate, String currency) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.currency = currency;
    }


}
