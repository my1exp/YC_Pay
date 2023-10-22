package com.yc_pay.service;

import com.yc_pay.model.WalletInfoDB;
import com.yc_pay.model.WalletResponse;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static com.yc_pay.model.dbModels.generated.Tables.CRYPTO_WALLETS;

@Singleton
public class DatabaseService {

    public static String userName = "postgres";
    public static String password = "1234";
    public static String url = "jdbc:postgresql://localhost:5432/CryptoManager";

    public static WalletResponse saveXrpHotWallet(String currency, String network,
                                                  byte[] secret, String address, float amountCrypto){

        StringBuilder s = new StringBuilder();
        for (byte b : secret) {
            s.append(b).append(";");
        }
        String stringSecret = s.toString();
        WalletResponse walletResponse = new WalletResponse();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext postIntent = DSL.using(conn, SQLDialect.POSTGRES);
            postIntent
                    .insertInto(CRYPTO_WALLETS, CRYPTO_WALLETS.CURRENCY_CRYPTO, CRYPTO_WALLETS.NETWORK,
                            CRYPTO_WALLETS.PUBLIC_KEY, CRYPTO_WALLETS.PRIVATE_KEY, CRYPTO_WALLETS.ADDRESS,
                            CRYPTO_WALLETS.CREATE_DATE, CRYPTO_WALLETS.TYPE, CRYPTO_WALLETS.AMOUNT_CRYPTO)
                    .values(currency, network, null, stringSecret, address,
                            LocalDateTime.now(), "HotWallet", BigDecimal.valueOf(amountCrypto))
                    .execute();
            Result<Record> result = postIntent.select()
                    .from(CRYPTO_WALLETS)
                    .where(CRYPTO_WALLETS.ADDRESS.eq(address))
                    .fetch();

            for (Record r : result) {
                walletResponse = new WalletResponse(r.getValue(CRYPTO_WALLETS.ID),
                        r.getValue(CRYPTO_WALLETS.ADDRESS));
            }
            return walletResponse;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return walletResponse;
        }
    }

    public static WalletInfoDB getWalletInfo(int walletId){
        WalletInfoDB walletInfoDB = new WalletInfoDB();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext get = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = get.select()
                    .from(CRYPTO_WALLETS)
                    .where(CRYPTO_WALLETS.ID.eq(walletId))
                    .fetch();
            conn.close();

            for (Record r : result) {
                walletInfoDB = new WalletInfoDB(r.getValue(CRYPTO_WALLETS.ADDRESS),
                        r.getValue(CRYPTO_WALLETS.PRIVATE_KEY), r.getValue(CRYPTO_WALLETS.AMOUNT_CRYPTO));
            }
            return walletInfoDB;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return walletInfoDB;
        }
    }
}
