package edu.ntnu.idatt1002.k2_2.mitodo.data;

import javafx.util.StringConverter;

/**
 * Class for converting enums in the format "LOREM_IPSUM" to a string in the format "Lorem ipsum".
 * @param <T> The enum type to convert to string.
 *
 * @version 1.0.0
 */
public class EnumToStringConverter<T> extends StringConverter<T>
{
    @Override
    public String toString(T t)
    {
        if (t == null) return "";
        String str = t.toString().replaceAll("_", " ").toLowerCase();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    @Override
    public T fromString(String s)
    {
        return null;
    }
}
