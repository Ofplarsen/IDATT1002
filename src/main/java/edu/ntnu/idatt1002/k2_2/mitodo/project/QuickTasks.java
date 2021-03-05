package edu.ntnu.idatt1002.k2_2.mitodo.project;

import java.util.ArrayList;

public class QuickTasks implements Listable
{
    private ArrayList<Task> tasks;

    protected QuickTasks()
    {
        tasks = new ArrayList<>();
    }

    public String getTitle()
    {
        return "Quick Tasks";
    }

    public Task addTask(String title, PriorityEnum priority)
    {
        Task task = new Task(title, priority);
        tasks.add(task);
        return task;
    }

    public void removeTask(Task task)
    {
        tasks.remove(task);
    }

    public void setTasks(ArrayList<Task> tasks)
    {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks()
    {
        return tasks;
    }
}
