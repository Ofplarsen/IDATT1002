package edu.ntnu.idatt1002.k2_2.mitodo.data;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task1 = new Task("Task1");
    Task task2 = new Task("Task2");
    @Nested
    class idTest{
        @Test
        void getIdMatchesId(){
            assertEquals(task1.getID(), task1.getID());
        }
        @Test
        void getIdDoesntMatch(){
            assertNotEquals(task1.getID(), task2.getID());
        }
    }

    @Nested
    class equalsTest{
        @Test
        void doesEqualsAll(){
            assertTrue(task1.equals(task1));
        }
        @Test
        void titleSameButNotId(){
            Task task3 = new Task("Task1");
            assertFalse(task1.equals(task3));
        }
        @Test
        void differentTasks(){
            assertFalse(task1.equals(task2));
        }

    }
}