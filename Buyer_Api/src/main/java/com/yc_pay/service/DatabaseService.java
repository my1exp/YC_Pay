package com.yc_pay.service;

import com.yc_pay.model.dbModels.generated.Tables;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.Record;


@Singleton
public class DatabaseService {
    public static String userName = "postgres";
    public static String password = "1234";
    public static String url = "jdbc:postgresql://localhost:5432/postgres";


    public static String getCurrenciesCrypto() {

        StringBuilder output = new StringBuilder();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = create.select().from(Tables.CURRENCY).fetch();
            conn.close();

            for (Record r : result){
                String currency = r.getValue(Tables.CURRENCY.NAME);
                String network = r.getValue(Tables.CURRENCY.NETWORK);
                output.append("Currency: ").append(currency).append(" network: ").append(network);
            }
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
