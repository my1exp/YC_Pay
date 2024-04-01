package com.yc_pay.service;

import com.yc_pay.Api.CryptoCurrency;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import static com.yc_pay.model.dbModels.generated.Tables.CRYPTOPRICE;
import static org.jooq.impl.DSL.row;

@Singleton
@Slf4j
public class DatabaseService {

    static ApplicationContext applicationContext = ApplicationContext.run();
    static Environment environment = applicationContext.getEnvironment();
    static String url = environment.getProperty("db.url", String.class).get();
    static String userName = environment.getProperty("db.username", String.class).get();
    static String password = environment.getProperty("db.password", String.class).get();

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
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static void updatePrices(ArrayList<CryptoCurrency> cryptoCurrencies){

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
                DSLContext dslContext = DSL.using(conn, SQLDialect.POSTGRES);
            for (CryptoCurrency cryptoCurrency : cryptoCurrencies) {

                dslContext
                        .update(CRYPTOPRICE)
                        .set(row(CRYPTOPRICE.PRICE, CRYPTOPRICE.UPDATE_DTTM),
                                row(cryptoCurrency.getPrice(), cryptoCurrency.getDateTime()))
                        .where(CRYPTOPRICE.NAME.eq(cryptoCurrency.getName()))
                        .execute();
            }
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
    }
}
