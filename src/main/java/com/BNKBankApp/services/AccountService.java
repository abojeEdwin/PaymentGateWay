package com.BNKBankApp.services;
import com.BNKBankApp.data.model.Account;
import com.BNKBankApp.data.model.PaymentRequest;
import com.BNKBankApp.data.model.Transaction;
import com.BNKBankApp.data.repository.AccountRepository;
import com.BNKBankApp.data.repository.PaymentRequestRepo;
import com.BNKBankApp.data.repository.TransactionRepository;
import com.BNKBankApp.exception.AccountNotFoundException;
import com.BNKBankApp.exception.InvalidBalanceException;
import com.BNKBankApp.exception.InvalidTransactionPinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class AccountService {

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private TransactionRepository transactionRepository;

        @Autowired
        private PaymentRequestRepo paymentRequestRepository;

        private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        public void deleteAll() {
            accountRepository.deleteAll();
        }

        public Account saveAccount(Account account) {
            return accountRepository.save(account);
        }

        public Long count(){
            return accountRepository.count();
        }

        public Account findAccountByCardNumber(String cardNumber){
            return paymentRequestRepository.findByCardNumber(cardNumber).map(PaymentRequest::getAccount)
                    .orElseThrow(() -> new AccountNotFoundException(cardNumber));
        }

        public void transfer(String fromAccountNumber,String toAccountNumber, double amount,String senderTransactionPin) {

            Account from = accountRepository.findByAccountNumber(fromAccountNumber);
            Account to = accountRepository.findByAccountNumber(toAccountNumber);

            if(!verifyPassword(from.getTransactionPin(),(senderTransactionPin))) {
                throw new InvalidTransactionPinException("Invalid transaction pin, please try again");
            }
            if(from.getBalance() >= amount) {
                from.setBalance(from.getBalance() - amount);
                to.setBalance(to.getBalance() + amount);
                accountRepository.save(from);
                accountRepository.save(to);
            }
            else{throw new InvalidBalanceException("Insufficient account balance");}

            Transaction transaction = new Transaction();
            transaction.setTransactionType("transfer_out");
            transaction.setAmount(amount);
            transaction.setRecipient(fromAccountNumber);
            String accountBalance = String.valueOf(from.getBalance());
            transaction.setBalance(accountBalance);
            transaction.setDescription("Transfer to " + toAccountNumber);
            Instant instant = Instant.now();
            transaction.setTimestamp(instant);
            transactionRepository.save(transaction);

            Transaction recieverTransaction = new Transaction();
            recieverTransaction.setTransactionType("transfer_in");
            recieverTransaction.setAmount(amount);
            recieverTransaction.setRecipient(toAccountNumber);
            recieverTransaction.setTimestamp(instant);
            recieverTransaction.setDescription("Transfer from " + fromAccountNumber);
            String recieverAccountBalance = String.valueOf(to.getBalance());
            recieverTransaction.setBalance(recieverAccountBalance);
            transactionRepository.save(recieverTransaction);

        }


        public static boolean verifyPassword(String hashedPassword, String inputPassword) {
            if (hashedPassword == null || hashedPassword.isEmpty() ||
                    inputPassword == null || inputPassword.isEmpty()) {
                return false;
            }

            try {
                return passwordEncoder.matches(inputPassword, hashedPassword);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

    }


