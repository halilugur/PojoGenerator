package com.ugurhalil.builder;

import com.ugurhalil.database.DatabaseConnection;
import com.ugurhalil.structures.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * @author  Halil UĞUR
 * @version 1.0
 * @since   2019-06-06
 */
public class ClassBuilder {

    public static void build(Table table) throws FileNotFoundException {

        Properties prop = DatabaseConnection.readPropertiesData();

        String PACKAGE_PATH = prop.getProperty("model.package.name");
        String MODELS_PATH = prop.getProperty("generated.java.files.path") + "/";

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("package ").append(PACKAGE_PATH).append(";");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("/**");
        stringBuilder.append(" *");
        stringBuilder.append(" *This file auto generate.");
        stringBuilder.append(" *");
        stringBuilder.append(" */");
        stringBuilder.append("\n");
        stringBuilder.append("public class ").append(capitalize(table.getName())).append(" {");
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        table.getColumns().forEach(column -> {
            stringBuilder.append("    private ").append(column.getTranslatedType()).append(" ").append(column.getName()).append(";").append("\n");
        });

        table.getForeignKeys().forEach(foreignKey -> {
            stringBuilder.append("    private java.util.Set<").append(PACKAGE_PATH).append(".").append(capitalize(foreignKey.getTableName())).append("> ").append(foreignKey.getTableName()).append("s;").append("\n");
        });

        table.getColumns().forEach(column -> {
            stringBuilder.append("\n");
            stringBuilder.append("    public void").append(" set").append(capitalize(column.getName())).append(" (").append(column.getTranslatedType()).append(" ").append(column.getName()).append(") {").append("\n");
            stringBuilder.append("        this.").append(column.getName()).append(" = ").append(column.getName()).append(";");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("    public ").append(column.getTranslatedType()).append(" get").append(capitalize(column.getName())).append(" () {").append("\n");
            stringBuilder.append("        return this.").append(column.getName()).append(";");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
        });

        table.getForeignKeys().forEach(foreignKey -> {
            stringBuilder.append("\n");
            stringBuilder.append("    public void").append(" set").append(capitalize(foreignKey.getTableName())).append("s (java.util.Set<").append(PACKAGE_PATH).append(".").append(capitalize(foreignKey.getTableName())).append("> ").append(foreignKey.getTableName()).append("s) {").append("\n");
            stringBuilder.append("        this.").append(foreignKey.getTableName()).append("s = ").append(foreignKey.getTableName()).append("s;");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("    public java.util.Set<").append(PACKAGE_PATH).append(".").append(capitalize(foreignKey.getTableName())).append("> get").append(capitalize(foreignKey.getTableName())).append("s () {").append("\n");
            stringBuilder.append("        return this.").append(foreignKey.getTableName()).append("s;");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
        });

        stringBuilder.append("\n");
        stringBuilder.append("}");


        File createFolder = new File(MODELS_PATH);
        boolean isCreated = createFolder.mkdirs();

        if (isCreated) {
            System.out.println("--------------------------");
            System.out.println("Create Folder For Pojo Files: " + MODELS_PATH);
            System.out.println("--------------------------");
        }

        File file = new File(MODELS_PATH + capitalize(table.getName()) + ".java");
        PrintWriter writer = new PrintWriter(file);
        writer.println(stringBuilder.toString());
        writer.close();
    }

    public static String capitalize(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }
}
