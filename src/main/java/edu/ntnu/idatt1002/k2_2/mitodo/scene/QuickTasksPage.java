package edu.ntnu.idatt1002.k2_2.mitodo.scene;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class QuickTasksPage extends Page
{
    @Override
    public Scene getScene()
    {
        //StackPane parent = new StackPane();

        StackPane menu = getMenuLayout();
        //Node content = getContentNode();

        //parent.getChildren().addAll(menu, content);

        //return parent.getScene();
        Scene scene = new Scene(menu);
        return scene;
    }

    private StackPane getContentLayout()
    {
        return null;
    }
}
