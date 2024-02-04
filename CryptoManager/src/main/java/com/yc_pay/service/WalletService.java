package com.yc_pay.service;

import com.yc_pay.client.xrp.XrpCheckResponse;
import com.yc_pay.client.xrp.XrpClient;
import com.yc_pay.client.xrp.XrpWalletResponse;
import com.yc_pay.model.WalletAddress;
import com.yc_pay.model.WalletResponse;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.yc_pay.service.DatabaseService.getWalletWithId;
import static com.yc_pay.service.DatabaseService.savePaymentFromBuyer;

@Singleton
public class WalletService {

    final private XrpClient xrpClient;

    public WalletService(XrpClient xrpClient) {
        this.xrpClient = xrpClient;
    }

    public WalletResponse getWalletToBuyer(String network, String currency, float amountCrypto) {
        if(currency.equals("XRP") && network.equals("NATIVE")){

            List<Integer> walletAndTag = DatabaseService.getWalletAndTag(currency, network, amountCrypto);
            System.out.println("max wallet + max tag: " + walletAndTag);
            if(walletAndTag.get(0) == 0 || walletAndTag.get(1) >= 50){
                int wallet_id = createWallet(network, currency);
                System.out.println("New wallet id: " + wallet_id);
                savePaymentFromBuyer(wallet_id, amountCrypto,0);
                return new WalletResponse(getWalletWithId(wallet_id), 0);
            }else{
                System.out.println("else");
                savePaymentFromBuyer(walletAndTag.get(0), amountCrypto, walletAndTag.get(1));
                return new WalletResponse(getWalletWithId(walletAndTag.get(0)), walletAndTag.get(1));
            }
        }else{
            return null;
        }
    }

    public Integer createWallet(String network, String currency) {
        if(currency.equals("XRP") && network.equals("NATIVE")){
            System.out.println("TEST CREATE WALLET METHOD");
//            Создание одноразового кошелька и запись в таблицу с кошельками
            XrpWalletResponse xrpWalletResponse = xrpClient.getWallet(network, currency);
            System.out.println(xrpWalletResponse);
            return DatabaseService.saveXrpHotWallet(currency, network, xrpWalletResponse); // Запись в таблицу БД
        }else{
            System.out.println("Create Wallet ELSE");
            return 0;
        }
    }

    public XrpCheckResponse XrpCheckPayments(HashMap<String, ArrayList<WalletAddress>> payments) {
        XrpCheckResponse ClientPayments = xrpClient.getPayments(payments);
        System.out.println(ClientPayments);
        return ClientPayments;
        }
//    public StatusResponse getStatusToBuyer(int walletId){
//
//        //ходим в базу, забираем адрес и приватный ключ
////        WalletInfoDB walletInfoDB = DatabaseService.getWalletInfo(walletId);
//
////        //проверка блокчейна
////        System.out.println(walletInfoDB.getAddress());
////        XrpCurrencyAmount xrpCurrencyAmount = NetworkCheckService.XrpNetworkCheck(walletInfoDB.getAddress());
////        double currentAmount = (Long.parseLong(xrpCurrencyAmount.toString()) / (Math.pow(10, 6))) - 10.0001;
////
////        if(Double.parseDouble(String.valueOf(walletInfoDB.getAmountCrypto())) <= currentAmount){
////            CryptoTxService.XrpSendToColdWallet(walletInfoDB.getAddress(), walletInfoDB.getPrivateKey(), currentAmount);
//            return new StatusResponse("Paid");
//            //ToDo если нашел, то отдельным Thread отправляем на холодный кошелек и передаем данные в сервис Transactions
//
//        }else{
//
//            System.out.println("Waiting for full payment");
//            return new StatusResponse("Waiting for full payment");
//        }
//    }
}