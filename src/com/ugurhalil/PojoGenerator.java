package com.ugurhalil;

import com.ugurhalil.database.DatabaseConnection;
import com.ugurhalil.factories.impl.ModelFactoryImpl;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author Halil UĞUR
 * @version 1.0
 * @since 2019-06-06
 */
public class PojoGenerator {

    public static void main(String[] args) {

        if (args.length > 0) {
            DatabaseMetaData databaseMetaData = DatabaseConnection.connection(args[0]);

            ModelFactoryImpl modelFactory = new ModelFactoryImpl();

            if (databaseMetaData != null) {
                try {
                    modelFactory.createStructure(databaseMetaData, args[0]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Please use the pojo properties file. Can you check file on the git repo.");
        }



    }
}
