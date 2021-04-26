package edu.ntnu.idatt1002.k2_2.mitodo.data.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Class containing sorting logic for ArrayLists of Task objects.
 */
public class TaskListSorter
{
    /**
     * Sorts a given ArrayList of Task objects by whether they are done or not.
     *
     * @param tasks the ArrayList containing Task objects
     */
    public static void sortByIsDone(ArrayList<Task> tasks)
    {
        tasks.sort(comparing(Task::isDone));
    }

    /**
     * Sorts a given ArrayList of Task objects by priority. Depending on the boolean increasing, the Task objects are sorted
     * either in an increasing order (increasing = true) or a decreasing order (increasing = false).
     *
     * @param tasks      the ArrayList containing Task objects
     * @param increasing the boolean for deciding if the tasks are sorted increasingly or decreasingly
     */
    public static void sortByPriority(ArrayList<Task> tasks, boolean increasing)
    {
        Comparator<Task> priorityComparator;
        if (increasing) {
            priorityComparator = comparing(Task::getPriority);
        } else {
            priorityComparator = (t1, t2) -> t2.getPriority().compareTo(t1.getPriority());
        }

        tasks.sort(priorityComparator);
    }

    /**
     * Sorts a given ArrayList of Task objects by due date. Depending on the boolean increasing, the Task objects are sorted
     * either in an increasing order (increasing = true) or a decreasing order (increasing = false). If a Task does not
     * have a due date, it will be appended to the end of the sorted ArrayList.
     *
     * @param tasks      the ArrayList containing Task objects
     * @param increasing the boolean deciding if the tasks are sorted increasingly or decreasingly
     */
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

    /**
     * Sorts a given ArrayList of Task objects by start date. Depending on the boolean increasing, the Task objects are sorted
     * either in an increasing order (increasing = true) or a decreasing order (increasing = false). If a Task does not
     * have a start date, it will be appended to the end of the sorted ArrayList.
     *
     * @param tasks      the ArrayList containing Task objects
     * @param increasing the boolean deciding if the tasks are sorted increasingly or decreasingly
     */
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
