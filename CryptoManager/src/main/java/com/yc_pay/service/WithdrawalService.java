package com.yc_pay.service;

import com.yc_pay.model.DetailsForSendXrp;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;

@Singleton
@Slf4j(topic = "WithdrawalService")
public class WithdrawalService {
    public String withdrawToAddress(String address) {

        HashMap<Integer, Double> walletsToWithdraw = DatabaseService.getWalletsToWithdraw();

        if (walletsToWithdraw.isEmpty()) {
            return "No wallets to withdraw from";
        }else {
            for (Integer walletId : walletsToWithdraw.keySet()) {
                try {
                    String privateKey = DatabaseService.getWalletPrivateKeyWithId(walletId);
                    if (privateKey != null) {
                        DetailsForSendXrp detailsForSendXrp = new DetailsForSendXrp(address, privateKey, walletsToWithdraw.get(walletId) - 10.001);
                        CryptoTxService.XrpSendToColdWallet(detailsForSendXrp);
                        log.info("Withdrawal to address: " + address);
                    }
                    DatabaseService.updateWithdrawFlag(walletId, 2);
                }catch (Exception e){
                    log.error("Error sending to address: " + e.getMessage());
                }
            }
        }
        return "Ok";
    }
}
