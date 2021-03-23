package edu.ntnu.idatt1002.k2_2.mitodo.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    Task task = new Task("A task", PriorityEnum.MEDIUM);

    @Nested
    @DisplayName("Tests for instantiating a task")
    class InstantiateTaskTest{

        @Test
        void instantiateTaskInvalidNameTask(){
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                Task invalidTask = new Task("", PriorityEnum.MEDIUM);
            });
        }

        @Test
        void instantiateTaskInvalidPriority(){
            Assertions.assertThrows(NullPointerException.class, () -> {
                Task invalidTask = new Task("Invalid Task", null);
            });
        }
    }

    @Nested
    @DisplayName("Tests for addSubtask method")
    class AddSubtasksTest{

        @Test
        void subtaskIsSuccessfullyAdded(){
            Assertions.assertEquals(0, task.getSubtasks().size());
            task.addSubtasks("A subtask");
            Assertions.assertEquals(1, task.getSubtasks().size());
        }

        /*@Test
        void subtaskIsNotSuccessfullyAdded*/ //TODO: make a negative test if relevant e.g. if checking params on instantiation
    }

    @Nested
    @DisplayName("Tests for equals method")
    class EqualsTest{

        @Test
        void tasksAreEqual(){
            Task task1 = new Task("Task", PriorityEnum.MEDIUM);
            Task task2 = new Task("Task", PriorityEnum.MEDIUM);

            assertEquals(task1, task1);
        }

        @Test
        void tasksHaveDifferentTitles(){
            Task task1 = new Task("Task", PriorityEnum.MEDIUM);
            Task task2 = new Task("Task2", PriorityEnum.MEDIUM);

            assertNotEquals(task1, task2);
        }

        @Test
        void tasksHaveDifferentPriorities(){
            Task task1 = new Task("Task", PriorityEnum.MEDIUM);
            Task task2 = new Task("Task", PriorityEnum.HIGH);

            assertNotEquals(task1, task2);
        }

    }
}
