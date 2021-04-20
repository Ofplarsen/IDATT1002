package edu.ntnu.idatt1002.k2_2.mitodo.data.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class TaskListSorter
{
    public static void sortByIsDone(ArrayList<Task> tasks)
    {
        tasks.sort(comparing(Task::isDone));
    }

    public static void sortByPriority(ArrayList<Task> tasks, boolean increasing)
    {
        Comparator<Task> priorityComparator;
        if (increasing) {
            priorityComparator = (t1, t2) -> t1.getPriority().compareTo(t2.getPriority());
        } else {
            priorityComparator = (t1, t2) -> t2.getPriority().compareTo(t1.getPriority());
        }

        tasks.sort(priorityComparator);
    }

    public static void sortByDueDate(ArrayList<Task> tasks, boolean increasing) {
        ArrayList<Task> noDueDate = (ArrayList<Task>) tasks.stream().filter(t -> t.getDueDate() == null).collect(Collectors.toList());

        tasks.removeAll(noDueDate);

        Comparator<Task> dueDateComparator;
        if (increasing) {
            dueDateComparator = comparing(Task::getDueDate);
        } else {
            dueDateComparator = (t1, t2) -> t2.getDueDate().compareTo(t1.getDueDate());
        }

        tasks.sort(dueDateComparator);
        tasks.addAll(noDueDate);
    }

    public static void sortByStartDate(ArrayList<Task> tasks, boolean increasing) {
        ArrayList<Task> noStartDate = (ArrayList<Task>) tasks.stream().filter(t -> t.getStartDate() == null).collect(Collectors.toList());

        tasks.removeAll(noStartDate);

        Comparator<Task> startDateComparator;
        if (increasing) {
            startDateComparator = comparing(Task::getStartDate);
        } else {
            startDateComparator = (t1, t2) -> t2.getStartDate().compareTo(t1.getStartDate());
        }

        tasks.sort(startDateComparator);
        tasks.addAll(noStartDate);
    }
}
