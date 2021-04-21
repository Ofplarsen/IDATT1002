package edu.ntnu.idatt1002.k2_2.mitodo.view.edittask;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
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

public abstract class
EditOrCreateTaskView extends View
{
    @FXML
    private ChoiceBox<Project> selectProject;
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

    protected int elementIndex = 0;
    protected Task task;
    protected Project project;

    @FXML
    public void initialize()
    {
        selectProject.getItems().setAll(Client.getRootProject().getAllProjects());
        selectProject.getItems().add(Client.getRootProject());

        selectPriority.getItems().setAll(PriorityEnum.values());
        selectPriority.setValue(PriorityEnum.Undefined);

        selectRepeat.setConverter(RepeatEnum.toString);
        selectRepeat.getItems().setAll(RepeatEnum.values());
        selectRepeat.setValue(RepeatEnum.DoesNotRepeat);
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
        Client.returnToPreviousView();
    }

    @FXML
    public void keyHandler(KeyEvent keyEvent){
        taskName.addEventFilter(KeyEvent.KEY_PRESSED, keyEventName -> {
            if(keyEventName.getCode() == KeyCode.UP){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        taskName.requestFocus();
                    }
                });
            }
            if(keyEventName.getCode() == KeyCode.DOWN){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        isDone.requestFocus();
                    }
                });

            }
        });


        isDone.addEventFilter(KeyEvent.KEY_PRESSED, keyEventDone -> {
            if(keyEventDone.getCode() == KeyCode.UP){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        taskName.requestFocus();
                    }
                });
            }
            if(keyEventDone.getCode() == KeyCode.DOWN){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        comments.requestFocus();
                    }
                });
            }
        });

        comments.addEventFilter(KeyEvent.KEY_PRESSED, keyEventComment -> {
            if(keyEventComment.getCode() == KeyCode.UP){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        isDone.requestFocus();
                    }
                });
            }
            if(keyEventComment.getCode() == KeyCode.DOWN){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        selectProject.requestFocus();
                    }
                });
            }
        });

        selectProject.addEventFilter(KeyEvent.KEY_PRESSED, keyEventProject -> {
            if(keyEventProject.getCode() == KeyCode.UP){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        comments.requestFocus();
                    }
                });
            }
            if(keyEventProject.getCode() == KeyCode.DOWN){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        selectStartDate.requestFocus();
                        selectStartDate.show();
                    }
                });
            }
        });

        selectStartDate.addEventFilter(KeyEvent.KEY_PRESSED, keyEventStartD -> {
            if(keyEventStartD.getCode() == KeyCode.UP){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        selectProject.requestFocus();
                    }
                });
            }
            if(keyEventStartD.getCode() == KeyCode.DOWN){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        selectDueDate.requestFocus();
                        selectDueDate.show();
                    }
                });
            }
        });

        selectDueDate.addEventFilter(KeyEvent.KEY_PRESSED, keyEventDueD -> {
            if(keyEventDueD.getCode() == KeyCode.UP){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        selectStartDate.requestFocus();
                        selectStartDate.show();
                    }
                });
            }
            if(keyEventDueD.getCode() == KeyCode.DOWN){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        btnClearDates.requestFocus();
                    }
                });
            }
        });

        btnClearDates.addEventFilter(KeyEvent.KEY_PRESSED, keyEventClearD -> {
            if(keyEventClearD.getCode() == KeyCode.UP){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        selectDueDate.requestFocus();
                        selectDueDate.show();
                    }
                });
            }
            if(keyEventClearD.getCode() == KeyCode.DOWN){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        selectRepeat.requestFocus();
                    }
                });
            }
        });

        selectRepeat.addEventFilter(KeyEvent.KEY_PRESSED, keyEventRepeat -> {
            if(keyEventRepeat.getCode() == KeyCode.UP){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        btnClearDates.requestFocus();
                    }
                });
            }
        });
        if(keyEvent.getCode() == KeyCode.ENTER){
            saveAndExit();
        }else if(keyEvent.getCode() == KeyCode.ESCAPE){
            cancel();
        }else if(keyEvent.getCode() == KeyCode.DELETE){

        }
    }

    public void moveTask() {
        if (selectProject.getValue() == null) return;
        Project userProject = selectProject.getValue();
        project.moveTask(this.task, userProject);
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
