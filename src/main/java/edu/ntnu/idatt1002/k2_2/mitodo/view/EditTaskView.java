package edu.ntnu.idatt1002.k2_2.mitodo.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class EditTaskView extends View
{
    @FXML
    private  TextArea comments;
    @FXML
    private  DatePicker selectStartDate;
    @FXML
    private  DatePicker selectDueDate;
    @FXML
    private  CheckBox taskName;
    @FXML
    private VBox parent;

    public void initialize() {

    }
    @FXML
    public void exit() {

    }
    @FXML
    public void save() {
    }
    @FXML
    public void setStartDate() {
    }
    @FXML
    public void setDueDate() {
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
