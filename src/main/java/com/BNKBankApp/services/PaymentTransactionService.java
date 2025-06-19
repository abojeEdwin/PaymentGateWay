package com.BNKBankApp.services;
import com.BNKBankApp.data.model.PaymentDetails;
import com.BNKBankApp.exception.PaymentDetailsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentTransactionService {

    private Map<String , PaymentDetails> paymentDetailsMap;

    public PaymentTransactionService(Map<String, PaymentDetails> paymentDetailsMap) {
        this.paymentDetailsMap = new HashMap<>();
    }

    public void storePaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetailsMap.put(paymentDetails.getTransactionId(), paymentDetails);
    }

    public PaymentDetails getPaymentDetails(String transactionId)throws Exception{
        if(paymentDetailsMap.containsKey(transactionId)){
            return paymentDetailsMap.get(transactionId);
        }else throw new PaymentDetailsNotFoundException("Payment details not found for transaction: " + transactionId);
    }

    public boolean isTransactionDuplicate(String transactionId, String cardNumber, double amount) {
        for (PaymentDetails paymentDetails : paymentDetailsMap.values()) {
            if (paymentDetails.getTransactionId().equals(transactionId) &&
                    paymentDetails.getCardNumber().equals(cardNumber) &&
                    paymentDetails.getOriginalAmount() == amount) {
                return true;
            }
        }
        return false;
    }


}
