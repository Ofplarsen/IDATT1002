package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.*;

/**
 * Class representing a item in the main menu with a text-label link to a view and a context menu.
 */
public class MainMenuItem
{
    private final View view;
    private final Label label;

    /**
     * Constructs a new MainMenuItem
     * @param view The view to link to.
     * @param contextMenu The context menu.
     */
    public MainMenuItem(View view, ContextMenu contextMenu)
    {
        this.view = view;

        label = new Label(view.getMainMenuTitle());
        label.setContextMenu(contextMenu);
        label.setPrefWidth(340);
        label.getStyleClass().add("tree-item");
    }

    /**
     * Sets an event handler for a drag over event.
     * @param eventHandler The event handler.
     */
    public void setOnDragOver(EventHandler<DragEvent> eventHandler)
    {
        label.setOnDragOver(eventHandler);
    }

    /**
     * Sets an event handler for a drag dropped event.
     * @param eventHandler The event handler.
     */
    public void setOnDragDropped(EventHandler<DragEvent> eventHandler)
    {
        label.setOnDragDropped(eventHandler);
    }

    /**
     * Handles any event. Goes to the view if it is a mouse event
     * or key event with a enter key code.
     * @param event The event.
     */
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
