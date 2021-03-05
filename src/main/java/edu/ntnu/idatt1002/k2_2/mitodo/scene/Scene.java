package edu.ntnu.idatt1002.k2_2.mitodo.scene;

import edu.ntnu.idatt1002.k2_2.mitodo.project.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public abstract class Scene
{
    public abstract void show();

    protected StackPane getMenuStackPane()
    {
        Button quickTasksButton = new Button("Quick tasks");
        quickTasksButton.setAlignment(Pos.TOP_CENTER);

        quickTasksButton.setOnMouseClicked((mouseEvent) -> SceneManager.setScene(new QuickTasksScene()));

        StackPane menu = new StackPane(quickTasksButton);
        menu.setAlignment(Pos.TOP_LEFT);
        return menu;
    }

    protected StackPane getTaskStackPane(Task task)
    {
        Label label = new Label(task.getTitle());
        return new StackPane(label);
    }
}
