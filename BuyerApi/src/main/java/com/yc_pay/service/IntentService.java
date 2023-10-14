package com.yc_pay.service;

import com.yc_pay.client.cryptoManager.CryptoManagerClient;
import com.yc_pay.client.cryptoManager.CryptoManagerStatusResponse;
import com.yc_pay.client.cryptoManager.CryptoManagerWalletResponse;
import com.yc_pay.model.IntentRequest;
import com.yc_pay.model.IntentResponse;
import com.yc_pay.model.WalletRequestDB;
import jakarta.inject.Singleton;

@Singleton
public class IntentService {

    final private CryptoManagerClient cryptoManagerClient;

    public IntentService(CryptoManagerClient cryptoManagerClient) {
        this.cryptoManagerClient = cryptoManagerClient;
    }


    public IntentResponse getIntent(String sessionId, String requestId){
        WalletRequestDB walletRequestDB = DatabaseService.getCurrency(sessionId, requestId); //проверяем интент в базе
        if (walletRequestDB != null) {
            if (walletRequestDB.getWallet() != null){ // если он есть и заполнен кошелен - запрашиваем статус оплаты
                CryptoManagerStatusResponse cryptoManagerStatusResponse = cryptoManagerClient.getStatusToBuyer(walletRequestDB.getNetwork(),
                        walletRequestDB.getWallet());
                DatabaseService.updateIntentStatus(sessionId, requestId, cryptoManagerStatusResponse.getStatus()); //обновляем статус
            }else { // если он есть и НЕ заполнен кошелен - запрашиваем кошелек
                CryptoManagerWalletResponse cryptoManagerWalletResponse = cryptoManagerClient.getWalletToBuyer(walletRequestDB.getNetwork(),
                        walletRequestDB.getCurrency());
                DatabaseService.updateIntentWallet(sessionId, requestId, cryptoManagerWalletResponse.getWallet()); //обновляем кошелек
            }
            return DatabaseService.getIntent(sessionId,requestId);
        }else{ // Если нет интента - возвращаем null
            return null;
        }
    }

    public void postBuyerIntent(String session_id, IntentRequest intentRequest){
        //ToDo сходить в Pricer и проверить разницу текущей цены и переданной
        DatabaseService.postBuyerIntent(session_id,intentRequest);
    }
}
