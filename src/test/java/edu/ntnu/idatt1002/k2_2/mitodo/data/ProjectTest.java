package edu.ntnu.idatt1002.k2_2.mitodo.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class ProjectTest {

    Project project = new Project("Project");
    Task task1 = new Task("Task1");
    Task task2 = new Task("Task2");
    Task task3 = new Task("Task3");

    @BeforeEach
    void init(){
        project.addTask(task1);
        project.addTask(task2);
        project.addTask(task3);
    }

    @Nested
    class TestsForBothGetTaskMethods{

        @Nested
        class GetTasksById{

            @Test
            void idBelongsToATaskInProject(){
                UUID supposedId = task2.getID();

                Assertions.assertEquals(task2, project.getTask(supposedId));
            }

            @Test
            void idDoesNotBelongToAnyTaskInProject(){
                UUID id = UUID.randomUUID();

                Assertions.assertNull(project.getTask(id));
            }
        }

        @Nested
        class GetTaskByName{

            @Test
            void nameBelongsToATaskInProject(){
                String supposedName = "Task1";

                Assertions.assertEquals(task1, project.getTask(supposedName));
            }

            @Test
            void nameDoesNotBelongToAnyTaskInProject(){
                String task4 = "Task4";

                Assertions.assertNull(project.getTask(task4));
            }
        }
    }

    @Nested
    class TestsForBothGetProjectMethods {

        Project project2 = new Project("Project2");
        Task task4 = new Task("Task4");
        Task task5 = new Task("Task5");
        Task task6 = new Task("Task6");

        @BeforeEach
        void prep(){
            project2.addTask(task4);
            project2.addTask(task5);
            project2.addTask(task6);
            project.addProject(project2);
        }

        @Nested
        class GetProjectById{

            @Test
            void projectWithIdIsInAnotherProject(){
                UUID project2Id = project2.getID();

                Assertions.assertNotNull(project.getProject(project2Id));
            }

            @Test
            void idDoesNotBelongToAnyProjectInAnotherProject(){
                UUID notAProjectId = UUID.randomUUID();

                Assertions.assertNull(project.getProject(notAProjectId));
            }
        }
    }

    @Nested
    class TestsForBothMoveProjectMethods {

        Project project2 = new Project("Project2");
        Project project3 = new Project("Project3");
        Project project4 = new Project("Project4");

        @BeforeEach
        void setup() {
            project.addProject(project2);
            project.addProject(project3);
            project.addProject(project4);

        }

    @Nested
    class TestsForBothRemoveProjectMethods {

        Project project2 = new Project("Project2");
        Project project3 = new Project("Project3");
        Project project4 = new Project("Project4");

        @BeforeEach
        void setup() {
            project.addProject(project2);
            project.addProject(project3);
            project.addProject(project4);
        }

        @Nested
        class RemoveAProjectById {

            @Test
            void removeASubprojectFromAProject(){
                Assertions.assertTrue(project.removeProject(project2.getID()));
            }

            @Test
            void removeAProjectThatIsNotASubproject(){
                Project project5 = new Project("Project5");
                Assertions.assertFalse(project.removeProject(project5.getID()));
            }
        }
    }
}