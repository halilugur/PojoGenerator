package com.ugurhalil.factories.impl;

import com.ugurhalil.builder.ClassBuilder;
import com.ugurhalil.enums.DatabaseFieldType;
import com.ugurhalil.factories.Factory;
import com.ugurhalil.structures.Table;
import com.ugurhalil.structures.Column;
import com.ugurhalil.structures.PrimaryKey;
import com.ugurhalil.structures.ForeignKey;

import java.io.FileNotFoundException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author  Halil UĞUR
 * @version 1.0
 * @since   2019-06-06
 */
public class ModelFactoryImpl implements Factory {

    @Override
    public void createStructure(DatabaseMetaData databaseMetaData) throws SQLException {

        ResultSet resultSet = databaseMetaData.getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            Table table = new Table();
            List<Column> columnList = new ArrayList<>();
            List<PrimaryKey> primaryKeyList = new ArrayList<>();
            List<ForeignKey> foreignKeyList = new ArrayList<>();


            String TABLE_NAME = resultSet.getString("TABLE_NAME");
            table.setName(TABLE_NAME);

            ResultSet resultColumns = databaseMetaData.getColumns(null, null, TABLE_NAME, null);

            while (resultColumns.next()) {
                Column column = new Column();
                column.setName(resultColumns.getString("COLUMN_NAME"));
                column.setType(resultColumns.getString("TYPE_NAME"));
                column.setTranslatedType(DatabaseFieldType.valueOf(column.getType()).fieldType());
                column.setSize(resultColumns.getInt("COLUMN_SIZE"));

                columnList.add(column);
            }

            ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null, TABLE_NAME);
            while (primaryKeys.next()) {
                PrimaryKey primaryKey =  new PrimaryKey();
                primaryKey.setTableName(primaryKeys.getString("TABLE_NAME"));
                primaryKey.setColumnName(primaryKeys.getString("COLUMN_NAME"));
                primaryKey.setSequenceSize(primaryKeys.getInt("KEY_SEQ"));
                primaryKeyList.add(primaryKey);
            }

            ResultSet foreignKeys = databaseMetaData.getExportedKeys(null, null, TABLE_NAME);

            while (foreignKeys.next()) {

                ForeignKey foreignKey =  new ForeignKey();
                foreignKey.setTableName(foreignKeys.getString("FKTABLE_NAME"));
                foreignKey.setColumnName(foreignKeys.getString("FKCOLUMN_NAME"));
                foreignKey.setSequenceSize(foreignKeys.getInt("KEY_SEQ"));
                foreignKeyList.add(foreignKey);
            }


            table.setColumns(columnList);
            table.setPrimaryKeys(primaryKeyList);
            table.setForeignKeys(foreignKeyList);

            try {
                ClassBuilder.build(table);
            } catch (FileNotFoundException e) {
                Logger.getLogger(ModelFactoryImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

    }
}
