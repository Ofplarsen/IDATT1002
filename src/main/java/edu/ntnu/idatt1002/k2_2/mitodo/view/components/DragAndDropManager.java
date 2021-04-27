package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

/**
 * Class responsible for setting and getting the Object that is currently being dragged.
 */
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
