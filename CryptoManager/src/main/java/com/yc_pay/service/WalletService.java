package com.yc_pay.service;

import com.yc_pay.model.Intent;
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

    public WalletResponse getWalletToBuyer(Intent intent) {
        if(intent.getCurrency().equals("XRP") && intent.getNetwork().equals("NATIVE")){
            List<Integer> walletAndTag = DatabaseService.getWalletAndTag(intent.getCurrency(), intent.getNetwork());
            if(walletAndTag.get(0) == 0 || walletAndTag.get(1) >= 100050){
                int walletId = createWallet(intent.getNetwork(), intent.getCurrency());
                savePaymentFromBuyer(walletId, intent.getAmountCrypto(), walletAndTag.get(1), intent.getRequest_id(),
                        intent.getSession_id(), intent.getMerchant_id(), intent.getAmountFiat());
                return new WalletResponse(walletId, getWalletAddressWithId(walletId), walletAndTag.get(1));
            }else{
                savePaymentFromBuyer(walletAndTag.get(0), intent.getAmountCrypto(), walletAndTag.get(1),
                        intent.getRequest_id(), intent.getSession_id(), intent.getMerchant_id(), intent.getAmountFiat());
                return new WalletResponse(walletAndTag.get(0),
                        getWalletAddressWithId(walletAndTag.get(0)), walletAndTag.get(1));
            }
        }else{
            return null;
        }
    }

    public Integer createWallet(String network, String currency) {
        if(currency.equals("XRP") && network.equals("NATIVE")){
//          Создание одноразового кошелька и запись в таблицу с кошельками
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
}