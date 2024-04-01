package com.yc_pay.service;

import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionResponse;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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

    public void createEntriesForExchangeTransaction(double exchangedAmountCrypto, String exchangedCurrencyCrypto,
                                                    String receivedCurrencyFiat, double receivedAmountFiat,
                                                    Integer identificationFlag) throws IOException, InterruptedException {
        if (identificationFlag == 1) {
            log.info("Try to create Entries For Exchange Identified Transaction");

            double marketPrice = Double.parseDouble(Objects.requireNonNull(CheckExchangeDiffService.getMarketPriceCrypto(exchangedCurrencyCrypto)));
            double exchangeDifference = receivedAmountFiat - exchangedAmountCrypto * marketPrice;

            if(exchangeDifference > 0){
                log.info("Exchange difference is positive");
                throw new IOException("Exchange difference is positive");
            }else{
                databaseService.createEntryForExchangeIdentifiedTransaction(exchangedAmountCrypto, exchangedCurrencyCrypto,
                        receivedCurrencyFiat, receivedAmountFiat); //Обмен по рыночной стоимости
                log.info("Success created Entries For Exchange " + exchangedAmountCrypto  + " " +  exchangedCurrencyCrypto
                        + " to " + receivedAmountFiat + " " + receivedCurrencyFiat);

                //Разницу между рыночной стоимостью и полученной фиксируем отдельной проводкой
                databaseService.createEntryForExchangeDifference(exchangeDifference);
            }
        }else{
            log.info("Try to create Entries For Exchange Unidentified Transaction");
            databaseService.createEntryForExchangeUnidentifiedTransaction(exchangedAmountCrypto, exchangedCurrencyCrypto,
                    receivedCurrencyFiat, receivedAmountFiat);
            log.info("Success created Entries For Exchange " + exchangedAmountCrypto  + " " +  exchangedCurrencyCrypto
                    + " to " + receivedAmountFiat + " " + receivedCurrencyFiat);
        }
    }
}

