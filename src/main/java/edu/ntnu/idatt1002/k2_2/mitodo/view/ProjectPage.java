/*

package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.controller.PageManager;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;

public class ProjectPage extends Page
{
    public final static String FXML_NAME = "subproject";

    private Project subproject;
    private Scene scene;

    @FXML
    private SplitPane parent;
    @FXML
    private TreeView mainMenu;
    @FXML
    private Label subprojectTitle;
    @FXML
    private ComboBox sortComboBox;

    public static ProjectPage getNewPage(Project project)
    {
        ProjectPage subprojectPage = (ProjectPage) PageManager.getPage(FXML_NAME);
        subprojectPage.setSubproject(project);
        return subprojectPage;
    }

    public void initialize()
    {
        this.scene = new Scene(parent);
    }

    private void setSubproject(Subproject subproject)
    {
        this.subproject = subproject;
    }

    @Override
    public Scene getScene()
    {
        return scene;
    }

    @Override
    public void fillWithContent()
    {
        MainMenu mm = new MainMenu(subproject.getTitle());
        mm.editMenuLayout(mainMenu);
        subprojectTitle.setText(subproject.getTitle());

        sortComboBox.setItems(FXCollections.observableArrayList(
                "test",
                "test test")
        );
    }
}

*/
