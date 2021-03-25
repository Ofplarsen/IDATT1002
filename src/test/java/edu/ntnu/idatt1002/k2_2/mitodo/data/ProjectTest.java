package edu.ntnu.idatt1002.k2_2.mitodo.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    Project projectForTesting = new Project("Project");

    @Nested
    class TestsForBothGetTask{

        Task taskForTesting1 = new Task("Task1");
        Task taskForTesting2 = new Task("Task2");
        Task taskForTesting3 = new Task("Task3");

        @Test
        void getTaskByIdTest(){
            projectForTesting.addTask(taskForTesting1);
            projectForTesting.addTask(taskForTesting2);
            projectForTesting.addTask(taskForTesting3);
            UUID supposedId = taskForTesting2.getId();

            Assertions.assertEquals(taskForTesting2, projectForTesting.getTask(supposedId));
        }

    }

}