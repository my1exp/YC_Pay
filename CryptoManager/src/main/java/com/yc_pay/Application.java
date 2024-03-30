package com.yc_pay;

import com.yc_pay.service.jobs.CheckPaymentJob;
import com.yc_pay.service.jobs.PaymentDeliveryToTxJob;
import com.yc_pay.service.jobs.ReturnCryptoToUserJob;
import com.yc_pay.service.jobs.StatusDeliveryToPaymentApiJob;
import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
        CheckPaymentJob checkPaymentJob = new CheckPaymentJob();
        StatusDeliveryToPaymentApiJob statusDeliveryToPaymentApiJob = new StatusDeliveryToPaymentApiJob();
        PaymentDeliveryToTxJob paymentDeliveryToTxJob = new PaymentDeliveryToTxJob();
        ReturnCryptoToUserJob returnCryptoToUserJob = new ReturnCryptoToUserJob();
//        checkPaymentJob.start();
        statusDeliveryToPaymentApiJob.start();
        paymentDeliveryToTxJob.start();
        returnCryptoToUserJob.start();

    }
}