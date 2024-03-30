package com.yc_pay.service.jobs;

import com.yc_pay.model.TransactionToTxService;
import com.yc_pay.service.DatabaseService;
import com.yc_pay.service.delivery.TransactionDeliveryService;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;

@Singleton
@Slf4j(topic = "PaymentDeliveryToTxService")
public class PaymentDeliveryToTxJob extends Thread{

    @SneakyThrows
    public void run() {
        while (true) {

            log.info("Starting PaymentDeliveryToTxService");
            ArrayList<TransactionToTxService> unidentifiedTransactions = DatabaseService.getUndeliveredTransactions(false);
            if (unidentifiedTransactions.size() > 0) {
                for (TransactionToTxService transaction : unidentifiedTransactions) {
                    log.info("Delivering unidentified transaction to TxService: " + transaction.getPaid_amount_crypto() + " " +
                            transaction.getCurrency_crypto());
                    TransactionDeliveryService.deliveryUnidentifiedTransaction(transaction.getPaid_amount_crypto(),
                            transaction.getCurrency_crypto());
                    DatabaseService.markTransactionAsDelivered(transaction.getHash());
                    log.info("Successfully delivered unidentified transaction to TxService");
                }
            }

            ArrayList<TransactionToTxService> identifiedTransactions = DatabaseService.getUndeliveredTransactions(true);
            if (identifiedTransactions.size() > 0) {
                for (TransactionToTxService transaction : identifiedTransactions) {
                    log.info("Delivering identified transaction to TxService: " + transaction.getPaid_amount_crypto() + " " +
                            transaction.getCurrency_crypto() + " to merchant ID: " + transaction.getMerchant_id());
                    TransactionDeliveryService.deliveryIdentifiedTransaction(transaction.getMerchant_id(),
                            transaction.getPaid_amount_crypto(), transaction.getCurrency_crypto(),
                            transaction.getRequired_amount_crypto(), transaction.getAmount_fiat());
                    DatabaseService.markTransactionAsDelivered(transaction.getHash());
                }
                log.info("Successfully delivered identified transaction to TxService");
            }else{
                log.info("No new identified and fully paid transactions");
            }
            log.info("Job has finished, sleeping for a minute");
            sleep(1000 * 60);
        }
    }
}
