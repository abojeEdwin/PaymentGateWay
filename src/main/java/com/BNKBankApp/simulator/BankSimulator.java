package com.BNKBankApp.simulator;
import com.BNKBankApp.data.model.PaymentResponse;
import org.springframework.stereotype.Service;
import java.util.Random;


@Service
public class BankSimulator {


    public static double TRANSACTION_FEE = 0.02;


    public boolean retrieveMoney(String cardNumber, double amount){
        if(isCardValid(cardNumber) && hasSufficientFunds(cardNumber, amount)){
            //Deduct the transaction fee from the amount
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
        //This is a simulator check
        int count = 0;
        for(int i = 0; i < cardNumber.length(); i++){
            count ++;
            if(count == 13){
                return true;
            }
        }


        return false;
    }

    private boolean hasSufficientFunds(String cardNumber, double amount){
        //Check if the card has sufficient funds
        //Retrieve the available balance for the card from a database or a payment gateway
        double availableBalance = getAvailableBalance(cardNumber);
        return availableBalance >= amount;

    }

    private double getAvailableBalance(String cardNumber){
        //Simulate the available balance as a double value
        return 1000.0;
    }

    private boolean retrieveFromCard(String cardNumber, double amount){
        //Simulate the available balance as a double value

        Random rand = new Random();
        return rand.nextBoolean();//Simulate retrieval success/failure randomly
    }


    public PaymentResponse retrieveFromBank(String cardNumber, double amount){
        BankSimulator simulator = new BankSimulator();
        PaymentResponse paymentResponse = new PaymentResponse();

        boolean transactionResult = simulator.retrieveMoney(cardNumber,amount);
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
