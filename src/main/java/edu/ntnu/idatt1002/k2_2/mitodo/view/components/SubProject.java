package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.file.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SubProject extends View
{
    @FXML
    private HBox parent;
    @FXML
    private Label projectName;
    @FXML
    private CheckBox showTasks;

    private Project project;
    private VBox listContainer;
    private Project originProject;

    public void setProjectAndListContainer(Project project, VBox listContainer)
    {
        this.project = project;
        this.listContainer = listContainer;
        projectName.setText(project.getTitle());
    }

    public void setOriginProject(Project originProject) {
        this.originProject = originProject;
    }

    @FXML
    private void showSubProjectTasks(){
        if(!(project.getAllTasks().size()==0)){
            setSubprojectTasks(listContainer,showTasks.isSelected());
        }
    }
    private void setSubprojectTasks(VBox listContainer, boolean value) {
        int i = listContainer.getChildren().indexOf(parent) + 1;
        if (value) {
            for (Task task : project.getAllTasks())
            {
                TaskInProject taskInProject = (TaskInProject) FileManager.getView("TaskInProject");
                taskInProject.setTask(task);
                taskInProject.setView(this);
                listContainer.getChildren().add(i,taskInProject.getParent());
            }
        } else {
            for (Task task : project.getAllTasks())
            {
                listContainer.getChildren().remove(i);
            }

        }
    }

    @FXML
    private void handleGoToProjectClick()
    {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
