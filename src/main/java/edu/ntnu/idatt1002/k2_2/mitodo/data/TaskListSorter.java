package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.util.ArrayList;
import java.util.Comparator;

import static java.util.Comparator.comparing;

public class TaskListSorter
{
    public static void sortByIsDone(ArrayList<Task> tasks)
    {
        tasks.sort(comparing(Task::isDone));
    }

    public static void sortByPriority(ArrayList<Task> tasks)
    {
        Comparator<Task> priorityComparator = (t1, t2) -> t2.getPriority().compareTo(t1.getPriority());
        tasks.sort(priorityComparator);
    }

    public static void sortByDueDate(ArrayList<Task> tasks)
    {
        ArrayList<Task> noDueDate = new ArrayList<>();

        for(int i = tasks.size()-1; i >=0; i-=1)
        {
            if(tasks.get(i).getDueDate()==null)
            {
                noDueDate.add(tasks.remove(i));
            }
        }

        Comparator<Task> dueDateComparator = comparing(Task::getDueDate);
        tasks.sort(dueDateComparator);
        tasks.addAll(noDueDate);
    }

    public static void sortByStartDate(ArrayList<Task> tasks)
    {
        ArrayList<Task> noStartDate = new ArrayList<>();

        for(int i = tasks.size()-1; i >=0; i-=1)
        {
            if(tasks.get(i).getStartDate()==null)
            {
                noStartDate.add(tasks.remove(i));
            }
        }

        Comparator<Task> startDateComparator = comparing(Task::getStartDate);
        tasks.sort(startDateComparator);
        tasks.addAll(noStartDate);
    }
}
