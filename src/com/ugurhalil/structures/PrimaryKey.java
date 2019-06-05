package com.ugurhalil.structures;

/**
 * @author  Halil UĞUR
 * @version 1.0
 * @since   2019-06-06
 */
public class PrimaryKey {
    
    private String columnName;
    private String tableName;
    private Integer sequenceSize;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getSequenceSize() {
        return sequenceSize;
    }

    public void setSequenceSize(Integer sequenceSize) {
        this.sequenceSize = sequenceSize;
    }
}
