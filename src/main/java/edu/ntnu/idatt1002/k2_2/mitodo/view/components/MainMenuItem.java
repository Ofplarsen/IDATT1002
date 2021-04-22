package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainMenuItem
{
    private final View view;
    private final Label label;

    public MainMenuItem(View view, ContextMenu contextMenu)
    {
        this.view = view;

        label = new Label(view.getMainMenuTitle());
        label.setContextMenu(contextMenu);
        label.setPrefWidth(10000);
        label.getStyleClass().add("tree-item");
    }

    public void setOnDragDropped(EventHandler<DragEvent> eventHandler)
    {
        label.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
        });

        label.setOnDragDropped(eventHandler);
    }

    public void handleEvent(Event event)
    {
        boolean goToView = false;
        if (event instanceof MouseEvent)
        {
            goToView = true;
        }
        else if (event instanceof KeyEvent)
        {
            goToView = ((KeyEvent) event).getCode().equals(KeyCode.ENTER);
        }

        if (goToView)
        {
            Client.setView(view);
            view.update();
        }
    }

    public View getView()
    {
        return view;
    }

    public Label getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return null;
    }
}
