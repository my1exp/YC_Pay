package com.yc_pay.service;

import com.yc_pay.client.cryptoManager.CryptoManagerClient;
import com.yc_pay.client.cryptoManager.CryptoManagerStatusResponse;
import com.yc_pay.client.cryptoManager.CryptoManagerWalletResponse;
import com.yc_pay.client.pricer.PricerClient;
import com.yc_pay.model.IntentRequest;
import com.yc_pay.model.IntentResponse;
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


    public IntentResponse getIntent(String sessionId, String requestId){
        WalletRequestDB walletRequestDB = DatabaseService.getNetworkAndCurrency(sessionId, requestId); //проверяем интент в базе
        if (walletRequestDB != null) { // если он есть - проверяем статус оплаты
            System.out.println("Found intent");
            if(walletRequestDB.getStatus().equals("Paid")){ //сначала в базе интентов
                return DatabaseService.getIntent(sessionId,requestId);
            }else{
//               New thread

                //если в базе интентов нет информации, то в CryptoManagerService
                CryptoManagerStatusResponse cryptoManagerStatusResponse =
                        cryptoManagerClient.getStatusToBuyer(walletRequestDB.getWalletId());
                if (cryptoManagerStatusResponse.getStatus().equals("Paid")){
                    DatabaseService.updateIntentStatus(sessionId, requestId, cryptoManagerStatusResponse.getStatus()); //обновляем статус
                }
                return DatabaseService.getIntent(sessionId,requestId);
            }
        }else{
            return null; // Если нет интента - возвращаем null
        }
    }

    public void postBuyerIntent(String session_id, IntentRequest intentRequest) {

        float currentChangeRate = pricerClient.getPriceToBuyer(intentRequest.getCurrency_crypto(),
                intentRequest.getAmount_fiat(), intentRequest.getCurrency_fiat()).getAmountCrypto();
        float difference = ((intentRequest.getAmount_crypto() - currentChangeRate) / intentRequest.getAmount_crypto()) * 100;
        if ((currentChangeRate <= intentRequest.getAmount_crypto()) || difference >= -0.1) {//Проверка на корректность переданной стоимости
            DatabaseService.postBuyerIntent(session_id, intentRequest); // делаем запись о намерении
            System.out.println("Success intent ");
            CryptoManagerWalletResponse cryptoManagerWalletResponse = cryptoManagerClient.getWalletToBuyer(intentRequest.getNetwork(),
                    intentRequest.getCurrency_crypto(), intentRequest.getAmount_crypto()); //создаем кошелек
            DatabaseService.updateIntentWallet(session_id, intentRequest.getRequest_id(),
                    cryptoManagerWalletResponse.getWalletId(), cryptoManagerWalletResponse.getWallet()); //обновляем кошелек в намерении
            System.out.println("Wallet : " + cryptoManagerWalletResponse.getWallet());
        }
    }
}