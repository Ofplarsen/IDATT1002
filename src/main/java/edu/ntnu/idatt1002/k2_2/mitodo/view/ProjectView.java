package edu.ntnu.idatt1002.k2_2.mitodo.view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class ProjectView extends Page {

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

    }
}
