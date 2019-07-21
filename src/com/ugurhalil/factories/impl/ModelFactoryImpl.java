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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Halil UĞUR
 * @version 1.0
 * @since 2019-06-06
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
            List<ForeignKey> importForeignKeyList = new ArrayList<>();


            String TABLE_NAME = resultSet.getString("TABLE_NAME");
            table.setName(TABLE_NAME);

            ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null, TABLE_NAME);
            while (primaryKeys.next()) {
                PrimaryKey primaryKey = new PrimaryKey();
                primaryKey.setTableName(primaryKeys.getString("TABLE_NAME"));
                primaryKey.setColumnName(primaryKeys.getString("COLUMN_NAME"));
                primaryKey.setSequenceSize(primaryKeys.getInt("KEY_SEQ"));
                primaryKeyList.add(primaryKey);
            }

            ResultSet importForeignKeys = databaseMetaData.getImportedKeys(null, null, TABLE_NAME);
            while (importForeignKeys.next()) {
                ForeignKey foreignKey = new ForeignKey();
                foreignKey.setTableName(importForeignKeys.getString("PKTABLE_NAME"));
                foreignKey.setColumnName(importForeignKeys.getString("FKCOLUMN_NAME"));
                foreignKey.setSequenceSize(importForeignKeys.getInt("KEY_SEQ"));
                importForeignKeyList.add(foreignKey);
            }

            ResultSet foreignKeys = databaseMetaData.getExportedKeys(null, null, TABLE_NAME);
            while (foreignKeys.next()) {
                ForeignKey foreignKey = new ForeignKey();
                foreignKey.setTableName(foreignKeys.getString("FKTABLE_NAME"));
                foreignKey.setColumnName(foreignKeys.getString("FKCOLUMN_NAME"));
                foreignKey.setSequenceSize(foreignKeys.getInt("KEY_SEQ"));
                foreignKeyList.add(foreignKey);
            }

            ResultSet resultColumns = databaseMetaData.getColumns(null, null, TABLE_NAME, null);
            while (resultColumns.next()) {
                Column column = new Column();
                column.setName(resultColumns.getString("COLUMN_NAME"));
                column.setType(resultColumns.getString("TYPE_NAME"));
                column.setTranslatedType(DatabaseFieldType.valueOf(column.getType()).fieldType());
                column.setTranslatedShortType(getShortTypeName(column.getTranslatedType()));
                column.setSize(resultColumns.getInt("COLUMN_SIZE"));

                boolean isPrimary = primaryKeyList.stream().anyMatch(primaryKey -> primaryKey.getColumnName().equals(column.getName()));
                boolean isForeign = importForeignKeyList.stream().anyMatch(foreignKey -> foreignKey.getColumnName().equals(column.getName()));

                if (!isPrimary && !isForeign) {
                    columnList.add(column);
                } else {
                    if (isPrimary) {
                        Objects.requireNonNull(primaryKeyList.stream().filter(primaryKey -> primaryKey.getColumnName().equals(column.getName())).findFirst().orElse(null)).setColumn(column);
                    } else {
                        Objects.requireNonNull(importForeignKeyList.stream().filter(foreignKey -> foreignKey.getColumnName().equals(column.getName())).findFirst().orElse(null)).setColumn(column);
                    }
                }
            }

            table.setColumns(columnList);
            table.setPrimaryKeys(primaryKeyList);
            table.setForeignKeys(foreignKeyList);
            table.setImportForeignKeys(importForeignKeyList);

            try {
                ClassBuilder.build(table);
            } catch (FileNotFoundException e) {
                Logger.getLogger(ModelFactoryImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

    }

    private String getShortTypeName(String longTypeName) {
        if (longTypeName.contains(".")) {
            String[] split = longTypeName.split("\\.");
            return split[split.length - 1];
        }
        return longTypeName;
    }
}
