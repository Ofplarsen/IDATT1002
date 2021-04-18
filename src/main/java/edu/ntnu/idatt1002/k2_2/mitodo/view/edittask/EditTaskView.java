package edu.ntnu.idatt1002.k2_2.mitodo.view.edittask;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import javafx.fxml.FXML;

public class EditTaskView extends EditOrCreateTaskView
{
    public void setTask(Task task)
    {
        this.task = task;
        this.project = task.getProject();

        isDone.setSelected(task.isDone());
        selectStartDate.setValue(task.getStartDate());
        selectDueDate.setValue(task.getDueDate());
        selectRepeat.setValue(task.getRepeat());
        taskName.setText(task.getTitle());
        comments.setText(task.getComments());
        selectPriority.setValue(task.getPriority());
    }
    public void isFromCalendar(boolean b){
        fromCalendar = b;
    }
    public void setProject(Project p){
        this.project = p;
    }
    public void setOption(int i){
        this.option = i;
    }

    @FXML
    protected void saveAndExit()
    {
        super.saveAndExit();
    }

    @FXML
    private void delete()
    {
        task.deleteItself();
        cancel();
    }
}
