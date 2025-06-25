package com.BNKBankApp.data.model;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Document(collection = "Account")
@Component
@NoArgsConstructor
public class Account {

    @Id
    private String id;

    @NotNull(message = "This field cannot be empty")
    private double balance;

    @NotNull(message = "This field cannot be empty")
    private String accountNumber;

    @NotNull(message = "This field cannot be empty")
    private String cardDetailsId;

    @NotNull(message = "This field cannot be empty")
    private String transactionPin;

    public Account(String id, double balance, String accountNumber, String cardDetailsId,String transactionPin) {
        this.id = id;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.cardDetailsId = cardDetailsId;
        this.transactionPin = transactionPin;
    }

}
