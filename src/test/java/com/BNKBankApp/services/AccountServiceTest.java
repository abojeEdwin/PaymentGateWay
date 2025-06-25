package com.BNKBankApp.services;
import com.BNKBankApp.data.model.Account;
import com.BNKBankApp.data.model.PaymentDetails;
import com.BNKBankApp.data.model.PaymentRequest;
import com.BNKBankApp.data.repository.AccountRepository;
import com.BNKBankApp.data.repository.PaymentRequestRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentService paymentService;


    @Autowired
    private GenerateAccountNumberService generateAccountNumberService;

    @Autowired
    private GenerateCardNumberService generateCardNumberService;

    @Autowired
    private PaymentRequestRepo paymentRequestRepo;

    @Autowired
    private AccountRepository accountRepository;

//    @BeforeEach
//    void beforeEach(){
//        accountService.deleteAll();
//        paymentRequestRepo.deleteAll();
//    }
//
//    @AfterEach
//    void tearDown(){
//        accountService.deleteAll();
//        paymentRequestRepo.deleteAll();
//    }

    @Test
    public void testAccountServiceSaveAccount(){

        Account account =  new Account();
        account.setBalance(1500);
        account.setAccountNumber(GenerateAccountNumberService.generateAccountNumber());
        account.setTransactionPin("0000");
        Account savedAccount = accountService.saveAccount(account);

        Account account1 = new Account();
        account1.setBalance(1500.0);
        account1.setTransactionPin("0000");
        account1.setAccountNumber(GenerateAccountNumberService.generateAccountNumber());
        Account savedAccount1 = accountService.saveAccount(account1);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAccount(savedAccount);
        paymentRequest.setCardNumber(GenerateCardNumberService.generateCardNumber());
        paymentRequest.setCvv(GenerateCardNumberService.generateAccountCvv());
        paymentRequest.setAmount(1500.0);
        PaymentRequest savedPaymentRequest = paymentService.savePaymentRequest(paymentRequest);

        PaymentRequest paymentRequest1 = new PaymentRequest();
        paymentRequest1.setAccount(savedAccount1);
        paymentRequest1.setAmount(1500.0);
        paymentRequest1.setCardNumber(GenerateCardNumberService.generateCardNumber());
        paymentRequest1.setCvv(GenerateCardNumberService.generateAccountCvv());
        PaymentRequest savedPaymentRequest1 = paymentService.savePaymentRequest(paymentRequest1);

        account.setCardDetailsId(savedPaymentRequest.getId());
        accountService.saveAccount(account);
        account1.setCardDetailsId(savedPaymentRequest1.getId());
        accountService.saveAccount(account1);

        assertEquals(2,paymentRequestRepo.count());

    }

    @Test
    public void testAccountServiceTransfer(){
        accountService.transfer("0482752600","1424181203",1000.0,"0000");
        assertEquals(500,accountRepository.findByAccountNumber("0482752600").getBalance());
    }


}