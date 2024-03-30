package com.yc_pay.service.jobs;


import com.yc_pay.model.DetailsForSendXrp;
import com.yc_pay.service.CryptoTxService;
import com.yc_pay.service.DatabaseService;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j(topic = "ReturnCryptoToUserJob")
@Singleton
public class ReturnCryptoToUserJob extends Thread{

    @SneakyThrows
    public void run() {
        while (true) {
            log.info("Start ReturnCryptoToUserJob");

            // Забираем все платежи, которые были сделаны в течение последних суток, успешно идентифицированы
            // и полученная сумма меньше необходимой
            ArrayList<Integer> paymentsListToReturn = DatabaseService.getPaymentsListToReturn();
            if (paymentsListToReturn.size() != 0) {

                log.info("Found " + paymentsListToReturn.size() + " payments to return");
                ArrayList<DetailsForSendXrp> detailsForSendXrp = DatabaseService.getDetailsForReturnXrpToUser(paymentsListToReturn);
                System.out.println(detailsForSendXrp.size());
                if (detailsForSendXrp.size() > 0) {
                    for (DetailsForSendXrp details : detailsForSendXrp) {
                        CryptoTxService.XrpSendToColdWallet(details);
                    }
                }
                for(int paymentId : paymentsListToReturn) {
                    DatabaseService.markTransactionsAsReturnedToUser(paymentId);
                }
                log.info("Returned and updated " + detailsForSendXrp.size() + " payments");
            }else{
                log.info("No payments to return");
            }
            log.info("Job has finished, sleeping for a hour");
            Thread.sleep(1000 * 60 * 60);
        }
    }
}
