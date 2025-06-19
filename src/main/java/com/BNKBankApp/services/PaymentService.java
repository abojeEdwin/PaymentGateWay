package com.BNKBankApp.services;
import com.BNKBankApp.simulator.BankSimulator;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final BankSimulator bankSimulator;
    private  PaymentTransactionService paymentTransactionService;


}
