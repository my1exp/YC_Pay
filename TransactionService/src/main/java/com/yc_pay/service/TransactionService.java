package com.yc_pay.service;

import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionResponse;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.util.ArrayList;

@Singleton
@Slf4j(topic = "TransactionService")
public class TransactionService {

    private final DatabaseService databaseService;

    public TransactionService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public ArrayList<TransactionResponse> getUserTransaction(String merchantId){
        return databaseService.getTransactionFromUser(merchantId);
    }

    public void createEntriesForIdentifiedTransaction(Transaction transaction){
        log.info("Try to create Entries For Transaction " + transaction.getPaid_amount_crypto()
                + " " + transaction.getCurrency_crypto());
        databaseService.createEntryForAnyTransaction(transaction.getPaid_amount_crypto(), transaction.getCurrency_crypto());

        log.info("Try to create Entries for Identified Transaction " + transaction.getPaid_amount_crypto()
                + " " + transaction.getCurrency_crypto());
        databaseService.createEntryForIdentifiedTransaction(transaction);
        log.info("Success created Entries for identified Transaction " + transaction.getPaid_amount_crypto()
                + " " + transaction.getCurrency_crypto());
    }

    public void createEntriesForUnidentifiedTransaction(double amountCrypto, String currencyCrypto) {
        log.info("Try to create Entries For Transaction " + amountCrypto + " " + currencyCrypto);
        databaseService.createEntryForAnyTransaction(amountCrypto, currencyCrypto);

        log.info("Try to create Entries For Unidentified Transaction " + amountCrypto + " " + currencyCrypto);
        databaseService.createEntryForUnidentifiedTransaction(amountCrypto, currencyCrypto);
        log.info("Success created Entries for Unidentified Transaction " + amountCrypto + " " + currencyCrypto);
    }
}

