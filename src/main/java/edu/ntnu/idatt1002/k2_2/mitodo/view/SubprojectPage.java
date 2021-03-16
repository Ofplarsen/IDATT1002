package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.controller.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Subproject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;

public class SubprojectPage extends Page
{
    private final Subproject subproject;
    private final Parent parent;
    private final Scene scene;

    public SubprojectPage(Subproject subproject)
    {
        this.subproject = subproject;
        this.parent = FileManager.loadFXML("subproject");
        this.scene = new Scene(parent);
    }

    @Override
    public Scene getScene()
    {
        return scene;
    }

    @Override
    public void fillWithContent()
    {
        MainMenu mainMenu = new MainMenu(subproject.getTitle());
        mainMenu.editMenuLayout(parent);

        Label subprojectTitle = (Label) parent.lookup("#subprojectTitle");;
        subprojectTitle.setText(subproject.getTitle());
    }
}
