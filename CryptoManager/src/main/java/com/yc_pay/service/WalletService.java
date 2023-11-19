package com.yc_pay.service;

import com.yc_pay.model.WalletResponse;
import jakarta.inject.Singleton;
import org.xrpl.xrpl4j.crypto.keys.Entropy;
import org.xrpl.xrpl4j.crypto.keys.Seed;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class WalletService {

    public WalletResponse getWalletToBuyer(String network, String currency, float amountCrypto) {
        if(currency.equals("XRP") && network.equals("NATIVE")){
            return DatabaseService.getWalletAndTag(currency, network, amountCrypto);
        }else{
            return null;
        }
    }

    public static Integer createWallet(String network, String currency) {
        if(currency.equals("XRP") && network.equals("NATIVE")){
//            Создание одноразового кошелька и запись в таблицу с кошельками
            final byte[] out = new byte[16];
            ThreadLocalRandom.current().nextBytes(out);                                     //генерация рандомного byte[16]
            Entropy entropy = Entropy.of(out);
            Seed seed = Seed.secp256k1SeedFromEntropy(entropy);                             // генерация seed из entropy(byte[16])
            String newWallet = seed.deriveKeyPair().publicKey().deriveAddress().toString(); //генерация адреса
            return DatabaseService.saveXrpHotWallet(currency, network, out, newWallet); // Запись в таблицу БД
        }else{
            return 0;
        }
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