package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

public class DragAndDropManager
{
    private static Object value;

    public static void setValue(Object value)
    {
        DragAndDropManager.value = value;
    }

    public static Object getValue()
    {
        return value;
    }
}
