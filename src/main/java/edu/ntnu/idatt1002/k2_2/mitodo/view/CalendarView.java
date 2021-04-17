package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.data.TaskListSorter;
import edu.ntnu.idatt1002.k2_2.mitodo.file.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.TaskInProject;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CalendarView extends View
{
    @FXML
    private VBox parent;
    @FXML
    private VBox taskContainer;

    private ArrayList<Task> tasks;
    private LocalDate dateToday;

    @FXML
    private void initialize()
    {
        update();
    }

    @Override
    public void update()
    {
        dateToday = LocalDate.now();
        updateTasks();
        fillWithTasks();
    }

    private void updateTasks()
    {
        tasks = Client.getQuickTasks().getTasks();
        tasks.addAll(Client.getRootProject().getAllTasks());
        tasks = (ArrayList<Task>) tasks.stream().filter(task -> task.getDueDate() != null && !task.isDone()).collect(Collectors.toList());
        TaskListSorter.sortByDueDate(tasks);
    }

    private void fillWithTasks()
    {
        taskContainer.getChildren().clear();

        addLabel("Today");

        ArrayList<Task> tasksToday = getTasksDueOnDay(dateToday);
        if (tasksToday.size() == 0)
        {
            Label noToday = new Label("No Tasks Due Today");
            noToday.setFont(new Font("System", 16));
            noToday.setPrefHeight(30);
            taskContainer.getChildren().add(noToday);
        }
        else
        {
            for (Task task : tasksToday)
            {
                addTask(task);
            }
        }

        int startIndex = tasksToday.size();
        ArrayList<Task> tasksDueOnDate;
        for (int i = startIndex; i < tasks.size(); i += tasksDueOnDate.size())
        {
            LocalDate date = tasks.get(i).getDueDate();
            tasksDueOnDate = getTasksDueOnDay(date);

            addLabel(date.toString());

            for (Task task : tasksDueOnDate)
            {
                addTask(task);
            }
        }
    }

    private ArrayList<Task> getTasksDueOnDay(LocalDate day)
    {
        return (ArrayList<Task>) tasks.stream().filter(task -> task.getDueDate().equals(day)).collect(Collectors.toList());
    }

    private void addLabel(String title)
    {
        Label todayLabel = new Label(title);
        todayLabel.setFont(new Font("System", 32));
        todayLabel.setPrefHeight(40);
        taskContainer.getChildren().add(todayLabel);
    }

    private void addTask(Task task)
    {
        TaskInProject taskInProject = (TaskInProject) FileManager.getView("TaskInProject");
        taskInProject.setTask(task);
        taskInProject.setView(this);
        taskContainer.getChildren().add(taskInProject.getParent());
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
