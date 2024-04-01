package com.yc_pay.service;

import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionResponse;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static com.yc_pay.model.dbModels.generated.Tables.*;

@Singleton
@Slf4j(topic = "DatabaseService")
public class DatabaseService {

    static ApplicationContext applicationContext = ApplicationContext.run();
    static Environment environment = applicationContext.getEnvironment();
    static String url = environment.getProperty("db.url", String.class).get();
    static String userName = environment.getProperty("db.username", String.class).get();
    static String password = environment.getProperty("db.password", String.class).get();



    public static ArrayList<TransactionResponse> getTransactionFromUser(String merchantId){
        ArrayList<TransactionResponse> transactions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result =
                    create
                    .select()
                    .from(TRANSACTION)
                    .where(TRANSACTION.MERCHANT_ID.eq(merchantId))
                    .fetch();
            conn.close();

            for (Record r : result) {
                TransactionResponse transaction = new TransactionResponse();
                transaction.setPayment_id(r.getValue(TRANSACTION.PAYMENT_ID));
                transaction.setCurrency_crypto(r.getValue(TRANSACTION.CURRENCY_CRYPTO));
                transaction.setAmount_crypto(r.getValue(TRANSACTION.AMOUNT_CRYPTO));
                transaction.setCurrency_fiat(r.getValue(TRANSACTION.CURRENCY_FIAT));
                transaction.setPayment_dttm(String.valueOf(r.getValue(TRANSACTION.PAYMENT_DTTM)));
                transactions.add(transaction);
            }
        }
        catch (Exception e) {
            log.error("Failed to get transactions" + e.getMessage());
        }
        return transactions;
    }

    public static void createEntryForAnyTransaction(double amountCrypto, String currencyCrypto) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext createEntryForAnyTransaction = DSL.using(conn, SQLDialect.POSTGRES);
            createEntryForAnyTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("GIP", -1 * amountCrypto, currencyCrypto, "W1", LocalDateTime.now())
                    .execute();

            createEntryForAnyTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("W1", amountCrypto, currencyCrypto, "GIP", LocalDateTime.now())
                    .execute();
            } catch (SQLException ex) {
            log.error("Failed to create an entry for any transaction" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public static void createEntryForUnidentifiedTransaction(double amountCrypto, String currencyCrypto) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext createEntryForUnidentifiedTransaction = DSL.using(conn, SQLDialect.POSTGRES);
            createEntryForUnidentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("W1", -1 * amountCrypto, currencyCrypto, "GBCP", LocalDateTime.now())
                    .execute();


            createEntryForUnidentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("GBCP", amountCrypto, currencyCrypto, "W1", LocalDateTime.now())
                    .execute();
        } catch (SQLException ex) {
            log.error("Failed to create an entry for unidentified transaction" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public static void createEntryForIdentifiedTransaction(Transaction transaction) {
        String pocket = null;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext createEntryForIdentifiedTransaction = DSL.using(conn, SQLDialect.POSTGRES);

            @NotNull Result<Record1<String>> result =
                    createEntryForIdentifiedTransaction
                            .select(POCKET.POCKET_)
                            .from(POCKET)
                            .where(POCKET.MERCHANT_ID.eq(transaction.getMerchant_id()))
                            .fetch();

            for (Record r : result) {
                pocket = r.getValue(POCKET.POCKET_);
            }

            if (pocket != null) {
                createEntryForIdentifiedTransaction
                        .insertInto(ENTRIES,
                                ENTRIES.POCKET,
                                ENTRIES.AMOUNT,
                                ENTRIES.CURRENCY,
                                ENTRIES.REFERENCE,
                                ENTRIES.CREATED_DTTM)
                        .values("W1", -1 * transaction.getPaid_amount_crypto(),
                                transaction.getCurrency_crypto(), "W70", LocalDateTime.now())
                        .execute();

                createEntryForIdentifiedTransaction
                        .insertInto(ENTRIES,
                                ENTRIES.POCKET,
                                ENTRIES.AMOUNT,
                                ENTRIES.CURRENCY,
                                ENTRIES.REFERENCE,
                                ENTRIES.CREATED_DTTM)
                        .values("W70", transaction.getPaid_amount_crypto(),
                                transaction.getCurrency_crypto(), "W1", LocalDateTime.now())
                        .execute();

                createEntryForIdentifiedTransaction
                        .insertInto(ENTRIES,
                                ENTRIES.POCKET,
                                ENTRIES.AMOUNT,
                                ENTRIES.CURRENCY,
                                ENTRIES.REFERENCE,
                                ENTRIES.CREATED_DTTM)
                        .values("W70", -1 * transaction.getAmount_fiat(),
                                transaction.getCurrency_fiat(), pocket, LocalDateTime.now())
                        .execute();

                createEntryForIdentifiedTransaction
                        .insertInto(ENTRIES,
                                ENTRIES.POCKET,
                                ENTRIES.AMOUNT,
                                ENTRIES.CURRENCY,
                                ENTRIES.REFERENCE,
                                ENTRIES.CREATED_DTTM)
                        .values(pocket, transaction.getAmount_fiat(),
                                transaction.getCurrency_fiat(), "W70", LocalDateTime.now())
                        .execute();

                double overpayment = transaction.getPaid_amount_crypto() - transaction.getRequired_amount_crypto();

                if (overpayment > 0){
                    createEntryForIdentifiedTransaction
                            .insertInto(ENTRIES,
                                    ENTRIES.POCKET,
                                    ENTRIES.AMOUNT,
                                    ENTRIES.CURRENCY,
                                    ENTRIES.REFERENCE,
                                    ENTRIES.CREATED_DTTM)
                            .values("W70", -1 * overpayment, transaction.getCurrency_crypto(), "GBCP", LocalDateTime.now())
                            .execute();

                    createEntryForIdentifiedTransaction
                            .insertInto(ENTRIES,
                                    ENTRIES.POCKET,
                                    ENTRIES.AMOUNT,
                                    ENTRIES.CURRENCY,
                                    ENTRIES.REFERENCE,
                                    ENTRIES.CREATED_DTTM)
                            .values("GBCP", overpayment, transaction.getCurrency_crypto(),"W70" , LocalDateTime.now())
                            .execute();
                    log.info("Overpayment = " + overpayment + " " + transaction.getCurrency_crypto());
                }
            }
        } catch (SQLException ex) {
            log.error("Failed to create an entry for identified transaction" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public static void createEntryForExchangeUnidentifiedTransaction(double exchangedAmountCrypto, String exchangedCurrencyCrypto,
                                                              String receivedCurrencyFiat, double receivedAmountFiat) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext createEntryForExchangeUnidentifiedTransaction = DSL.using(conn, SQLDialect.POSTGRES);
            createEntryForExchangeUnidentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("GBCP", -1 * exchangedAmountCrypto, exchangedCurrencyCrypto, "GCP", LocalDateTime.now())
                    .execute();

            createEntryForExchangeUnidentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("GCP", receivedAmountFiat, receivedCurrencyFiat, "GBCP", LocalDateTime.now())
                    .execute();
        } catch (SQLException ex) {
            log.error("Failed to create an entry for unidentified transaction" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public static void createEntryForExchangeIdentifiedTransaction(double exchangedAmountCrypto, String exchangedCurrencyCrypto,
                                                            String receivedCurrencyFiat, double receivedAmountFiat) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext createEntryForExchangeIdentifiedTransaction = DSL.using(conn, SQLDialect.POSTGRES);
            createEntryForExchangeIdentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("W70", -1 * exchangedAmountCrypto, exchangedCurrencyCrypto, "W70", LocalDateTime.now())
                    .execute();

            createEntryForExchangeIdentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("W70", receivedAmountFiat, receivedCurrencyFiat, "W70", LocalDateTime.now())
                    .execute();
        } catch (SQLException ex) {
            log.error("Failed to create an entry for unidentified transaction" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public static void createEntryForExchangeDifference(double exchangeDifference) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext createEntryForExchangeIdentifiedTransaction = DSL.using(conn, SQLDialect.POSTGRES);
            createEntryForExchangeIdentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("W70", -1 * exchangeDifference, "USDT", "GCP", LocalDateTime.now())
                    .execute();

            createEntryForExchangeIdentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("GCP", exchangeDifference, "USDT", "W70", LocalDateTime.now())
                    .execute();
        } catch (SQLException ex) {
            log.error("Failed to create an entry for unidentified transaction" + ex.getMessage());
            throw new RuntimeException(ex);
        }

    }
}