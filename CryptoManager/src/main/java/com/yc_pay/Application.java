package com.yc_pay;

import com.yc_pay.service.jobs.CheckPaymentJob;
import com.yc_pay.service.jobs.StatusDeliveryToPaymentApiJob;
import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
        CheckPaymentJob checkPaymentJob = new CheckPaymentJob();
        StatusDeliveryToPaymentApiJob statusDeliveryToPaymentApiJob = new StatusDeliveryToPaymentApiJob();
        checkPaymentJob.start();
        statusDeliveryToPaymentApiJob.start();
    }
}