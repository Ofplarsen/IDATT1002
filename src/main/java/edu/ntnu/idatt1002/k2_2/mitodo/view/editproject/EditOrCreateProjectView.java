package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * Abstract class representing edit or create project view.
 */
public abstract class EditOrCreateProjectView extends View
{
    @FXML
    protected VBox parent;
    @FXML
    protected TextField projectTitle;
    @FXML
    protected Button btnSaE;

    /**
     * Key handler for this page. Saves and exits for enter key and cancels for escape key.
     * @param keyEvent The key event.
     */
    @FXML
    protected void keyHandler(KeyEvent keyEvent)
    {
        switch (keyEvent.getCode())
        {
            case ENTER:
                saveAndExit();
                break;
            case ESCAPE:
                cancel();
                break;
        }
        projectTitle.addEventFilter(KeyEvent.KEY_PRESSED, keyEventTitle ->
        {
            if(keyEventTitle.getCode() == KeyCode.DOWN)
            {
                btnSaE.requestFocus();
            }
        });
    }

    protected abstract void saveAndExit();

    /**
     * Returns to the previous page.
     */
    @FXML
    protected void cancel()
    {
        Client.returnToPreviousView();
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
