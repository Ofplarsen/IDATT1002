package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import javafx.scene.input.DragEvent;

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
