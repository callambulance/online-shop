package com.codecool.shop.dao.implementation;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {


    public static DataSource connect() throws SQLException, IOException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        FileInputStream fis=new FileInputStream("src/main/resources/connection.properties");
        Properties p=new Properties ();
        p.load (fis);
        String dbName= (String) p.get ("database");
        String user = (String) p.get ("user");
        String password = (String) p.get ("password");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

//        System.out.println("Trying to connect");
        dataSource.getConnection().close();
//        System.out.println("Connection ok.");

        return dataSource;
    }

}

