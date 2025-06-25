package com.BNKBankApp.services;
import com.BNKBankApp.data.model.PaymentDetails;
import com.BNKBankApp.data.model.PaymentRequest;
import com.BNKBankApp.data.model.PaymentResponse;
import com.BNKBankApp.data.repository.PaymentRequestRepo;
import com.BNKBankApp.simulator.BankSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    PaymentRequestRepo paymentRequestRepo;

    private BankSimulator bankSimulator;
    private  PaymentTransactionService paymentTransactionService;


    public PaymentService(BankSimulator bankSimulator, PaymentTransactionService paymentTransactionService) {
        this.bankSimulator = bankSimulator;
        this.paymentTransactionService = paymentTransactionService;
    }


    public boolean validatePaymentRequest(PaymentRequest paymentRequest) {
        if( paymentRequest.getCardNumber().length() < 16 &&
                paymentRequest.getCurrency()  == null &&
                paymentRequest.getCvv() == null){
            return false;
        }
        return true;
    }

    public PaymentResponse processPayment (PaymentRequest paymentRequest){
        PaymentResponse paymentResponse = new PaymentResponse();

        if(!validatePaymentRequest(paymentRequest)){
            paymentResponse.setStatus("Failed");
            paymentResponse.setMessage("Invalid Payment Request");
            return paymentResponse;
        }
        double totalAmount = paymentRequest.getAmount() + BankSimulator.TRANSACTION_FEE;

//        if(paymentTransactionService.isTransactionDuplicate(paymentRequest.getTransactionId(), paymentRequest.getCardNumber(), paymentRequest.getAmount())){
//            paymentResponse.setStatus("Duplicate transaction with : " + paymentRequest.getTransactionId());
//            paymentResponse.setMessage("Duplicate transaction");
//            return paymentResponse;
//        }

        paymentResponse = bankSimulator.retrieveFromBank(paymentRequest.getCardNumber(), paymentRequest.getAmount());
        paymentResponse.setTransactionId(paymentRequest.getTransactionId());
        paymentResponse.setTotalAmount(totalAmount);
        paymentResponse.setCurrency(paymentRequest.getCurrency());
        paymentResponse.setOriginalAmount(paymentRequest.getAmount());

        PaymentDetails paymentDetails = new PaymentDetails(paymentRequest.getTransactionId(),paymentRequest.getCardNumber(),paymentRequest.getAmount(),totalAmount);
        paymentTransactionService.storePaymentDetails(paymentDetails);
        return paymentResponse;
    }

    public PaymentRequest savePaymentRequest(PaymentRequest paymentRequest){
        return paymentRequestRepo.save(paymentRequest);
    }
}
