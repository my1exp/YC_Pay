package com.yc_pay.service;

import com.yc_pay.model.StatusResponse;
import com.yc_pay.model.WalletResponse;
import jakarta.inject.Singleton;
import org.xrpl.xrpl4j.crypto.keys.KeyPair;
import org.xrpl.xrpl4j.crypto.keys.PrivateKey;
import org.xrpl.xrpl4j.crypto.keys.PublicKey;
import org.xrpl.xrpl4j.crypto.keys.Seed;
import org.xrpl.xrpl4j.model.transactions.Address;

@Singleton
public class WalletService {

    public WalletResponse getWalletToBuyer(String network, String currency) {

        //ToDo логика генерации кошельков
        Seed randomSeed = Seed.ed25519Seed();
        KeyPair keyPair = randomSeed.deriveKeyPair();
        PublicKey publicKey = keyPair.publicKey();
        PrivateKey privateKey = keyPair.privateKey();
        Address address = keyPair.publicKey().deriveAddress();


        System.out.println("address: " + address.toString());
        System.out.println("publicKey: " + publicKey.toString());
        System.out.println("privateKey: " + privateKey.toString());
        System.out.println("_________________________________________");

        return new WalletResponse(address.toString());

    }

    public StatusResponse getStatusToBuyer(String network, String wallet){
        //ToDo проверка блокчейна
        //ToDo если нашел транзакцию - передавать в сервис Transactions
        return new StatusResponse("Paid");

    }
}
