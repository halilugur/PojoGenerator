package com.ugurhalil.database;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author  Halil UĞUR
 * @version 1.0
 * @since   2019-06-06
 */
public class DatabaseConnection {

    public static final String USER_DIR = System.getProperty("user.dir");

    public static DatabaseMetaData connection() {
        Connection connection;
        try {

            Properties properties = readPropertiesData();

            String DB_URL = properties.getProperty("database.url");
            String DRIVER = properties.getProperty("database.driver");
            String USER = properties.getProperty("database.user");
            String PASS = properties.getProperty("database.pass");

            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            return connection.getMetaData();

        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static Properties readPropertiesData() {
        String propertiesPath = USER_DIR + "/pojo.properties";
        try (InputStream input = new FileInputStream(propertiesPath)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
