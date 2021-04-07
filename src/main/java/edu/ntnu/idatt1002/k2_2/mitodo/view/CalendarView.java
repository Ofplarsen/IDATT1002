package edu.ntnu.idatt1002.k2_2.mitodo.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class CalendarView extends View
{
    @FXML
    private VBox tasksDisplay;
    @FXML
    private VBox parent;
    @FXML
    private Label allTasksTitle;

    public void initialize() {
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
