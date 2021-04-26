package edu.ntnu.idatt1002.k2_2.mitodo.data.task;

import edu.ntnu.idatt1002.k2_2.mitodo.data.project.RootProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListSorterTest {
    RootProject rootProject = new RootProject();
    ArrayList<Task> listToSort = new ArrayList<>();
    ArrayList<Task> sortedByDone = new ArrayList<>();
    Task task1 = new Task("Task1", rootProject);
    Task task2 = new Task("Task2", rootProject);
    Task task3 = new Task("Task3", rootProject);
    int year = LocalDate.now().getYear();
    int month = LocalDate.now().getMonthValue();
    int day = LocalDate.now().getDayOfMonth();

    @BeforeEach
    void init(){
        listToSort.add(task1);
        listToSort.add(task2);
        listToSort.add(task3);
    }
    @Test
    void sortByIsDone() {
        sortedByDone.add(task1);
        sortedByDone.add(task3);
        sortedByDone.add(task2);
        task2.setDone(true);
        TaskListSorter.sortByIsDone(listToSort);
        assertEquals(listToSort,sortedByDone);
        task2.setDone(false);
    }
    @Nested
    @DisplayName("Tests for making sure the sorting by priority system works, both increasing and decreasing")
    public class SortByPriority {
        ArrayList<Task> sortedByPrio = new ArrayList<>();
        @Test
        void sortByPriorityIncreasing() {
            sortedByPrio.add(task3);
            sortedByPrio.add(task1);
            sortedByPrio.add(task2);
            task1.setPriority(PriorityEnum.MEDIUM);
            task3.setPriority(PriorityEnum.HIGH);
            task2.setPriority(PriorityEnum.LOW);
            TaskListSorter.sortByPriority(listToSort, false);
            assertEquals(listToSort, sortedByPrio);
            sortedByPrio = new ArrayList<>();

        }

        @Test
        void sortByPriorityDecreasing() {
            sortedByPrio.add(task2);
            sortedByPrio.add(task1);
            sortedByPrio.add(task3);
            task1.setPriority(PriorityEnum.MEDIUM);
            task3.setPriority(PriorityEnum.HIGH);
            task2.setPriority(PriorityEnum.LOW);
            TaskListSorter.sortByPriority(listToSort, true);
            assertEquals(listToSort, sortedByPrio);
        }
    }
    @Nested
    @DisplayName("Tests for sortByDueDate both increasing and decreasing")
    public class SortByDueDate {
        ArrayList<Task> sortedByDueDate = new ArrayList<>();

        @Test
        void sortByDueDateIncreasing() {
            sortedByDueDate.add(task1);
            sortedByDueDate.add(task3);
            sortedByDueDate.add(task2);

            task1.setDates(null,LocalDate.of(year+1, month, day), RepeatEnum.DOES_NOT_REPEAT);
            task3.setDates(null,LocalDate.of(year+2, month, day), RepeatEnum.DOES_NOT_REPEAT);
            task2.setDates(null,LocalDate.of(year+3, month, day), RepeatEnum.DOES_NOT_REPEAT);
            TaskListSorter.sortByDueDate(listToSort, true);
            assertEquals(listToSort, sortedByDueDate);
        }

        @Test
        void sortByDueDateDecreasing() {
            sortedByDueDate.add(task1);
            sortedByDueDate.add(task3);
            sortedByDueDate.add(task2);

            task1.setDates(null,LocalDate.of(year+3, month, day), RepeatEnum.DOES_NOT_REPEAT);
            task3.setDates(null,LocalDate.of(year+2, month, day), RepeatEnum.DOES_NOT_REPEAT);
            task2.setDates(null,LocalDate.of(year+1, month, day), RepeatEnum.DOES_NOT_REPEAT);
            TaskListSorter.sortByDueDate(listToSort, false);
            assertEquals(listToSort, sortedByDueDate);
        }
    }

    @Nested
    @DisplayName("Tests for sortByStartDate, both increasing and decreasing")
    public class sortByStartDate {
        ArrayList<Task> sortedByStartDate = new ArrayList<>();
        @Test
        void sortByStartDateDecreasing() {
            sortedByStartDate.add(task3);
            sortedByStartDate.add(task2);
            sortedByStartDate.add(task1);

            task1.setDates(LocalDate.of(year+1, month, day),null, RepeatEnum.DOES_NOT_REPEAT);
            task2.setDates(LocalDate.of(year+2, month, day),null, RepeatEnum.DOES_NOT_REPEAT);
            task3.setDates(LocalDate.of(year+3, month, day),null, RepeatEnum.DOES_NOT_REPEAT);
            TaskListSorter.sortByStartDate(listToSort, false);
            assertEquals(listToSort, sortedByStartDate);
        }

        @Test
        void sortByStartDateIncreasing() {
            sortedByStartDate.add(task1);
            sortedByStartDate.add(task2);
            sortedByStartDate.add(task3);

            task1.setDates(LocalDate.of(year+1, month, day),null, RepeatEnum.DOES_NOT_REPEAT);
            task2.setDates(LocalDate.of(year+2, month, day),null, RepeatEnum.DOES_NOT_REPEAT);
            task3.setDates(LocalDate.of(year+3, month, day),null, RepeatEnum.DOES_NOT_REPEAT);
            TaskListSorter.sortByStartDate(listToSort, true);
            assertEquals(listToSort, sortedByStartDate);
        }
    }
}