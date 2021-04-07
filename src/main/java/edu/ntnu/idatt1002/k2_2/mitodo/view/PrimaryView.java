package edu.ntnu.idatt1002.k2_2.mitodo.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;


public class PrimaryView extends View
{

    private Scene scene;

    @FXML
    private SplitPane parent;

    @FXML
    private AnchorPane content;

    public void initialize() {
        this.scene = new Scene(parent);
    }

    public void setContent(View view)
    {
        content.getChildren().add(view.getParent());
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
