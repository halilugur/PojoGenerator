package com.ugurhalil.structures;

import java.util.List;

/**
 * @author  Halil UĞUR
 * @version 1.0
 * @since   2019-06-06
 */
public class Table {

    private String name;
    private List<Column> columns;
    private List<PrimaryKey> primaryKeys;
    private List<ForeignKey> foreignKeys;
    private List<ForeignKey> importForeignKeys;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<PrimaryKey> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<PrimaryKey> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public List<ForeignKey> getImportForeignKeys() {
        return importForeignKeys;
    }

    public void setImportForeignKeys(List<ForeignKey> importForeignKeys) {
        this.importForeignKeys = importForeignKeys;
    }

    @Override
    public String toString() {

        System.out.println("--------------------------");
        System.out.println(this.getName());
        for (Column column : this.getColumns()) {
            System.out.print(column.getName() + "   ");
            System.out.print(column.getType() + "    ");
            System.out.print(column.getTranslatedType() + "    ");
            System.out.println(column.getSize());
        }

        System.out.println("***Primary Key***");
        for (PrimaryKey primaryKey : this.getPrimaryKeys()) {
            System.out.print(primaryKey.getTableName() + "   ");
            System.out.print(primaryKey.getColumnName() + "   ");
            System.out.println(primaryKey.getSequenceSize());
        }
        System.out.println("***Foreign Key***");
        for (ForeignKey foreignKey : this.getForeignKeys()) {
            System.out.print(foreignKey.getTableName() + "   ");
            System.out.print(foreignKey.getColumnName() + "   ");
            System.out.println(foreignKey.getSequenceSize());
        }
        System.out.println("******");
        System.out.println("--------------------------");

        return super.toString();
    }
}
