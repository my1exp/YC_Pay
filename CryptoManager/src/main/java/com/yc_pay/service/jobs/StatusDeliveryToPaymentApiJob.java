package com.yc_pay.service.jobs;

import com.yc_pay.model.RequestAndSession;
import com.yc_pay.service.DatabaseService;
import com.yc_pay.service.delivery.StatusDeliveryService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;

@Singleton
@Slf4j(topic = "StatusDeliveryToPaymentApiJob")
public class StatusDeliveryToPaymentApiJob extends Thread{
    public void run(){
        while (true){
            ArrayList<RequestAndSession> listOfRequestsId = DatabaseService.listOfRequestsIdByDeliveryStatus(0);
            if (!listOfRequestsId.isEmpty()){
                System.out.println(listOfRequestsId.size());
                try{
                    for (RequestAndSession requestAndSession : listOfRequestsId) {
                        System.out.println(requestAndSession);
                        String response = StatusDeliveryService.statusDelivery(requestAndSession.getRequestId(), requestAndSession.getSessionId());
                        if (!response.equals("error")){
                            Integer paymentId = DatabaseService.getPaymentIdByRequestIdAndSessionId(
                                    requestAndSession.getRequestId(), requestAndSession.getSessionId());
                            DatabaseService.updateDeliveredFlag(paymentId, 1);
                        }
                    }
                }catch (Exception e){
                    log.error("deliveryToPayment job failed", e);
                }
                log.info("deliveryToPayment job executed - payments delivered");
                try {
                    Thread.sleep(1000 * 2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else{
                log.info("deliveryToPayment job executed - no payments to deliver");
                try {
                    Thread.sleep(1000 * 2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

