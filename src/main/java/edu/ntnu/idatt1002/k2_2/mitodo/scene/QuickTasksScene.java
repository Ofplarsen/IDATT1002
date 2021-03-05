package edu.ntnu.idatt1002.k2_2.mitodo.scene;

import edu.ntnu.idatt1002.k2_2.mitodo.project.ProjectManager;
import edu.ntnu.idatt1002.k2_2.mitodo.project.Task;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class QuickTasksScene extends Scene
{
    @Override
    public void show()
    {
        StackPane menu = getMenuStackPane();
        StackPane content = getContentStackPane();
        StackPane mainStackPane = new StackPane(menu, content);

        var scene = new javafx.scene.Scene(mainStackPane);

        SceneManager.getStage().setScene(scene);
        SceneManager.getStage().show();
    }

    private StackPane getContentStackPane()
    {
        ArrayList<Task> tasks = ProjectManager.getQuickTasks().getTasks();

        StackPane[] taskStackPanes = new StackPane[tasks.size()];

        for (int i = 0; i < tasks.size(); i++)
        {
            taskStackPanes[i] = getTaskStackPane(tasks.get(i));
        }

        return new StackPane(taskStackPanes);
    }
}
