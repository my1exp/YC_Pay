package com.yc_pay.service;

import com.yc_pay.model.TransactionResponse;
import jakarta.inject.Singleton;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import static com.yc_pay.model.dbModels.generated.Tables.TRANSACTION;

@Singleton
public class DatabaseService {

    public static String userName = "postgres";
    public static String password = "1234";
    public static String url = "jdbc:postgresql://localhost:5432/TransactionService";

    public ArrayList<TransactionResponse> getTransactionFromUser(String merchantId){
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
            e.printStackTrace();
        }
        return transactions;
    }
}
