package com.yc_pay.service;

import com.yc_pay.model.*;
import com.yc_pay.model.dbModels.generated.Tables;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

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

    public static Intent getIntentByOrderIdAndMerchantId(String sessionId, String orderId, String merchant_id) {
        Intent intent = new Intent();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext get = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = get.select()
                    .from(INTENT)
                    .where(INTENT.REQUEST_ID.eq(orderId)
                            , INTENT.MERCHANT_ID.eq(merchant_id))
                    .fetch();
            conn.close();

            for (Record r : result) {
                intent = new Intent(r.getValue(INTENT.NETWORK), r.getValue(INTENT.CURRENCY),
                        r.getValue(INTENT.WALLET_TO), String.valueOf(r.getValue(Optional.ofNullable(INTENT.DESTINATION_TAG).orElse(null))),
                        r.getValue(INTENT.STATUS), String.valueOf(r.getValue(Optional.ofNullable(INTENT.WALLET_ID).orElse(null))),
                        r.getValue(INTENT.AMOUNT_CRYPTO).floatValue(), r.getValue(INTENT.AMOUNT_FIAT).floatValue(),
                        r.getValue(INTENT.MERCHANT_ID), r.getValue(INTENT.REQUEST_ID), r.getValue(INTENT.SESSION_ID));
            }
            return intent;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void updateIntentWallet(String merchantId, String requestId, int walletId, String wallet, int dest_tag) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext upd = DSL.using(conn, SQLDialect.POSTGRES);
            upd.update(INTENT)
                    .set(row(INTENT.WALLET_TO, INTENT.DESTINATION_TAG, INTENT.WALLET_ID),
                            row(wallet, dest_tag, walletId))
                    .where(INTENT.REQUEST_ID.eq(requestId), INTENT.MERCHANT_ID.eq(merchantId))
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateIntentStatusByMerchantIdAndRequestId(String merchantId, String requestId, String status) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext upd = DSL.using(conn, SQLDialect.POSTGRES);
            upd.update(INTENT).set(INTENT.STATUS, status)
                    .where(INTENT.REQUEST_ID.eq(requestId), INTENT.MERCHANT_ID.eq(merchantId))
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateIntentStatusBySessionIdAndRequestId(String sessionId, String requestId, String status) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext updateIntentStatusBySessionIdAndRequestId = DSL.using(conn, SQLDialect.POSTGRES);
            updateIntentStatusBySessionIdAndRequestId.
                    update(INTENT).set(INTENT.STATUS, status)
                    .where(INTENT.REQUEST_ID.eq(requestId), INTENT.SESSION_ID.eq(sessionId))
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MerchantPayment> getMerchantPayments(String merchantId) {

        ArrayList<MerchantPayment> merchantPaymentsList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext get = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = get.select()
                    .from(INTENT)
                    .where(INTENT.MERCHANT_ID.eq(merchantId),
                            INTENT.STATUS.eq("Paid"))
                    .fetch();
            conn.close();

            for (Record r : result) {
                MerchantPayment merchantPayment = new MerchantPayment(r.getValue(INTENT.MERCHANT_ID),
                        r.getValue(INTENT.REQUEST_ID), r.getValue(INTENT.CURRENCY),
                        r.getValue(INTENT.AMOUNT_CRYPTO), "USD", r.getValue(INTENT.AMOUNT_FIAT),
                        r.getValue(INTENT.CREATE_DATE));
                merchantPaymentsList.add(merchantPayment);
            }
            return merchantPaymentsList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getIntentByOrderIdAndSessionId(String intentId, String sessionId) {
        String status = null;

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext get = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record1<String>> result = get.
                    select(INTENT.STATUS)
                    .from(INTENT)
                    .where(INTENT.REQUEST_ID.eq(intentId), INTENT.SESSION_ID.eq(sessionId))
                    .fetch();
            conn.close();

            for (Record r : result) {
                status = r.getValue(INTENT.STATUS);
            }
            return status;
        }
        catch (Exception e) {
            return null;
        }
    }
}
