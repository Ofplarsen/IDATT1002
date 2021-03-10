package edu.ntnu.idatt1002.k2_2.mitodo.scene;

import edu.ntnu.idatt1002.k2_2.mitodo.project.Subproject;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SubprojectPage extends Page
{
    private final Subproject subproject;

    public SubprojectPage(Subproject subproject)
    {
        this.subproject = subproject;
    }

    @Override
    public Scene getScene()
    {
        HBox parent = new HBox();

        StackPane menu = new MainMenu(subproject.getTitle()).getMenuLayout();
        StackPane content = getContentLayout();

        parent.getChildren().addAll(menu, content);

        StackPane mainStackPane = new StackPane();
        mainStackPane.getChildren().add(parent);
        StackPane.setAlignment(parent, Pos.CENTER);

        return new Scene(mainStackPane);
    }

    private StackPane getContentLayout()
    {
        Label testLabel = new Label(subproject.getTitle() + " content.");
        StackPane stackPane = new StackPane(testLabel);
        return stackPane;
    }
}
