package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.TaskListSorter;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.TaskComponent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Class representing a calendar page.
 * Linked with the CalendarView.fxml file.
 */
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

    /**
     * Updates the calendar view.
     */
    @Override
    public void update()
    {
        dateToday = LocalDate.now();
        updateTasks();
        fillWithTasks();
    }

    /**
     * Updates the list of tasks.
     */
    private void updateTasks()
    {
        tasks = Client.getRootProject().getAllTasks();
        tasks = (ArrayList<Task>) tasks.stream().filter(task -> task.getDueDate() != null && !task.isDone()).collect(Collectors.toList());
        TaskListSorter.sortByDueDate(tasks, true);
    }

    /**
     * Updates the displayed tasks.
     */
    private void fillWithTasks()
    {
        taskContainer.getChildren().clear();
        ArrayList<Task> expiredTasks = (ArrayList<Task>) tasks.stream().filter(t -> t.getDueDate().isBefore(LocalDate.now())&&!t.isDone()).collect(Collectors.toList());
        tasks.removeAll(expiredTasks);
        if(!expiredTasks.isEmpty()){
            addLabel("Overdue Tasks");
            for (Task task : expiredTasks){
                addTask(task);
            }
        }

        addLabel("Today");

        ArrayList<Task> tasksToday = getTasksDueOnDay(dateToday);
        if (tasksToday.size() == 0)
        {
            Label noToday = new Label("No tasks due today.");
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

            if (date.isEqual(dateToday.plusDays(1))){
                addLabel("Tomorrow");

            }else addLabel(dateToString(date));

            for (Task task : tasksDueOnDate)
            {
                addTask(task);
            }
        }
        
    }

    /**
     * @param day The day to get tasks from.
     * @return List of tasks due on the given date.
     */
    private ArrayList<Task> getTasksDueOnDay(LocalDate day)
    {
        return (ArrayList<Task>) tasks.stream().filter(task -> task.getDueDate().equals(day)).collect(Collectors.toList());
    }

    /**
     * @param date The date to convert to string.
     * @return The string representation of the given date.
     */
    private String dateToString(LocalDate date)//Remove the withLocale to make it to the computer language of preference
    {
       return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.ENGLISH));
    }

    /**
     * Adds a label to the page.
     * @param title The title of the label to add.
     */
    private void addLabel(String title)
    {
        Text todayLabel = new Text(title);
        todayLabel.setFont(new Font("System", 32));
        todayLabel.setId("header");
        taskContainer.getChildren().add(todayLabel);
    }

    /**
     * Adds a task component o the page.
     * @param task The task to add a component for.
     */
    private void addTask(Task task)
    {
        TaskComponent taskComponent = (TaskComponent) Client.getComponent("Task");
        taskComponent.setTask(task);
        taskComponent.setView(this);
        taskContainer.getChildren().add(taskComponent.getParent());
    }

    @Override
    public Node getParent()
    {
        return parent;
    }

    @Override
    public String getMainMenuTitle()
    {
        return "Calendar";
    }

    @Override
    public boolean equals(View view)
    {
        return view instanceof CalendarView;
    }
}
