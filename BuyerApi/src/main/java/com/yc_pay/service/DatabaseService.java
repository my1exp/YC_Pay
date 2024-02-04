package com.yc_pay.service;

import com.yc_pay.model.*;
import com.yc_pay.model.dbModels.generated.Tables;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.jooq.Record;
import static com.yc_pay.model.dbModels.generated.Tables.INTENT;
import static org.jooq.impl.DSL.row;


@Singleton
public class DatabaseService {
    public static String userName = "postgres";
    public static String password = "1234";
    public static String url = "jdbc:postgresql://localhost:5432/postgres";


    public static ArrayList<CurrencyCrypto> getCurrenciesCrypto() {

        ArrayList<CurrencyCrypto> currencyCryptoList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = create.select().from(Tables.CURRENCY).fetch();
            conn.close();

            for (Record r : result) {
                ArrayList<Network> networkList = new ArrayList<>();
                String networksDB = r.getValue(Tables.CURRENCY.NETWORK);
                String[] networks = networksDB.split(",");
                for (String s : networks) {
                    Network network = new Network();
                    network.setName(s);
                    networkList.add(network);
                }
                CurrencyCrypto currencyCrypto = new CurrencyCrypto(r.getValue(Tables.CURRENCY.NAME), networkList);
                currencyCryptoList.add(currencyCrypto);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return currencyCryptoList;
    }

    public static void postBuyerIntent(String session_id, IntentRequest intentRequest){

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext postIntent = DSL.using(conn, SQLDialect.POSTGRES);
            postIntent
                    .insertInto(INTENT, INTENT.REQUEST_ID, INTENT.SESSION_ID, INTENT.CURRENCY,
                            INTENT.AMOUNT_FIAT, INTENT.NETWORK, INTENT.MERCHANT_ID, INTENT.WALLET_TO,
                            INTENT.CREATE_DATE, INTENT.CATEGORY, INTENT.STATUS, INTENT.AMOUNT_CRYPTO)
                    .values(intentRequest.getOrder_id(), session_id, intentRequest.getCurrency_crypto(),
                            BigDecimal.valueOf(intentRequest.getAmount_fiat()),
                            intentRequest.getNetwork(), intentRequest.getMerchant_id(),
                            null, LocalDateTime.now(), "Payment", "Created",  BigDecimal.valueOf(intentRequest.getAmount_crypto()))
                    .execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static WalletRequestDB getIntent(String sessionId, String orderId, String merchant_id) {
        WalletRequestDB walletRequestDB = new WalletRequestDB();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext get = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = get.select()
                    .from(INTENT)
                    .where(INTENT.REQUEST_ID.eq(orderId)
                            , INTENT.MERCHANT_ID.eq(merchant_id))
                    .fetch();
            conn.close();

            for (Record r : result) {
                walletRequestDB = new WalletRequestDB(r.getValue(INTENT.NETWORK), r.getValue(INTENT.CURRENCY),
                        r.getValue(INTENT.WALLET_TO), String.valueOf(r.getValue(Optional.ofNullable(INTENT.DESTINATION_TAG).orElse(null))),
                        r.getValue(INTENT.STATUS), String.valueOf(r.getValue(Optional.ofNullable(INTENT.WALLET_ID).orElse(null))),
                        r.getValue(INTENT.AMOUNT_CRYPTO).floatValue());
            }
            return walletRequestDB;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void updateIntentWallet(String merchantId, String requestId, String wallet, int dest_tag) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext upd = DSL.using(conn, SQLDialect.POSTGRES);
            upd.update(INTENT).set(row(INTENT.WALLET_TO, INTENT.DESTINATION_TAG),
                            row(wallet, dest_tag))
                    .where(INTENT.REQUEST_ID.eq(requestId), INTENT.MERCHANT_ID.eq(merchantId))
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateIntentStatus(String merchantId, String requestId, String status) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext upd = DSL.using(conn, SQLDialect.POSTGRES);
            upd.update(INTENT).set(INTENT.STATUS, status)
                    .where(INTENT.REQUEST_ID.eq(requestId), INTENT.MERCHANT_ID.eq(merchantId))
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
