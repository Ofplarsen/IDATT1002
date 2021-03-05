package edu.ntnu.idatt1002.k2_2.mitodo.project;

import java.util.ArrayList;

public interface Listable
{
    String getTitle();

    Task addTask(String title, PriorityEnum priority);

    void removeTask(Task task);

    void setTasks(ArrayList<Task> tasks);

    ArrayList<Task> getTasks();
}
