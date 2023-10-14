package com.yc_pay.service;

import com.yc_pay.model.StatusResponse;
import com.yc_pay.model.WalletResponse;
import jakarta.inject.Singleton;

@Singleton
public class WalletService {

    public WalletResponse getWalletToBuyer(String network, String currency){

        //ToDo логика генерации кошельков

        //ToDo если нашел транзакцию - передавать в сервис Transactions и менять статус в PaymentApi с created на paid
        return new WalletResponse("GeneratedWallet");

    }

    public StatusResponse getStatusToBuyer(String network, String wallet){
        //ToDo проверка блокчейна
        //ToDo если нашел транзакцию - передавать в сервис Transactions
        return new StatusResponse("Paid");

    }
}
