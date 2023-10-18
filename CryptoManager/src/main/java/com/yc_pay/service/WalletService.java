package com.yc_pay.service;

import com.yc_pay.model.StatusResponse;
import com.yc_pay.model.WalletResponse;
import jakarta.inject.Singleton;
import org.xrpl.xrpl4j.crypto.keys.*;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class WalletService {

    public WalletResponse getWalletToBuyer(String network, String currency) {
        //1. Создавать кошельки из byte[16] и хранить его
        //2. Делать отдельный Thread до момента оплаты и перевода
        final byte[] out = new byte[16];
        ThreadLocalRandom.current().nextBytes(out);                                     //генерация рандомного byte[16]
        Entropy entropy = Entropy.of(out);
        Seed seed = Seed.secp256k1SeedFromEntropy(entropy);                             // генерация seed из entropy(byte[16])
        String newWallet = seed.deriveKeyPair().publicKey().deriveAddress().toString(); //генерация адреса
        return DatabaseService.saveXrpHotWallet(currency, network, out, newWallet);
    }

    public StatusResponse getStatusToBuyer(String network, String wallet){

        //ToDo проверка блокчейна
        //ToDo если нашел транзакцию - передавать в сервис Transactions
        // Забрать из базы byte[16] и сделать адрес для проверки

        StringBuilder s = new StringBuilder();
        String[] strings = s.toString().split(";");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        byte[] in = new byte[16];
        for (String s1: strings){
            int i = Integer.parseInt(s1);
            outputStream.write(i);
        }
        System.out.println(Arrays.toString(outputStream.toByteArray()));

        // выполнить проверку

        return new StatusResponse("Paid");

    }
}
