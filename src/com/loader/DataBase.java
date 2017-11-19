package com.loader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by GUNGUI on 10/18/2017.
 */
public class DataBase {
    private Connection connection;
    private Statement statement;

    public DataBase() {

        String CLASS_NAME = "org.sqlite.JDBC";
        String URL = "jdbc:sqlite:src/Resource/DB/data.db";
        try {
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(URL);

            statement = connection.createStatement();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    /**
     * Simple helper function
     *
     * @param sql Query
     * @return result of that query
     */

    public  ResultSet query(String sql){
        if(sql == null){
            throw new IllegalArgumentException("SQL query must't be null");
        }
        try{
            return statement.executeQuery(sql);

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }
    public void insert(String sql){
        if(sql == null){
            throw new IllegalArgumentException("SQL query must't be null");
        }
        try{
           statement.executeUpdate(sql);
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
