package edu.ntnu.idatt1002.k2_2.mitodo.data.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class TaskListSorter
{
    public static void sortByPriority(ArrayList<Task> tasks, boolean increasing)
    {
        Comparator<Task> comparator = comparing(Task::getPriority);

        if (!increasing)
        {
            comparator = comparator.reversed();
        }

        tasks.sort(comparator);
    }

    public static void sortByDueDate(ArrayList<Task> tasks, boolean increasing) {
        ArrayList<Task> noDueDate = (ArrayList<Task>) tasks.stream().filter(t -> t.getDueDate() == null).collect(Collectors.toList());
        tasks.removeAll(noDueDate);

        Comparator<Task> comparator = comparing(Task::getDueDate);

        if (!increasing)
        {
            comparator = comparator.reversed();
        }

        tasks.sort(comparator);
        tasks.addAll(noDueDate);
    }

    public static void sortByStartDate(ArrayList<Task> tasks, boolean increasing) {
        ArrayList<Task> noStartDate = (ArrayList<Task>) tasks.stream().filter(t -> t.getStartDate() == null).collect(Collectors.toList());
        tasks.removeAll(noStartDate);

        Comparator<Task> comparator = comparing(Task::getStartDate);

        if (!increasing)
        {
            comparator = comparator.reversed();
        }

        tasks.sort(comparator);
        tasks.addAll(noStartDate);
    }
}
