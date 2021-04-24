package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SubProjectComponent extends View
{
    @FXML
    private HBox parent;
    @FXML
    private Label projectName;

    private Project project;

    public void setProject(Project project)
    {
        this.project = project;
        projectName.setText(project.getTitle());

        parent.setOnDragDetected(dragEvent ->
        {
            Dragboard dragboard = parent.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent clipboardContent = new ClipboardContent();

            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            Image image = parent.snapshot(parameters, null);
            dragboard.setDragView(image);

            clipboardContent.putString("");
            dragboard.setContent(clipboardContent);

            DragAndDropManager.setValue(project);

            dragEvent.consume();
        });
    }

    @FXML
    private void handleGoToProjectClick()
    {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
        Client.updateMainMenu();
    }

    @Override
    public Node getParent()
    {
        return parent;
    }

    @Override
    public String getMainMenuTitle()
    {
        return project.getTitle();
    }

    @Override
    public boolean equals(View view)
    {
        return view instanceof SubProjectComponent && ((SubProjectComponent) view).project.equals(project);
    }
}
