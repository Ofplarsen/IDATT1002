package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AllProjects extends Page {

    private Scene scene;

    @FXML
    private Label allProjectsTitle;
    @FXML
    private VBox parent;
    @FXML
    private VBox allProjectsDisplay;

    public void initialize() {
        this.scene = new Scene(parent);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void fillWithContent() {
        ArrayList<Project> projects = Client.getRootProject().getProjects();
        for (Project project : projects ) {
            Label label = new Label(project.getTitle());
            allProjectsDisplay.getChildren().add(label);
        }
    }
}
