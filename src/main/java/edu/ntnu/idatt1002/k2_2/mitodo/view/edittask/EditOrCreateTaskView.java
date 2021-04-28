package edu.ntnu.idatt1002.k2_2.mitodo.view.edittask;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.EnumToStringConverter;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.RepeatEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * Abstract class representing edit or create task view.
 */
public abstract class EditOrCreateTaskView extends View
{
    @FXML
    protected ChoiceBox<Project> selectProject;
    @FXML
    protected VBox parent;
    @FXML
    protected CheckBox isDone;
    @FXML
    protected TextField taskName;
    @FXML
    protected TextArea comments;
    @FXML
    protected DatePicker selectStartDate;
    @FXML
    protected DatePicker selectDueDate;
    @FXML
    protected ChoiceBox<RepeatEnum> selectRepeat;
    @FXML
    protected ChoiceBox<PriorityEnum> selectPriority;
    @FXML
    protected Button btnClearDates;

    protected Task task;
    protected Project project;

    @FXML
    protected void initialize()
    {
        selectProject.getItems().setAll(Client.getRootProject().getAllProjects());
        selectProject.getItems().add(Client.getRootProject());

        selectPriority.setConverter(new EnumToStringConverter<>());
        selectPriority.getItems().setAll(PriorityEnum.values());
        selectPriority.setValue(PriorityEnum.UNDEFINED);

        selectRepeat.setConverter(new EnumToStringConverter<>());
        selectRepeat.getItems().setAll(RepeatEnum.values());
        selectRepeat.setValue(RepeatEnum.DOES_NOT_REPEAT);

        addKeyEventFilter(
                taskName,
                () -> taskName.requestFocus(),
                () -> isDone.requestFocus()
        );

        addKeyEventFilter(
                isDone,
                () -> taskName.requestFocus(),
                () -> comments.requestFocus()
        );

        addKeyEventFilter(
                comments,
                () -> isDone.requestFocus(),
                () -> selectProject.requestFocus()
        );

        addKeyEventFilter(
                selectProject,
                () -> comments.requestFocus(),
                () ->
                {
                    selectStartDate.requestFocus();
                    selectStartDate.show();
                }
        );

        addKeyEventFilter(
                selectStartDate,
                () -> selectProject.requestFocus(),
                () ->
                {
                    selectDueDate.requestFocus();
                    selectDueDate.show();
                }
        );

        addKeyEventFilter(
                selectDueDate,
                () ->
                {
                    selectStartDate.requestFocus();
                    selectStartDate.show();
                },
                () -> btnClearDates.requestFocus()
        );

        addKeyEventFilter(
                btnClearDates,
                () ->
                {
                    selectDueDate.requestFocus();
                    selectDueDate.show();
                },
                () -> selectRepeat.requestFocus()
        );

        addKeyEventFilter(
                selectRepeat,
                () -> btnClearDates.requestFocus(),
                () -> selectPriority.requestFocus()
        );

        addKeyEventFilter(
                selectPriority,
                () -> selectRepeat.requestFocus(),
                () -> selectPriority.requestFocus()
        );

        Platform.runLater(() -> taskName.requestFocus());
    }

    protected void addKeyEventFilter(Node node, Runnable keyUpRunnable, Runnable keyDownRunnable)
    {
        node.addEventFilter(KeyEvent.KEY_PRESSED, keyEventClearD ->
        {
            if(keyEventClearD.getCode() == KeyCode.UP)
            {
                Platform.runLater(keyUpRunnable);
            }
            if(keyEventClearD.getCode() == KeyCode.DOWN)
            {
                Platform.runLater(keyDownRunnable);
            }
        });
    }

    @FXML
    protected void handleIsDoneCheckBox()
    {
        isDone.isSelected();
    }

    @FXML
    protected void clearDates()
    {
        selectDueDate.setValue(null);
        selectStartDate.setValue(null);
    }

    protected void saveAndExit()
    {
        task.setTitle(taskName.getText());
        task.setDone(isDone.isSelected());
        task.setDates(selectStartDate.getValue(),selectDueDate.getValue(), selectRepeat.getValue());
        task.setComments(comments.getText());
        task.setPriority(selectPriority.getValue());
        moveTask();
        cancel();
    }

    @FXML
    protected void cancel()
    {
        try
        {
            Client.returnToPreviousView();
        }
        catch (RuntimeException re)
        {
            re.printStackTrace();
        }
    }

    public void moveTask()
    {
        if (selectProject.getValue() == null|| selectProject.getValue().equals(project)) return;
        Project userProject = selectProject.getValue();
        project.moveTaskTo(this.task, userProject);
    }

    /**
     * Key handler for this page. Saves and exits for enter key and cancels for escape key.
     * @param keyEvent The key event.
     */
    @FXML
    protected void keyHandler(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            saveAndExit();
        }
        else if(keyEvent.getCode() == KeyCode.ESCAPE)
        {
            cancel();
        }
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
