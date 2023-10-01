package com.yc_pay.service;

import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionResponse;
import jakarta.inject.Singleton;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.yc_pay.model.dbModels.generated.Tables.TRANSACTION;

@Singleton
public class DatabaseService {

    public static String userName = "postgres";
    public static String password = "1234";
    public static String url = "jdbc:postgresql://localhost:5432/TransactionService";

    public ArrayList<Object> createTransactionFromUser (TransactionResponse tx){

        BigDecimal amount = BigDecimal.valueOf(tx.getAmount());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(tx.getCreatedAt(), formatter);

        Transaction txFromUser = new Transaction(tx.getTransactionId(), tx.getMerchantId(), tx.getWalletFrom(),
                tx.getWalletTo(), tx.getCurrency(), amount, tx.getNetwork(), dateTime, "payment", "created");

        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(tx);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            create
                    .insertInto(TRANSACTION, TRANSACTION.TRANSACTIONID, TRANSACTION.MERCHANTID,
                            TRANSACTION.WALLETFROM, TRANSACTION.WALLETTO, TRANSACTION.CURRENCY,
                            TRANSACTION.AMOUNT, TRANSACTION.NETWORK, TRANSACTION.CREATEDATE,
                            TRANSACTION.CATEGORY, TRANSACTION.STATUS)
                    .values(txFromUser.getTransactionId(),txFromUser.getMerchantId(), txFromUser.getWalletFrom(),
                            txFromUser.getWalletTo(), txFromUser.getCurrency(), txFromUser.getAmount(), txFromUser.getNetwork(),
                            txFromUser.getCreatedAt(), txFromUser.getCategory(), txFromUser.getStatus())
                    .execute();
            conn.close();
            arrayList.add("Added");
        }
        catch (Exception e) {
            arrayList.add("Error");
        }
        return arrayList;
    }

    public Transaction getTransactionFromUser(String transactionId, String merchantId){
        Transaction transaction = new Transaction();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result =
                    create
                    .select()
                    .from(TRANSACTION)
                    .where(TRANSACTION.TRANSACTIONID.eq(transactionId),
                            TRANSACTION.MERCHANTID.eq(merchantId))
                    .fetch();
            conn.close();

            for (Record r : result) {
                transaction.setTransactionId(r.getValue(TRANSACTION.TRANSACTIONID));
                transaction.setMerchantId(r.getValue(TRANSACTION.MERCHANTID));
                transaction.setWalletFrom(r.getValue(TRANSACTION.WALLETFROM));
                transaction.setWalletTo(r.getValue(TRANSACTION.WALLETTO));
                transaction.setCurrency(r.getValue(TRANSACTION.CURRENCY));
                transaction.setAmount(r.getValue(TRANSACTION.AMOUNT));
                transaction.setNetwork(r.getValue(TRANSACTION.NETWORK));
                transaction.setCreatedAt(r.getValue(TRANSACTION.CREATEDATE));
                transaction.setCategory(r.getValue(TRANSACTION.CATEGORY));
                transaction.setStatus(r.getValue(TRANSACTION.STATUS));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return transaction;
    }
}
