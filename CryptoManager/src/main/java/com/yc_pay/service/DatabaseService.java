package com.yc_pay.service;

import com.yc_pay.model.PaymentExists;
import com.yc_pay.model.RequestAndSession;
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
import java.util.HashMap;
import java.util.List;

import static com.yc_pay.model.dbModels.generated.Tables.*;
import static org.jooq.impl.DSL.*;

@Singleton
public class DatabaseService {

    public static String userName = "postgres";
    public static String password = "1234";
    public static String url = "jdbc:postgresql://localhost:5432/CryptoManager";


    public static Integer saveXrpHotWallet(String currency, String network, String privateKey, String address) {

        int wallet_id = 0;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext postIntent = DSL.using(conn, SQLDialect.POSTGRES);
            postIntent
                    .insertInto(CRYPTO_WALLETS, CRYPTO_WALLETS.CURRENCY_CRYPTO, CRYPTO_WALLETS.NETWORK,
                            CRYPTO_WALLETS.PRIVATE_KEY, CRYPTO_WALLETS.ADDRESS,
                            CRYPTO_WALLETS.CREATE_DATE, CRYPTO_WALLETS.TYPE)
                    .values(currency, network, privateKey, address, LocalDateTime.now(), "HotWallet")
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

    public static void savePaymentFromBuyer(int wallet_id, float amount_crypto,
                                            int tag, String requestId, String sessionId) {

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext postIntent = DSL.using(conn, SQLDialect.POSTGRES);
            postIntent
                    .insertInto(CRYPTO_PAYMENTS, CRYPTO_PAYMENTS.CRYPTO_WALLET_ID,
                            CRYPTO_PAYMENTS.STATUS, CRYPTO_PAYMENTS.AMOUNT_CRYPTO,
                            CRYPTO_PAYMENTS.DESTINATION_TAG, CRYPTO_PAYMENTS.REQUEST_ID,
                            CRYPTO_PAYMENTS.SESSION_ID)
                    .values(wallet_id, 1, Double.valueOf(amount_crypto), tag, requestId, sessionId)
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Integer> getWalletAndTag(String currency, String network, float amountCrypto) {

        List<Integer> walletTag = new ArrayList<>();

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
            int countMaxWalletId = 100000 + Integer.parseInt(countRowsResult.get(0).getValue("count").toString());

            walletTag.add(maxWalletId);
            walletTag.add(countMaxWalletId);
            return walletTag;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getWalletAddressWithId(int id){
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

    public static String getWalletPrivateKeyWithId(int id){
        String pk = null;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext getWalletPrivateKeyWithId = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record1<String>> result = getWalletPrivateKeyWithId
                    .select(CRYPTO_WALLETS.PRIVATE_KEY)
                    .from(CRYPTO_WALLETS)
                    .where(CRYPTO_WALLETS.ID.eq(id))
                    .fetch();
            conn.close();

            for (Record r : result) {
                pk =  r.getValue(CRYPTO_WALLETS.PRIVATE_KEY);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return pk;
    }

    public static PaymentExists isPaymentExist(Integer destTag, String address) {

        PaymentExists paymentExists = new PaymentExists();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext selectPayment = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record5<Integer, Double, Double, Integer, String>> result = selectPayment
                    .select(CRYPTO_PAYMENTS.ID,
                            CRYPTO_PAYMENTS.AMOUNT_CRYPTO,
                            CRYPTO_PAYMENTS.PAID_AMOUNT,
                            CRYPTO_WALLETS.ID,
                            CRYPTO_PAYMENTS.REQUEST_ID)
                    .from(CRYPTO_PAYMENTS)
                    .leftJoin(CRYPTO_WALLETS).on(CRYPTO_PAYMENTS.CRYPTO_WALLET_ID.eq(CRYPTO_WALLETS.ID))
                    .where(CRYPTO_PAYMENTS.DESTINATION_TAG.eq(destTag),
                            CRYPTO_WALLETS.ADDRESS.eq(address))
                    .fetch();
            conn.close();

            if (!result.isEmpty()) {
                for (Record r : result) {
                    paymentExists.setPaymentId(r.getValue(CRYPTO_PAYMENTS.ID));
                    paymentExists.setRequiredAmount(r.getValue(CRYPTO_PAYMENTS.AMOUNT_CRYPTO));
                    paymentExists.setPaidAmount(r.getValue(CRYPTO_PAYMENTS.PAID_AMOUNT));
                    paymentExists.setWalletId(r.getValue(CRYPTO_WALLETS.ID));
                    paymentExists.setIntentRequestId(r.getValue(CRYPTO_PAYMENTS.REQUEST_ID));
                }
                return paymentExists;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean isHashExists(Integer walletId, String hash) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext selectHash = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record1<Integer>> result =
                    selectHash
                            .select(TRANSACTIONS_HASH.CRYPTO_WALLET_ID)
                            .from(TRANSACTIONS_HASH)
                            .where(TRANSACTIONS_HASH.CRYPTO_WALLET_ID.eq(walletId),
                                    TRANSACTIONS_HASH.HASH.eq(hash))
                    .fetch();
            conn.close();

            return !result.isEmpty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addNewTransactionHash(Integer walletId, String hash, Integer cryptoPaymentId, Double receivedAmount) {

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext AddNewTransactionHash = DSL.using(conn, SQLDialect.POSTGRES);
            AddNewTransactionHash
                    .insertInto(TRANSACTIONS_HASH, TRANSACTIONS_HASH.CRYPTO_WALLET_ID,
                            TRANSACTIONS_HASH.HASH, TRANSACTIONS_HASH.PAYMENT_ID, TRANSACTIONS_HASH.RECEIVED_AMOUNT)
                    .values(walletId, hash, cryptoPaymentId, receivedAmount)
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateOfPaymentPaidAmountAndStatus(Double receivedAmount, int status, int paymentId) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext UpdateOfPaymentPaidAmountAndStatus = DSL.using(conn, SQLDialect.POSTGRES);
            UpdateOfPaymentPaidAmountAndStatus
                    .update(CRYPTO_PAYMENTS)
                    .set(CRYPTO_PAYMENTS.PAID_AMOUNT, receivedAmount)
                    .set(CRYPTO_PAYMENTS.STATUS, status)
                    .where(CRYPTO_PAYMENTS.ID.eq(paymentId))
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateDeliveredFlag(int paymentId, int status) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext updateDeliveredFlag = DSL.using(conn, SQLDialect.POSTGRES);
            updateDeliveredFlag
                    .update(CRYPTO_PAYMENTS)
                    .set(CRYPTO_PAYMENTS.DELIVERED_PAYMENT_API_FLG, status)
                    .where(CRYPTO_PAYMENTS.ID.eq(paymentId))
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<RequestAndSession> listOfRequestsIdByDeliveryStatus(int flg) {
        ArrayList<RequestAndSession> listOfRequestsId = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext listOfRequestsIdByDeliveryStatus = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record2<String, String>> result =
                    listOfRequestsIdByDeliveryStatus
                            .select(CRYPTO_PAYMENTS.REQUEST_ID,
                                    CRYPTO_PAYMENTS.SESSION_ID)
                            .from(CRYPTO_PAYMENTS)
                            .where(CRYPTO_PAYMENTS.DELIVERED_PAYMENT_API_FLG.eq(flg))
                            .fetch();
            conn.close();

            if (!result.isEmpty()) {
                for (Record r : result) {
                    if (r.getValue(CRYPTO_PAYMENTS.REQUEST_ID)!= null && r.getValue(CRYPTO_PAYMENTS.SESSION_ID)!= null){
                        listOfRequestsId.add(new RequestAndSession(
                                r.getValue(CRYPTO_PAYMENTS.REQUEST_ID), r.getValue(CRYPTO_PAYMENTS.SESSION_ID)));
                    }
                }
            }
            return listOfRequestsId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer getPaymentIdByRequestIdAndSessionId(String requestId, String sessionId) {
        Integer paymentId = null;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext getPaymentIdByRequestIdAndSessionId = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record1<Integer>> result =
                    getPaymentIdByRequestIdAndSessionId
                            .select(CRYPTO_PAYMENTS.ID)
                            .from(CRYPTO_PAYMENTS)
                            .where(CRYPTO_PAYMENTS.REQUEST_ID.eq(requestId),
                                    CRYPTO_PAYMENTS.SESSION_ID.eq(sessionId))
                            .fetch();
            conn.close();

            if (!result.isEmpty()) {
                for (Record r : result) {
                    paymentId = r.getValue(CRYPTO_PAYMENTS.ID);
                }
            }
            return paymentId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<Integer, Double> getWalletsToWithdraw() {
        HashMap<Integer, Double> walletsToWithdraw = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext getWalletsToWithdraw = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record2<BigDecimal, Integer>> result = getWalletsToWithdraw
                    .select(sum(TRANSACTIONS_HASH.RECEIVED_AMOUNT).as("sum"),
                            TRANSACTIONS_HASH.CRYPTO_WALLET_ID)
                    .from(TRANSACTIONS_HASH)
                    .groupBy(TRANSACTIONS_HASH.CRYPTO_WALLET_ID)
                    .fetch();

            if (!result.isEmpty()) {
                for (Record r : result) {
                    Double receivedAmount = null;
                    if (r.getValue(String.valueOf("sum")) instanceof Number) {
                        receivedAmount = ((Number) r.getValue(String.valueOf("sum"))).doubleValue();
                    }

                    if (receivedAmount > 10.001) {
                        updateWithdrawFlag(r.getValue(TRANSACTIONS_HASH.CRYPTO_WALLET_ID), 1);
                        walletsToWithdraw.put(r.getValue(TRANSACTIONS_HASH.CRYPTO_WALLET_ID),receivedAmount);
                    }
                }
            }
            return walletsToWithdraw;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void updateWithdrawFlag(Integer cryptoWalletId, Integer flg) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext updateWithdrawFlag = DSL.using(conn, SQLDialect.POSTGRES);
            updateWithdrawFlag
                    .update(TRANSACTIONS_HASH)
                    .set(TRANSACTIONS_HASH.WITHDRAW_FLG, flg)
                    .where(TRANSACTIONS_HASH.CRYPTO_WALLET_ID.eq(cryptoWalletId))
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
