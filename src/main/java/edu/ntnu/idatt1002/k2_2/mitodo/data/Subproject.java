package edu.ntnu.idatt1002.k2_2.mitodo.data;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Subproject
{
    private String title;
    private Color color;
    private ArrayList<Task> tasks;

    public Subproject(String title) {
        this.title = title;
        this.color = color.BLUE;
    }

    public Subproject(String title, Color color)
    {
        this.title = title;
        this.color = color;

        tasks = new ArrayList<>();
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return color;
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
