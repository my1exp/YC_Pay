package com.yc_pay.service;

import com.yc_pay.client.cryptoManager.CryptoManagerClient;
import com.yc_pay.client.cryptoManager.CryptoManagerResponse;
import com.yc_pay.model.IntentRequest;
import com.yc_pay.model.IntentResponse;
import com.yc_pay.model.WalletRequest;
import jakarta.inject.Singleton;

@Singleton
public class IntentService {

    final private CryptoManagerClient cryptoManagerClient;

    public IntentService(CryptoManagerClient cryptoManagerClient) {
        this.cryptoManagerClient = cryptoManagerClient;
    }


    public IntentResponse getIntent(String sessionId, String requestId){
        System.out.println("Get Transaction");
        WalletRequest walletRequest = DatabaseService.getCurrency(sessionId, requestId);
        if (walletRequest != null) {
            CryptoManagerResponse cryptoManagerResponse = cryptoManagerClient.getWalletToBuyer(walletRequest.getNetwork(),
                    walletRequest.getCurrency());
            DatabaseService.updateIntent(sessionId, requestId, cryptoManagerResponse.getWallet());
            return DatabaseService.getIntent(sessionId,requestId);
        }else{
            return null;
        }
    }

    public void postBuyerIntent(String session_id, IntentRequest intentRequest){
        //ToDo сходить в Pricer и проверить разницу текущей цены и переданной
        DatabaseService.postBuyerIntent(session_id,intentRequest);
    }
}
