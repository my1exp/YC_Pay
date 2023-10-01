package com.yc_pay.service;

import com.yc_pay.client.TransactionClient;
import com.yc_pay.client.TransactionResponse;
import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionRequest;
import jakarta.inject.Singleton;

@Singleton
public class TransactionService {
    private final TransactionClient transactionClient;

    public TransactionService(TransactionClient transactionClient) {
        this.transactionClient = transactionClient;
    }

    public TransactionResponse getTransaction(String transactionId, String merchantId){
        System.out.println("Get Transaction");
        return transactionClient.getBuyerTransaction(transactionId, merchantId);
    }

    public TransactionResponse postBuyerTransaction(String transactionId, String merchantId, TransactionRequest transactionRequest){
        System.out.println("Post Transaction");
        return transactionClient.postBuyerTransaction(transactionId, merchantId, transactionRequest);
//        return new Transaction(transactionId, merchantId, "wallet_from",
//                "wallet_to", "currency", 10.6F, "network",
//                "created_at", "category", "status");
    }
}
