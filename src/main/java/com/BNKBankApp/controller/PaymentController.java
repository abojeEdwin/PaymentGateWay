package com.BNKBankApp.controller;
import com.BNKBankApp.data.model.PaymentDetails;
import com.BNKBankApp.data.model.PaymentRequest;
import com.BNKBankApp.data.model.PaymentResponse;
import com.BNKBankApp.exception.PaymentDetailsNotFoundException;
import com.BNKBankApp.services.PaymentService;
import com.BNKBankApp.services.PaymentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentTransactionService paymentTransactionService;

    @PostMapping("/processPayment")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest){
        return  ResponseEntity.ok(paymentService.processPayment(paymentRequest));
    }

    @GetMapping("/transactionId")
    @ExceptionHandler(PaymentDetailsNotFoundException.class)
    public ResponseEntity <PaymentDetails> getPaymentDetails(@RequestBody String transactionId) throws Exception {
          PaymentDetails paymentDetails = paymentTransactionService.getPaymentDetails(transactionId);
            if(paymentDetails != null ){
                return ResponseEntity.ok(paymentDetails);
            }else throw new PaymentDetailsNotFoundException("Payment Details Not Found");
        }

    }

