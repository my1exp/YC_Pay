package com.yc_pay.service;

import com.yc_pay.model.WalletResponse;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.yc_pay.model.dbModels.generated.Tables.CRYPTO_PAYMENTS;
import static com.yc_pay.model.dbModels.generated.Tables.CRYPTO_WALLETS;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.max;

@Singleton
public class DatabaseService {

    public static String userName = "postgres";
    public static String password = "1234";
    public static String url = "jdbc:postgresql://localhost:5432/CryptoManager";

    public static Integer saveXrpHotWallet(String currency, String network,
                                                  byte[] secret, String address){

        StringBuilder s = new StringBuilder();
        for (byte b : secret) {
            s.append(b).append(";");
        }
        String stringSecret = s.toString();
        int wallet_id = 0;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext postIntent = DSL.using(conn, SQLDialect.POSTGRES);
            postIntent
                    .insertInto(CRYPTO_WALLETS, CRYPTO_WALLETS.CURRENCY_CRYPTO, CRYPTO_WALLETS.NETWORK,
                            CRYPTO_WALLETS.PUBLIC_KEY, CRYPTO_WALLETS.PRIVATE_KEY, CRYPTO_WALLETS.ADDRESS,
                            CRYPTO_WALLETS.CREATE_DATE, CRYPTO_WALLETS.TYPE)
                    .values(currency, network, null, stringSecret, address,
                            LocalDateTime.now(), "HotWallet")
                    .execute();
            Result<Record> result = postIntent.select()
                    .from(CRYPTO_WALLETS)
                    .where(CRYPTO_WALLETS.ADDRESS.eq(address))
                    .fetch();

            for (Record r : result) {
                wallet_id = r.getValue(CRYPTO_WALLETS.ID);
            }
            return wallet_id;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return wallet_id;
        }
    }

    public static void savePaymentFromBuyer(int wallet_id, float amount_crypto, int tag) {

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext postIntent = DSL.using(conn, SQLDialect.POSTGRES);
            postIntent
                    .insertInto(CRYPTO_PAYMENTS, CRYPTO_PAYMENTS.CRYPTO_WALLET_ID,
                            CRYPTO_PAYMENTS.STATUS, CRYPTO_PAYMENTS.AMOUNT_CRYPTO,
                            CRYPTO_PAYMENTS.DESTINATION_TAG)
                    .values(wallet_id, 1, Double.valueOf(amount_crypto), tag)
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static WalletResponse getWalletAndTag(String currency, String network, float amountCrypto) {

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext dslContext = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record1<Integer>> maxIdResult = dslContext
                    .select(max(CRYPTO_WALLETS.ID))
                    .from(CRYPTO_WALLETS)
                    .where(CRYPTO_WALLETS.CURRENCY_CRYPTO.eq(currency),
                            CRYPTO_WALLETS.NETWORK.eq(network))
                    .fetch();
            int maxWalletId = Integer.parseInt(maxIdResult.get(0).getValue("max").toString());

            @NotNull Result<Record1<Integer>> countRowsResult = dslContext
                    .select(count(CRYPTO_PAYMENTS.ID))
                    .from(CRYPTO_PAYMENTS)
                    .where(CRYPTO_PAYMENTS.CRYPTO_WALLET_ID.eq(maxWalletId))
                    .fetch();
            conn.close();
            int countMaxWalletId = Integer.parseInt(countRowsResult.get(0).getValue("count").toString());

            if(maxWalletId == 0 || countMaxWalletId >= 100){
               int wallet_id =  WalletService.createWallet(currency, network);
               savePaymentFromBuyer(wallet_id, amountCrypto,0);
               return new WalletResponse(getWalletWithId(wallet_id), 0);
            }else{
                savePaymentFromBuyer(maxWalletId, amountCrypto, countMaxWalletId);
                return new WalletResponse(getWalletWithId(maxWalletId), countMaxWalletId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getWalletWithId(int id){
        String wallet = null;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext postIntent = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record1<String>> result = postIntent.select(CRYPTO_WALLETS.ADDRESS)
                    .from(CRYPTO_WALLETS)
                    .where(CRYPTO_WALLETS.ID.eq(id))
                    .fetch();
            conn.close();

            for (Record r : result) {
                wallet =  r.getValue(CRYPTO_WALLETS.ADDRESS);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return wallet;
    }


//    public static WalletInfoDB getWalletInfo(int walletId){
//        WalletInfoDB walletInfoDB = new WalletInfoDB();
//        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
//            DSLContext get = DSL.using(conn, SQLDialect.POSTGRES);
//            Result<Record> result = get.select()
//                    .from(CRYPTO_WALLETS)
//                    .where(CRYPTO_WALLETS.ID.eq(walletId))
//                    .fetch();
//            conn.close();
//
//            for (Record r : result) {
//                walletInfoDB = new WalletInfoDB(r.getValue(CRYPTO_WALLETS.ADDRESS),
//                        r.getValue(CRYPTO_WALLETS.PRIVATE_KEY), r.getValue(CRYPTO_WALLETS.AMOUNT_CRYPTO));
//            }
//            return walletInfoDB;
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//            return walletInfoDB;
//        }
//    }
}
