package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.TaskInProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;


public class ProjectView extends View
{
    private Project project;

    @FXML
    private VBox parent;
    @FXML
    private Label headline;
    @FXML
    private ComboBox filterBox;

    public void initialize() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Priority",
                        "Due Date",
                        "Start Date"
                );
        filterBox.setItems(options);
    }

    public void setProject(Project projectMain)
    {
        this.project = projectMain;

        headline.setText(projectMain.getTitle());
       // if(project.getProjects().size()>0) //maybe do something like this to add projects instead of tasks
        addTasks();
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
    public void setContent(String name) {
        headline.setText(name);

    }

  public void handleAddTaskButton() {
        Task newTask = project.addTask("'New Task'");
        EditTaskView editTaskView = (EditTaskView) Client.setView("EditTaskView");
        editTaskView.setTask(newTask);
        editTaskView.setProject(project);
        editTaskView.update();
    }

    public void handleEditButtonClick(){
        EditProjectView editProjectView =(EditProjectView) Client.setView("EditProjectView");
        editProjectView.setProject(project);
    }
//TODO Maybe find a way to optimize this?
    public void addTasks(){
        if (project.getAllTasks().size() < 1){
           // HBox hBox = new HBox();
            Label noTaskMessage = new Label("No Tasks Currently Added");
            noTaskMessage.setPadding(new Insets(30,0,0,8));
            noTaskMessage.setFont(new Font(40));
          //  hBox.getChildren().add(noTaskMessage);
            parent.getChildren().add(noTaskMessage); //parent.getChildren().add(hBox);
        }
        else {

            for (Task s : project.getAllTasks()) {
                FXMLLoader loader = new FXMLLoader();
                try {
                    Node node = loader.load(getClass().getResource("/fxml/TaskInProject.fxml").openStream());
                    parent.getChildren().add(node);
                    URL editButtonUrl = getClass().getResource("/images/editImage.jpg");
                    URL deleteButtonUrl = getClass().getResource("/images/deleteImage.jpg");
                    ImageView editButton = new ImageView(editButtonUrl.toExternalForm());
                    TaskInProject controller = loader.getController();
                    ImageView deleteButton = new ImageView(deleteButtonUrl.toExternalForm());

                    controller.setProject(project);
                    controller.setTask(s);
                    controller.setTaskName(s.getTitle()); //set label
                    controller.setPriorityText(s.getPriority().toString().toLowerCase());
                    controller.setDate(s.getStartDateAsString(), s.getDueDateAsString());
                    controller.setIsDone(s.isDone());
                    controller.setEditImage(editButton);
                    controller.setDeleteImage(deleteButton);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
