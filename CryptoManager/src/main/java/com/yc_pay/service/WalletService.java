package com.yc_pay.service;

import com.yc_pay.client.xrp.XrpClient;
import com.yc_pay.model.WalletResponse;
import jakarta.inject.Singleton;
import org.xrpl.xrpl4j.crypto.keys.Seed;
import java.util.concurrent.ThreadLocalRandom;
import org.xrpl.xrpl4j.crypto.keys.*;
import java.util.Arrays;
import java.util.List;

import static com.yc_pay.service.DatabaseService.getWalletAddressWithId;
import static com.yc_pay.service.DatabaseService.savePaymentFromBuyer;

@Singleton
public class WalletService {

    final private XrpClient xrpClient;

    public WalletService(XrpClient xrpClient) {
        this.xrpClient = xrpClient;
    }

    public WalletResponse getWalletToBuyer(String network, String currency, float amountCrypto,
                                           String requestId, String sessionId) {
        if(currency.equals("XRP") && network.equals("NATIVE")){
            List<Integer> walletAndTag = DatabaseService.getWalletAndTag(currency, network, amountCrypto);
            if(walletAndTag.get(0) == 0 || walletAndTag.get(1) >= 100005){
                int walletId = createWallet(network, currency);
                savePaymentFromBuyer(walletId, amountCrypto,100000, requestId, sessionId);
                return new WalletResponse(walletId, getWalletAddressWithId(walletId),0);
            }else{
                savePaymentFromBuyer(walletAndTag.get(0), amountCrypto,
                        walletAndTag.get(1), requestId, sessionId);
                return new WalletResponse(walletAndTag.get(0),
                        getWalletAddressWithId(walletAndTag.get(0)), walletAndTag.get(1));
            }
        }else{
            return null;
        }
    }

    public Integer createWallet(String network, String currency) {
        if(currency.equals("XRP") && network.equals("NATIVE")){
//            Создание одноразового кошелька и запись в таблицу с кошельками

//          генерация рандомного byte[16]
            final byte[] privateKey = new byte[16];
            ThreadLocalRandom.current().nextBytes(privateKey);
//          генерация секретного ключа
            Entropy entropy = Entropy.of(privateKey);
            Seed seed = Seed.secp256k1SeedFromEntropy(entropy);
//          генерация адреса кошелька
            String address = seed.deriveKeyPair().publicKey().deriveAddress().toString();
//          Запись в таблицу БД
            return DatabaseService.saveXrpHotWallet(currency, network, Arrays.toString(privateKey), address);
        }else{
            return 0;
        }
    }

//    public XrpCheckResponse XrpCheckPayments(HashMap<String, ArrayList<WalletAddress>> payments) {
//        XrpCheckResponse ClientPayments = xrpClient.checkPayment(payments);
//        System.out.println(ClientPayments);
//        return ClientPayments;
//        }
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