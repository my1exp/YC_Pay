package com.lk;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Singleton
public class DataBase {
    private static final String userName = "postgres";
    private static final String password = "24455742ikFT67uiRt453";
    private static final String url = "jdbc:postgresql://localhost:5432/usercabinet";

    public DSLContext connectionDataBase(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return DSL.using(conn, SQLDialect.POSTGRES);
    }

}
