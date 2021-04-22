package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.Component;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import edu.ntnu.idatt1002.k2_2.mitodo.view.editproject.CreateProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.editproject.EditProjectView;
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

    public void setProjectAndListContainer(Project project, VBox listContainer)
    {
        this.project = project;
        this.listContainer = listContainer;
        projectName.setText(project.getTitle());
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
                TaskInProject taskInProject = (TaskInProject) Client.getComponent("TaskInProject");
                taskInProject.setTask(task);
                taskInProject.setView(this);
                listContainer.getChildren().add(i,taskInProject.getParent());
            }
        } else {
            for (int j = 0; j<project.getAllTasks().size();j++) //removed for each because task was never used
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
        Client.updateMainMenu();
    }

    @Override
    public Node getParent()
    {
        return parent;
    }

    @Override
    public String getMainMenuTitle()
    {
        return project.getTitle();
    }

    @Override
    public boolean equals(View view)
    {
        return view instanceof SubProject && ((SubProject) view).project.equals(project);
    }
}
