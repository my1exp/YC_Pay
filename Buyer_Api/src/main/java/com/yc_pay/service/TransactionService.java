package com.yc_pay.service;

import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionRequest;
import jakarta.inject.Singleton;

@Singleton
public class TransactionService {
    public Transaction getTransaction(String hash, String network){
        //ToDo должен делать запрос в сервис транзакций и забирать оттуда данные по id транзакции
        return new Transaction("transaction_id", "hash", "wallet_from",
                "wallet_to", "currency", 10.6F,"network",
                "created_at", "category", "status");
    }

    public Transaction createTransaction(String hash, String network, TransactionRequest transactionRequest){

        //ToDo должен отдавать данные в сервис транзакций
        // если есть хэш в таблице - то апдейт данных
        // если его нет - то создается новая запись в базе

        Transaction transaction = new Transaction();
        transaction.setTransactionId("123");
        transaction.setHash(transactionRequest.getHash());
        transaction.setWalletFrom(transactionRequest.getWalletFrom());
        transaction.setWalletTo(transactionRequest.getWalletTo());
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setNetwork(transactionRequest.getNetwork());
        transaction.setCreatedAt(transactionRequest.getCreatedAt());
        transaction.setCategory("category");
        transaction.setStatus("status");

        return transaction;
    }
}
