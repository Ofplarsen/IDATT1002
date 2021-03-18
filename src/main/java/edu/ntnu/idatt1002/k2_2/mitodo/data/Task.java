package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.Objects;

public class Task
{
    private String title;
    private PriorityEnum priority;
    private Date startDate;
    private Date dueDate;
    private String comment;
    private ArrayList<Subtask> subtasks;
    private UUID uuid;

    public Task(String title, PriorityEnum priority)
    {
        this.title = title;
        this.priority = priority;
        this.uuid = UUID.randomUUID();
        subtasks = new ArrayList<>();
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setPriority(PriorityEnum priority)
    {
        this.priority = priority;
    }

    public PriorityEnum getPriority()
    {
        return priority;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate()
    {
        QuickTasks quickTasks = new QuickTasks();

        return dueDate;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }

    public Subtask addSubtasks(String title)
    {
        Subtask subtask = new Subtask(title);
        subtasks.add(subtask);
        return subtask;
    }

    public void removeSubtasks(Subtask subtask)
    {
        subtasks.remove(subtask);
    }

    public void setSubtasks(ArrayList<Subtask> subtasks)
    {
        this.subtasks = subtasks;
    }

    public ArrayList<Subtask> getSubtasks()
    {
        return subtasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return title.equals(task.title) && priority == task.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, priority);
    }
}
