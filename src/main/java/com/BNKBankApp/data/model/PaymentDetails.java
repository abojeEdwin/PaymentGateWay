package com.BNKBankApp.data.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@Document(collection = "PaymentDetails")
public class PaymentDetails {
    private String transactionId;
    private String cardNumber;
    private double originalAmount;
    private double totalAmount;


    public PaymentDetails( String transactionId,String cardNumber, double originalAmount, double totalAmount){
        this.transactionId = transactionId;
        this.cardNumber = cardNumber;
        this.originalAmount = originalAmount;
        this.totalAmount = totalAmount;
    }

}
