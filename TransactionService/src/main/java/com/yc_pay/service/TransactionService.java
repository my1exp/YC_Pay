package com.yc_pay.service;

import com.yc_pay.model.Transaction;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Objects;

@Singleton
@Slf4j(topic = "TransactionService")
public class TransactionService {

    private final DatabaseService databaseService;

    public TransactionService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void createEntriesForIdentifiedTransaction(Transaction transaction){
        log.info("Try to create Entries For Transaction " + transaction.getPaid_amount_crypto()
                + " " + transaction.getCurrency_crypto());
        DatabaseService.createEntryForAnyTransaction(transaction.getPaid_amount_crypto(), transaction.getCurrency_crypto());

        log.info("Try to create Entries for Identified Transaction " + transaction.getPaid_amount_crypto()
                + " " + transaction.getCurrency_crypto());
        DatabaseService.createEntryForIdentifiedTransaction(transaction);
        log.info("Success created Entries for identified Transaction " + transaction.getPaid_amount_crypto()
                + " " + transaction.getCurrency_crypto());
    }

    public void createEntriesForUnidentifiedTransaction(double amountCrypto, String currencyCrypto) {
        log.info("Try to create Entries For Transaction " + amountCrypto + " " + currencyCrypto);
        DatabaseService.createEntryForAnyTransaction(amountCrypto, currencyCrypto);

        log.info("Try to create Entries For Unidentified Transaction " + amountCrypto + " " + currencyCrypto);
        DatabaseService.createEntryForUnidentifiedTransaction(amountCrypto, currencyCrypto);
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
                DatabaseService.createEntryForExchangeIdentifiedTransaction(exchangedAmountCrypto, exchangedCurrencyCrypto,
                        receivedCurrencyFiat, receivedAmountFiat); //Обмен по рыночной стоимости
                log.info("Success created Entries For Exchange " + exchangedAmountCrypto  + " " +  exchangedCurrencyCrypto
                        + " to " + receivedAmountFiat + " " + receivedCurrencyFiat);

                //Разницу между рыночной стоимостью и полученной фиксируем отдельной проводкой
                DatabaseService.createEntryForExchangeDifference(exchangeDifference);
                log.info("Success created Entries For Exchange Commission " +  String.format("%.3f",-1 * exchangeDifference) +  " USDT");
            }
        }else{
            log.info("Try to create Entries For Exchange Unidentified Transaction");
            DatabaseService.createEntryForExchangeUnidentifiedTransaction(exchangedAmountCrypto, exchangedCurrencyCrypto,
                    receivedCurrencyFiat, receivedAmountFiat);
            log.info("Success created Entries For Exchange " + exchangedAmountCrypto  + " " +  exchangedCurrencyCrypto
                    + " to " + receivedAmountFiat + " " + receivedCurrencyFiat);
        }
    }

    public String createEntriesForPaymentTransaction(String merchantId, String currencyFiat, double amountFiat) {
        //проверка на сумму вывода
        double availablePaymentAmount = databaseService.checkAvailablePaymentAmount(merchantId);
        if(availablePaymentAmount >= amountFiat){
            log.info("Try to create Entries For Payment Transaction");
            databaseService.createEntryForPaymentTransaction(merchantId, amountFiat);
            log.info("Success created Entries For Payment Transaction " + amountFiat + " " + currencyFiat + " to merchant " + merchantId);
            return "Ok";
        }else{
            return "Not Enough for requested Payment";
        }
    }
}

