package com.BNKBankApp.data.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@Document(collection = "PaymentResponse")
public class PaymentResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double originalAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double totalAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String transactionId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currency;

    public PaymentResponse(String message, double originalAmount, String status, double totalAmount, String transactionId, String currency) {
        this.message = message;
        this.originalAmount = originalAmount;
        this.status = status;
        this.totalAmount = totalAmount;
        this.transactionId = transactionId;
        this.currency = currency;
    }

}
