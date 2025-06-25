package com.BNKBankApp.simulator;
import com.BNKBankApp.data.model.Account;
import com.BNKBankApp.data.model.PaymentRequest;
import com.BNKBankApp.data.model.PaymentResponse;
import com.BNKBankApp.data.repository.PaymentRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
public class BankSimulator {

    @Autowired
    private PaymentRequestRepo paymentRequestRepo;


    public static double TRANSACTION_FEE = 0.02;

    public BankSimulator(PaymentRequestRepo paymentRequestRepo) {
        this.paymentRequestRepo = paymentRequestRepo;
    }


    public boolean retrieveMoney(String cardNumber, double amount){
        if(isCardValid(cardNumber) && hasSufficientFunds(cardNumber, amount)){
            double fee = amount * TRANSACTION_FEE;
            double totalAmount = amount + fee;

            //Simulate money retrieval from the customer's card
            if(retrieveFromCard(cardNumber, totalAmount)){
                //Transaction Successful
                return true;
            }
        }
        return false;

    }
    private boolean isCardValid(String cardNumber){
        Optional<PaymentRequest> foundAccount = paymentRequestRepo.findByCardNumber(cardNumber);
        if(foundAccount.get().getAccount() == null){
            return false;
        }
        return true;
    }

    private boolean hasSufficientFunds(String cardNumber, double amount){
        Optional<PaymentRequest> foundAccount = paymentRequestRepo.findByCardNumber(cardNumber);
        if(foundAccount.get().getAccount().getBalance() <= amount){
            return false;
        }
        return true;

    }

    private double getAvailableBalance(String cardNumber){
        Optional<PaymentRequest> foundAccount = paymentRequestRepo.findByCardNumber(cardNumber);
        return foundAccount.get().getAccount().getBalance();
    }

    private boolean retrieveFromCard(String cardNumber, double amount){
        Optional<PaymentRequest> foundAccount = paymentRequestRepo.findByCardNumber(cardNumber);
        if(foundAccount.get().getAccount() == null){
            return false;
        }
        return true;

    }


    public PaymentResponse retrieveFromBank(String cardNumber, double amount){

        PaymentResponse paymentResponse = new PaymentResponse();
        boolean transactionResult = retrieveMoney(cardNumber,amount);
        if(transactionResult){
            paymentResponse.setMessage("Money retrieved successfully");
            paymentResponse.setStatus("Success");
        }else{
            paymentResponse.setStatus("Failed");
            paymentResponse.setMessage("Failed to retrieve money. Please check your card details or  balance");
        }
        return paymentResponse;
    }

}
