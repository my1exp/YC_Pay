package com.yc_pay.service;

import com.yc_pay.model.Transaction;
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

    public Object postUserTransaction(String transactionId, String merchantId, TransactionRequest transactionRequest){

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

    public TransactionResponse getUserTransaction(String transactionId, String merchantId){
        Transaction transaction = databaseService.getTransactionFromUser(transactionId,merchantId);
        if(transaction.getTransactionId() != null){
            System.out.println("Транзакция найдена - " + transaction);
        }else{
            System.out.println("Транзакция не найдена");
        }
        return new TransactionResponse(transaction.getTransactionId(),
                transaction.getMerchantId(), transaction.getWalletFrom(), transaction.getWalletTo(),
                transaction.getCurrency(), transaction.getAmount().floatValue(), transaction.getNetwork(),
                transaction.getCreatedAt().toString(), transaction.getCategory(), transaction.getStatus());
    }
}
