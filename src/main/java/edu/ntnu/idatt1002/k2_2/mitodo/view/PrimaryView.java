package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.data.FontSizeEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.MainMenu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.File;

/**
 * The PrimaryView contains the main menu and the content.
 * The main menu can be updated and the content can be changed.
 */
public class PrimaryView extends View
{
    private Scene scene;
    private MainMenu mainMenu;

    private final String smallTextCss = new File("smallText.css").toString();
    private final String bigTextCss = new File("bigText.css").toString();
    private final String defaultTextCss = new File("defaultText.css").toString();
    private FontSizeEnum currentFontSize = FontSizeEnum.Medium;

    @FXML
    private SplitPane parent;

    @FXML
    private TreeView<Label> mainMenuTreeView;

    @FXML
    private AnchorPane content;

    /**
     * Initializes the PrimaryView object.
     */
    @FXML
    private void initialize()
    {
        this.scene = new Scene(parent);
        mainMenu = new MainMenu(mainMenuTreeView);
        updateMainMenu();
        parent.getStylesheets().add(defaultTextCss);
    }

    /**
     * Updates the main menu.
     */
    public void updateMainMenu()
    {
        mainMenu.update();
    }

    /**
     * Sets the content to a new view.
     * @param view The view to display.
     */
    public void setContent(View view)
    {
        Node viewParent = view.getParent();

        content.getChildren().clear();
        content.getChildren().add(viewParent);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                viewParent.requestFocus();
            }
        });
        AnchorPane.setBottomAnchor(viewParent, 0.0);
        AnchorPane.setLeftAnchor(viewParent, 0.0);
        AnchorPane.setRightAnchor(viewParent, 0.0);
        AnchorPane.setTopAnchor(viewParent, 0.0);
    }

    /**
     * @return The scene
     */
    public Scene getScene()
    {
        return scene;
    }

    /**
     * @return The parent node.
     */
    @Override
    public Node getParent()
    {
        return parent;
    }

    public FontSizeEnum getCurrentFontSize(){
        return currentFontSize;
    }

    public void setFontSize(FontSizeEnum newFontSize){
        switch(currentFontSize){
            case Big:
                parent.getStylesheets().remove(bigTextCss);
                break;
            case Medium:
                parent.getStylesheets().remove(defaultTextCss);
                break;
            case Small:
                parent.getStylesheets().remove(smallTextCss);
                break;
        }
        switch (newFontSize){
            case Big:
                parent.getStylesheets().add(bigTextCss);
                currentFontSize = FontSizeEnum.Big;
                break;
            case Medium:
                parent.getStylesheets().add(defaultTextCss);
                currentFontSize = FontSizeEnum.Medium;
                break;
            case Small:
                parent.getStylesheets().add(smallTextCss);
                currentFontSize = FontSizeEnum.Small;
                break;
        }
    }
}
