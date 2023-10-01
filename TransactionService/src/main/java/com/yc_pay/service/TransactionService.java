package com.yc_pay.service;

import com.yc_pay.model.TransactionRequest;
import com.yc_pay.model.TransactionResponse;
import jakarta.inject.Singleton;

import java.util.ArrayList;

@Singleton
public class TransactionService {

    private final DatabaseService databaseService;

    public TransactionService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Object createTransactionFromUser(String transactionId, String merchantId, TransactionRequest transactionRequest){

        TransactionResponse tx = new TransactionResponse(transactionId, merchantId, transactionRequest.getWalletFrom(),
                transactionRequest.getWalletTo(), transactionRequest.getCurrency(),
                transactionRequest.getAmount(), transactionRequest.getNetwork(),
                transactionRequest.getCreatedAt(), "payment", "created");


        ArrayList<Object> createTransactionFromUser = databaseService.createTransactionFromUser(tx);
        if(createTransactionFromUser.get(1) == "Added"){
            System.out.println("Добавлена транзакция пользователя " + tx);
            return createTransactionFromUser.get(0);
        }else{
            System.out.println("Транзакция уже добавлена " + tx);
            return createTransactionFromUser.get(0);
        }
    }
}
