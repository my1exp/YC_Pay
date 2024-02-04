package com.yc_pay.service;

import com.yc_pay.client.cryptoManager.CryptoManagerClient;
import com.yc_pay.client.cryptoManager.CryptoManagerWalletResponse;
import com.yc_pay.client.pricer.PricerClient;
import com.yc_pay.model.IntentRequest;
import com.yc_pay.model.IntentResponse;
import com.yc_pay.model.IntentStatusResponse;
import com.yc_pay.model.WalletRequestDB;
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
        WalletRequestDB walletRequestDB = DatabaseService.getIntent(sessionId, requestId, merchantId);
        // если он есть - создаем кошелек для оплаты
        if (walletRequestDB.getStatus() != null) {
            if(walletRequestDB.getWallet() == null) {
                //создаем кошелек
                CryptoManagerWalletResponse cryptoManagerWalletResponse = cryptoManagerClient.getWalletToBuyer(walletRequestDB.getNetwork(),
                        walletRequestDB.getCurrency(), walletRequestDB.getAmountCrypto());
                //обновляем кошелек в намерении
                DatabaseService.updateIntentWallet(merchantId, requestId, cryptoManagerWalletResponse.getWallet(),
                        cryptoManagerWalletResponse.getDestination_tag());
                return new IntentResponse(cryptoManagerWalletResponse.getWallet(), String.valueOf(cryptoManagerWalletResponse.getDestination_tag()),
                        walletRequestDB.getCurrency(), walletRequestDB.getNetwork(), walletRequestDB.getAmountCrypto());
            }
            else{
                return new IntentResponse(walletRequestDB.getWallet(), walletRequestDB.getDestination_tag(),
                        walletRequestDB.getCurrency(), walletRequestDB.getNetwork(), walletRequestDB.getAmountCrypto());
            }
        }else{
            // Если нет интента - возвращаем пустой ответ
            return new IntentResponse();
        }
    }


    public IntentStatusResponse getIntentStatus(String sessionId, String order_id, String merchant_id){
        //проверяем интент в базе
        WalletRequestDB walletRequestDB = DatabaseService.getIntent(sessionId, order_id, merchant_id);
        // если он есть - проверяем статус оплаты
        if (walletRequestDB.getStatus() != null) {
            return new IntentStatusResponse(walletRequestDB.getStatus());
        }else{
            return new IntentStatusResponse(); // Если нет интента - возвращаем null
        }
    }

    public String postBuyerIntent(String session_id, IntentRequest intentRequest) {
        //проверяем интент в базе
        WalletRequestDB walletRequestDB = DatabaseService.getIntent(session_id, intentRequest.getOrder_id(),
                intentRequest.getMerchant_id());
        //Если уже есть - говорим, что уже приняли запрос
        if(walletRequestDB.getStatus() != null){
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
}