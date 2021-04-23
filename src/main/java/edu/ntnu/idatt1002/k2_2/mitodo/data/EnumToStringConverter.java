package edu.ntnu.idatt1002.k2_2.mitodo.data;

import javafx.util.StringConverter;

public class EnumToStringConverter<T> extends StringConverter<T>
{
    @Override
    public String toString(T tenum)
    {
        if (tenum == null) return "";
        String str = tenum.toString().replaceAll("_", " ").toLowerCase();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    @Override
    public T fromString(String s)
    {
        return null;
    }
}
