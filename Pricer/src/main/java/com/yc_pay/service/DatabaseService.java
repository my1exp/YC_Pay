package com.yc_pay.service;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import static com.yc_pay.model.dbModels.generated.Tables.CRYPTOPRICE;

@Singleton
public class DatabaseService {

    public static String userName = "postgres";
    public static String password = "1234";
    public static String url = "jdbc:postgresql://localhost:5432/Pricer";

    public static BigDecimal getPrice(String currencyCrypto) {

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = create
                    .select()
                    .from(CRYPTOPRICE)
                    .where(CRYPTOPRICE.TICKER.eq(currencyCrypto))
                    .fetch();
            conn.close();

            BigDecimal price = null;

            for (Record r : result) {
                price= r.getValue(CRYPTOPRICE.PRICE);
            }
            return price;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
