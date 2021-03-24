package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class PageTest extends Page {

    private Scene scene;

    @FXML
    private VBox tasksDisplay;
    @FXML
    private VBox parent;
    @FXML
    private Label allTasksTitle;

    public void initialize() {
        this.scene = new Scene(parent);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void fillWithContent() {
        ArrayList<Task> tasks = Client.getRootProject().getAllTasks();
        for (Task task : tasks ) {
            Label label = new Label(task.getTitle());
            tasksDisplay.getChildren().add(label);
        }
    }
}
