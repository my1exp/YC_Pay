package com.yc_pay.service;

import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionResponse;
import jakarta.inject.Singleton;

import java.util.ArrayList;

@Singleton
public class TransactionService {

    private final DatabaseService databaseService;

    public TransactionService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public ArrayList<TransactionResponse> getUserTransaction(String merchantId){
        return databaseService.getTransactionFromUser(merchantId);
    }

    public String createTransaction(Transaction transaction){


    }
}
