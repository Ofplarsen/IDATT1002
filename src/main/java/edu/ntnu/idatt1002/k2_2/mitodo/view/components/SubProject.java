package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;

public class SubProject {

    Project project;
    @FXML
    private Label projectName;


    public void setInfo(Project project) {
        this.project = project;
        projectName.setText(project.getTitle());
    }

    public void handleGoToProjectClick(){
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }
}
