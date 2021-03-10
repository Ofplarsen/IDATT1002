package edu.ntnu.idatt1002.k2_2.mitodo.scene;

import edu.ntnu.idatt1002.k2_2.mitodo.project.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public abstract class Page
{
    public abstract Scene getScene();

    protected StackPane getTaskLayout(Task task)
    {
        return new StackPane(new Label(task.getTitle()));
    }
}
