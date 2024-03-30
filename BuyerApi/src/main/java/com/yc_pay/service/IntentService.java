package com.yc_pay.service;

import com.yc_pay.client.cryptoManager.CryptoManagerClient;
import com.yc_pay.client.cryptoManager.CryptoManagerWalletResponse;
import com.yc_pay.client.pricer.PricerClient;
import com.yc_pay.model.IntentRequest;
import com.yc_pay.model.IntentResponse;
import com.yc_pay.model.IntentStatusResponse;
import com.yc_pay.model.Intent;
import jakarta.inject.Singleton;

@Singleton
public class IntentService {

    final private CryptoManagerClient cryptoManagerClient;
    final private PricerClient pricerClient;

    public IntentService(CryptoManagerClient cryptoManagerClient, PricerClient pricerClient) {
        this.cryptoManagerClient = cryptoManagerClient;
        this.pricerClient = pricerClient;
    }


    public IntentResponse getIntent(String sessionId, String requestId, String merchantId){
        //проверяем интент в базе
        Intent intent = DatabaseService.getIntentByOrderIdAndMerchantId(sessionId, requestId, merchantId);
        // если он есть - создаем кошелек для оплаты
        if (intent.getStatus() != null) {
            if(intent.getWallet() == null) {
                //создаем кошелек
                CryptoManagerWalletResponse cryptoManagerWalletResponse = cryptoManagerClient.getWalletToBuyer(
                        intent.getNetwork(), intent.getCurrency(), intent.getAmountCrypto(),
                        intent.getAmountFiat(), intent.getMerchant_id(), intent.getRequest_id(),intent.getSession_id());
                System.out.println(cryptoManagerWalletResponse);
                //обновляем кошелек в намерении
                DatabaseService.updateIntentWallet(merchantId, requestId, cryptoManagerWalletResponse.getWalletId(),
                        cryptoManagerWalletResponse.getWallet(), cryptoManagerWalletResponse.getDestination_tag());
                return new IntentResponse(cryptoManagerWalletResponse.getWallet(), String.valueOf(cryptoManagerWalletResponse.getDestination_tag()),
                        intent.getCurrency(), intent.getNetwork(), intent.getAmountCrypto());
            }
            else{
                return new IntentResponse(intent.getWallet(), intent.getDestination_tag(),
                        intent.getCurrency(), intent.getNetwork(), intent.getAmountCrypto());
            }
        }else{
            // Если нет интента - возвращаем пустой ответ
            return new IntentResponse();
        }
    }


    public IntentStatusResponse getIntentStatus(String sessionId, String order_id, String merchant_id){
        //проверяем интент в базе
        Intent intent = DatabaseService.getIntentByOrderIdAndMerchantId(sessionId, order_id, merchant_id);
        // если он есть - проверяем статус оплаты
        if (intent.getStatus() != null) {
            return new IntentStatusResponse(intent.getStatus());
        }else{
            return new IntentStatusResponse(); // Если нет интента - возвращаем null
        }
    }

    public String postBuyerIntent(String session_id, IntentRequest intentRequest) {
        //проверяем интент в базе
        Intent intent = DatabaseService.getIntentByOrderIdAndMerchantId(session_id, intentRequest.getOrder_id(),
                intentRequest.getMerchant_id());
        //Если уже есть - говорим, что уже приняли запрос
        if(intent.getStatus() != null){
            return "!Unique intent";
        }else{
            //проверяем текущую цену
            float currentChangeRate = pricerClient.getPriceToBuyer(intentRequest.getCurrency_crypto(),
                    intentRequest.getAmount_fiat(), intentRequest.getCurrency_fiat()).getAmountCrypto();
            //Проверка на корректность переданной стоимости
            float difference = ((intentRequest.getAmount_crypto() - currentChangeRate) / intentRequest.getAmount_crypto()) * 100;
            // делаем запись о намерении
            if ((currentChangeRate <= intentRequest.getAmount_crypto()) || difference >= -0.1) {
                DatabaseService.postBuyerIntent(session_id, intentRequest);
                return "Intent added";
            }
            else{
                return "Difference check false";
            }
        }
    }

    public String updateBuyerIntentStatus(String orderId, String sessionId) {

        String status = DatabaseService.getIntentByOrderIdAndSessionId(orderId, sessionId);
        if(status.equals("Created")){
            DatabaseService.updateIntentStatusBySessionIdAndRequestId(sessionId, orderId, "Paid");
        }
        return "Ok";
    }
}