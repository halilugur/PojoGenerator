package com.ugurhalil.structures;

/**
 * @author  Halil UĞUR
 * @version 1.0
 * @since   2019-06-06
 */
public class Column {

    private String name;
    private String type;
    private String translatedType;
    private Integer size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTranslatedType() {
        return translatedType;
    }

    public void setTranslatedType(String translatedType) {
        this.translatedType = translatedType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
