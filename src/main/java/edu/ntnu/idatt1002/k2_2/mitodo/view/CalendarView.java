package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.TaskInProject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;


public class CalendarView extends View
{
    @FXML
    private ScrollPane scrollParent;
    @FXML
    private VBox parent;

    public void initialize() {
        addTasksSorted(getAllTasks());
    }
    public ArrayList<Task> getAllTasks(){
        ArrayList<Task> taskBucket = new ArrayList<>();
        taskBucket.addAll(Client.getQuickTasks().getAllTasks());
        taskBucket.addAll(Client.getRootProject().getAllTasks());
        sortTasksByDueDate(taskBucket);
        return taskBucket;
    }
    public void addTasksSorted(ArrayList<Task> allTasks){
        if (allTasks.size() < 1){
            Label noTaskMessage = new Label("No Tasks With Due Date");
            noTaskMessage.setFont(new Font("System", 32));
            noTaskMessage.setPrefHeight(40);
            noTaskMessage.setAlignment(Pos.CENTER);
            parent.getChildren().add(noTaskMessage);
        }
        else {
            LocalDate date = LocalDate.now();
            Label today = new Label("Today");
            today.setFont(new Font("System", 32));
            today.setPrefHeight(40);
            parent.getChildren().add(today);
            if(!allTasks.get(0).getDueDate().isEqual(date)){
                Label noToday = new Label("No Tasks Due Today");
                noToday.setFont(new Font("System", 23));
                noToday.setPrefHeight(30);
                parent.getChildren().add(noToday);
            }

            for (Task s : allTasks) {
                FXMLLoader loader = new FXMLLoader();
                if(date.isBefore(s.getDueDate())){
                    Label dateOfTask = new Label(s.getDueDate().getDayOfMonth() + ". " + s.getDueDate().getMonth().toString());
                    dateOfTask.setFont(new Font("System", 23));
                    dateOfTask.setPrefHeight(30);
                    parent.getChildren().add(dateOfTask);
                }
                try {
                    Node hBox = loader.load(getClass().getResource("/fxml/TaskInProject.fxml").openStream());
                    parent.getChildren().add(hBox);
                    TaskInProject controller = loader.getController();
                    URL editButtonUrl = getClass().getResource("/images/editImage.png");
                    URL deleteButtonUrl = getClass().getResource("/images/deleteImage.png");
                    ImageView deleteButton = new ImageView(deleteButtonUrl.toExternalForm());
                    ImageView editButton = new ImageView(editButtonUrl.toExternalForm());
                    controller.setTaskName(s.getTitle()); //set label
                    controller.setTask(s, true);
                    controller.setPriorityText(s.getPriority().toString().toLowerCase());
                    controller.setDate(s.getStartDateAsString(), s.getDueDateAsString());
                    controller.setIsDone(s.isDone());
                    controller.setEditImage(editButton);
                    controller.setDeleteImage(deleteButton);
                    controller.setProjectName(s.getProject().getTitle());
                    controller.setProject(s.getProject());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                date = s.getDueDate();
            }
        }
    }
    public void sortTasksByDueDate(ArrayList <Task> list){
        for(int i = list.size()-1; i >=0; i-=1){
           if(list.get(i).getDueDate()==null){
               list.remove(i);
           }
        }
        Comparator<Task> dueDateComparator = (t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate());
        list.sort(dueDateComparator);
    }

    @Override
    public Node getParent()
    {
        return scrollParent;
    }

}
