package com.ugurhalil.factories;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author  Halil UĞUR
 * @version 1.0
 * @since   2019-06-06
 */
public interface Factory {

    void createStructure(DatabaseMetaData databaseMetaData) throws SQLException;

}
