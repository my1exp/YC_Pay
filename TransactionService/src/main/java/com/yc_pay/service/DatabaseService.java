package com.yc_pay.service;

import com.yc_pay.model.Entry;
import com.yc_pay.model.Transaction;
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
import static org.jooq.impl.DSL.max;

@Singleton
@Slf4j(topic = "DatabaseService")
public class DatabaseService {

    static ApplicationContext applicationContext = ApplicationContext.run();
    static Environment environment = applicationContext.getEnvironment();
    static String url = environment.getProperty("db.url", String.class).get();
    static String userName = environment.getProperty("db.username", String.class).get();
    static String password = environment.getProperty("db.password", String.class).get();

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

    public Double checkAvailablePaymentAmount(String merchantId) {
        double availableAmount = 0.0;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext checkAvailablePaymentAmount = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record1<Double>> result =
                    checkAvailablePaymentAmount
                            .select(ENTRIES.AMOUNT)
                            .from(ENTRIES)
                            .leftJoin(POCKET).on(ENTRIES.POCKET.eq(POCKET.POCKET_))
                            .where(POCKET.MERCHANT_ID.eq(merchantId))
                            .fetch();
            conn.close();

            for (Record r : result) {
                availableAmount += (r.getValue(ENTRIES.AMOUNT));
            }
            return availableAmount;
        }
        catch (Exception e) {
            log.error("Failed to get transactions" + e.getMessage());
            return null;
        }
    }

    public void createEntryForPaymentTransaction(String merchantId, double amountFiat) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext createEntryForExchangeIdentifiedTransaction = DSL.using(conn, SQLDialect.POSTGRES);

            @NotNull Result<Record1<String>> result =
                    createEntryForExchangeIdentifiedTransaction
                            .select(POCKET.POCKET_)
                            .from(POCKET)
                            .where(POCKET.MERCHANT_ID.eq(merchantId))
                            .fetch();

            String pocket = result.get(0).getValue(POCKET.POCKET_);

            createEntryForExchangeIdentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values(pocket, -1 * amountFiat, "USDT", "GOP", LocalDateTime.now())
                    .execute();

            createEntryForExchangeIdentifiedTransaction
                    .insertInto(ENTRIES,
                            ENTRIES.POCKET,
                            ENTRIES.AMOUNT,
                            ENTRIES.CURRENCY,
                            ENTRIES.REFERENCE,
                            ENTRIES.CREATED_DTTM)
                    .values("GOP", amountFiat, "USDT", pocket, LocalDateTime.now())
                    .execute();
        } catch (SQLException ex) {
            log.error("Failed to create an entry for unidentified transaction" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public String checkPocketExistsByMerchantId(String merchantId) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext checkPocketExists = DSL.using(conn, SQLDialect.POSTGRES);

            @NotNull Result<Record1<String>> result =
                    checkPocketExists
                            .select(POCKET.POCKET_)
                            .from(POCKET)
                            .where(POCKET.MERCHANT_ID.eq(merchantId))
                            .fetch();
            conn.close();

            if (!result.isEmpty()) {
                return result.get(0).getValue(POCKET.POCKET_);
            }else{
                return null;
            }

        } catch (Exception ex) {
            log.error("Failed to check pocket exists" + ex.getMessage());
            return null;
        }
    }

    public void createPocketForMerchant(String merchantId) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext createPocketForMerchant = DSL.using(conn, SQLDialect.POSTGRES);

            @NotNull Result<Record1<Integer>> result =
                    createPocketForMerchant
                            .select(max(POCKET.ID).as(POCKET.ID))
                            .from(POCKET)
                            .fetch();

            String newPocket = "W" + (result.get(0).getValue(POCKET.ID) + 500);

            createPocketForMerchant
                    .insertInto(POCKET,
                            POCKET.POCKET_,
                            POCKET.MERCHANT_ID)
                    .values(newPocket, merchantId)
                    .execute();

        } catch (SQLException ex) {
            log.error("Failed to create an entry for unidentified transaction" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<Entry> getEntriesForPocket(String pocket) {
        ArrayList<Entry> entries = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext checkPocketExistsByPocket = DSL.using(conn, SQLDialect.POSTGRES);
            @NotNull Result<Record5<String, Double, String, String, LocalDateTime>> result =
                    checkPocketExistsByPocket
                            .select(ENTRIES.POCKET,
                                    ENTRIES.AMOUNT,
                                    ENTRIES.CURRENCY,
                                    ENTRIES.REFERENCE,
                                    ENTRIES.CREATED_DTTM)
                            .from(ENTRIES)
                            .where(ENTRIES.POCKET.eq(pocket))
                            .fetch();
            conn.close();

            if (!result.isEmpty()) {
                for (Record r : result) {
                    Entry entry = new Entry(r.getValue(ENTRIES.POCKET), r.getValue(ENTRIES.AMOUNT),
                            r.getValue(ENTRIES.CURRENCY), r.getValue(ENTRIES.REFERENCE),
                            String.valueOf(r.getValue(ENTRIES.CREATED_DTTM)));
                    entries.add(entry);
                }
            }
            return entries;

        } catch (Exception ex) {
            log.error("Failed to check pocket exists" + ex.getMessage());
            return null;
        }
    }

    public Boolean checkPocketExistsByPocket(String pocket) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext checkPocketExistsByPocket = DSL.using(conn, SQLDialect.POSTGRES);

            @NotNull Result<Record1<String>> result =
                    checkPocketExistsByPocket
                            .select(POCKET.POCKET_)
                            .from(POCKET)
                            .where(POCKET.POCKET_.eq(pocket))
                            .fetch();
            conn.close();

            if (result.isEmpty()) {
                return false;
            }else{
                return true;
            }

        } catch (Exception ex) {
            log.error("Failed to check pocket exists" + ex.getMessage());
            return false;
        }
    }
}