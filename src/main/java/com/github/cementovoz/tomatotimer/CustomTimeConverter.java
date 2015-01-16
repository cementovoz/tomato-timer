package com.github.cementovoz.tomatotimer;

import javafx.util.StringConverter;

import java.text.Format;

/**
 * Created by cementovoz on 14.01.15.
 */
public class CustomTimeConverter extends StringConverter<Number> {

    private String prefix;
    private String postfix;

    public CustomTimeConverter() {
        this("", "");
    }

    public CustomTimeConverter(String prefix) {
        this(prefix, "");
    }

    public CustomTimeConverter(String prefix, String postfix) {
        this.prefix = prefix;
        this.postfix = postfix;
    }

    @Override
    public String toString(Number object) {
        return String.format("%1$02d:%2$02d", object.intValue() / 60 , object.intValue() % 60);
    }

    @Override
    public Number fromString(String string) {
        return Long.valueOf(string);
    }
}
