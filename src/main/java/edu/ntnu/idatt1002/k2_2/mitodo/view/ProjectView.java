package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.TaskInProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
    private HBox header;
    @FXML
    private VBox parent;
    @FXML
    private Label headline;
    @FXML
    private ComboBox filterBox;

    //Background taskBackground;
    //BackgroundFill bf = new BackgroundFill(Color.BLANCHEDALMOND, new CornerRadii(1), null);
    public void initialize() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Priority",
                        "Due Date",
                        "Start Date",
                        "Date Added"
                );
        filterBox.setItems(options);
    }

    public void setProject(Project project)
    {
        this.project = project;
        headline.setText(project.getTitle());
        addTasks();
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
    /*
    @Override
    public void setHeadline(String s) {
        headline.setText(s);
    }*/

    public void setContent(String name) {
        headline.setText(name);

    }

    public void addTasks(){
        if (project.getAllTasks().size() < 2){
            HBox hBox = new HBox();
            Label noTaskMessage = new Label("No Tasks Currently Added");
            noTaskMessage.setPadding(new Insets(30,0,0,8));
            noTaskMessage.setFont(new Font(40));
            hBox.getChildren().add(noTaskMessage);
            parent.getChildren().add(hBox);
        }
        else {

            for (Task s : project.getAllTasks()
            ) {
                FXMLLoader loader = new FXMLLoader();
                try {
                    Node node = loader.load(getClass().getResource("/fxml/TaskInProject.fxml").openStream());
                    parent.getChildren().add(node);
                    URL editButtonUrl = getClass().getResource("/Images/editImage.jpg");
                    URL deleteButtonUrl = getClass().getResource("/Images/deleteImage.jpg");
                    ImageView editButton = new ImageView(editButtonUrl.toExternalForm());
                    TaskInProject controller = loader.getController();
                    ImageView deleteButton = new ImageView(deleteButtonUrl.toExternalForm());

                    controller.setTask(s);
                    controller.setTaskName(s.getTitle()); //set label
                    controller.setPriorityText(s.getPriority().toString().toLowerCase());
                    controller.setDate(s.getStartDateAsString(), s.getDueDateAsString());
                    controller.setEditImage(editButton);
                    controller.setDeleteImage(deleteButton);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    /*
            HBox tester = new HBox();
            tester.setPrefSize(100,100);
            CheckBox box = new CheckBox();
            box.setSelected(false);
            box.setText(s);
            System.out.println(box.getProperties());
            box.setAlignment(Pos.TOP_LEFT);
            Label priority = new Label("Priority");
            box.setPrefSize(100,100);
            priority.autosize();
            taskBackground = new Background(bf);
            tester.setBackground(taskBackground);
            tester.getChildren().addAll(box,priority);
            parent.getChildren().add(tester);*/
}
