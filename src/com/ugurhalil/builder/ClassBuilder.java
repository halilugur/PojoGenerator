package com.ugurhalil.builder;

import com.ugurhalil.database.DatabaseConnection;
import com.ugurhalil.structures.Table;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Halil UĞUR
 * @version 1.0
 * @since 2019-06-06
 */
public class ClassBuilder {

    public static void build(Table table, String path) throws FileNotFoundException {

        Properties prop = DatabaseConnection.readPropertiesData(path);

        boolean isActiveHibernate = Boolean.parseBoolean(prop.getProperty("hibernate.is.active"));
        String fetchType = prop.getProperty("hibernate.fetchType");
        String cascadeType = prop.getProperty("hibernate.cascadeType");
        String PACKAGE_PATH = prop.getProperty("model.package.name");
        String MODELS_PATH = prop.getProperty("generated.java.files.path") + "/";

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("package ").append(PACKAGE_PATH).append(";");
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        stringBuilder.append("import java.io.Serializable;\n");

        table.getColumns().stream().filter(column -> column.getTranslatedType().contains(".")).collect(Collectors.toSet()).forEach(column -> {
            stringBuilder.append("import ").append(column.getTranslatedType()).append(";").append("\n");
        });

        if (CollectionUtils.isNotEmpty(table.getForeignKeys())) {
            stringBuilder.append("import java.util.List;").append("\n");
        }

        stringBuilder.append("\n");
        stringBuilder.append("/**\n");
        stringBuilder.append(" *\n");
        stringBuilder.append(" * This file auto generate by UGURSOFT project.\n");
        stringBuilder.append(" *\n");
        stringBuilder.append(" */\n");
        if (isActiveHibernate) {
            stringBuilder.append("@Entity(name = \"").append(table.getName()).append("\")\n");
        }
        stringBuilder.append("public class ").append(capitalize(table.getName())).append(" implements Serializable").append(" {");
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        table.getPrimaryKeys().forEach(primaryKey -> {
            if (isActiveHibernate) {
                stringBuilder.append("    @Id\n");
                stringBuilder.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
            }
            stringBuilder.append("    private ").append(primaryKey.getColumn().getTranslatedShortType()).append(" ").append(primaryKey.getColumn().getName()).append(";").append("\n\n");
        });

        table.getColumns().forEach(column -> {
            stringBuilder.append("    private ").append(column.getTranslatedShortType()).append(" ").append(column.getName()).append(";").append("\n\n");
        });

        table.getImportForeignKeys().forEach(foreignKey -> {
            if (isActiveHibernate) {
                stringBuilder.append("    @ManyToOne(fetch = FetchType.").append(fetchType).append(", cascade = CascadeType.").append(cascadeType).append(", )\n");
                stringBuilder.append("    @JoinColumn(name = \"").append(foreignKey.getColumnName()).append("\")\n");
            }
            stringBuilder.append("    private ").append(capitalize(foreignKey.getTableName())).append(" ").append(foreignKey.getColumn().getName()).append(";").append("\n\n");
        });

        table.getForeignKeys().forEach(foreignKey -> {
            stringBuilder.append("    private List<").append(capitalize(foreignKey.getTableName())).append("> ").append(foreignKey.getTableName()).append("s;").append("\n\n");
        });

        table.getImportForeignKeys().forEach(foreignKey -> {
            stringBuilder.append("\n");
            stringBuilder.append("    public void").append(" set").append(capitalize(foreignKey.getTableName())).append(" (").append(capitalize(foreignKey.getTableName())).append(" ").append(foreignKey.getColumn().getName()).append(") {").append("\n");
            stringBuilder.append("        this.").append(foreignKey.getColumn().getName()).append(" = ").append(foreignKey.getColumn().getName()).append(";");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("    public ").append(capitalize(foreignKey.getTableName())).append(" get").append(capitalize(foreignKey.getTableName())).append(" () {").append("\n");
            stringBuilder.append("        return this.").append(foreignKey.getColumn().getName()).append(";");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
        });

        table.getPrimaryKeys().forEach(primaryKey -> {
            stringBuilder.append("\n");
            stringBuilder.append("    public void").append(" set").append(capitalize(primaryKey.getColumn().getName())).append(" (").append(primaryKey.getColumn().getTranslatedShortType()).append(" ").append(primaryKey.getColumn().getName()).append(") {").append("\n");
            stringBuilder.append("        this.").append(primaryKey.getColumn().getName()).append(" = ").append(primaryKey.getColumn().getName()).append(";");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("    public ").append(primaryKey.getColumn().getTranslatedShortType()).append(" get").append(capitalize(primaryKey.getColumn().getName())).append(" () {").append("\n");
            stringBuilder.append("        return this.").append(primaryKey.getColumn().getName()).append(";");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
        });

        table.getColumns().forEach(column -> {
            stringBuilder.append("\n");
            stringBuilder.append("    public void").append(" set").append(capitalize(column.getName())).append(" (").append(column.getTranslatedShortType()).append(" ").append(column.getName()).append(") {").append("\n");
            stringBuilder.append("        this.").append(column.getName()).append(" = ").append(column.getName()).append(";");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("    public ").append(column.getTranslatedShortType()).append(" get").append(capitalize(column.getName())).append(" () {").append("\n");
            stringBuilder.append("        return this.").append(column.getName()).append(";");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
        });

        table.getForeignKeys().forEach(foreignKey -> {
            stringBuilder.append("\n");
            stringBuilder.append("    public void").append(" set").append(capitalize(foreignKey.getTableName())).append("s (List<").append(capitalize(foreignKey.getTableName())).append("> ").append(foreignKey.getTableName()).append("s) {").append("\n");
            stringBuilder.append("        this.").append(foreignKey.getTableName()).append("s = ").append(foreignKey.getTableName()).append("s;");
            stringBuilder.append("\n");
            stringBuilder.append("    }");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("    public List<").append(capitalize(foreignKey.getTableName())).append("> get").append(capitalize(foreignKey.getTableName())).append("s () {").append("\n");
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
        return original.substring(0, 1).toUpperCase(Locale.ENGLISH) + original.substring(1).toLowerCase(Locale.ENGLISH);
    }
}
