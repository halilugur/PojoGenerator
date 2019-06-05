package com.ugurhalil;

import com.ugurhalil.database.DatabaseConnection;
import com.ugurhalil.factories.impl.ModelFactoryImpl;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author  Halil UĞUR
 * @version 1.0
 * @since   2019-06-06
 */
public class PojoGenerator {

    public static void main(String[] args) {

        DatabaseMetaData databaseMetaData = DatabaseConnection.connection();

        ModelFactoryImpl modelFactory = new ModelFactoryImpl();

        if (databaseMetaData != null) {
            try {
                modelFactory.createStructure(databaseMetaData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
