package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TaskInProject {
    @FXML
    HBox parent;
    @FXML
    CheckBox taskBox;
    @FXML
    Label priority;
    @FXML
    Label startDate;
    @FXML
    Label dueDate;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;

    public void setTaskName(String task){
        taskBox.setText(task);
    }
    public void setPriorityText(String prio){
        priority.setText(prio + " priority" );
    }
    public void setDate(String start, String end){
        startDate.setText(start);
        dueDate.setText(end);
    }
    public void setEditImage(ImageView image){
        image.setFitHeight(50);
        image.setFitWidth(50);
        editButton.setGraphic(image);
    }
    public void setDeleteImage(ImageView image){
        image.setFitHeight(50);
        image.setFitWidth(50);
        deleteButton.setGraphic(image);
    }
    public Node getParent() {
        return parent;
    }
}
