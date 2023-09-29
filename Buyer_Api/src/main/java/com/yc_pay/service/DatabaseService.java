package com.yc_pay.service;

import com.yc_pay.model.CurrencyCrypto;
import com.yc_pay.model.Network;
import com.yc_pay.model.dbModels.generated.Tables;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import org.jooq.Record;


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
}
