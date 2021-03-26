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
                UUID supposedId = task2.getId();

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
    class TestsForBothMoveTaskMethods {

        Project project2 = new Project("Project2");

        @BeforeEach
        void init(){
            project.addProject(project2);
        }

        @Nested
        class MoveTaskByTitle{

            @Test
            void moveTaskThatIsInAProject(){
                project.moveTaskbyTitle("Task1", "Project2", project);

                Assertions.assertEquals(task1, project2.getTask("Task1"));
                Assertions.assertNull(project.getTask("Task1"));
            }

            @Test
            void moveTaskThatIsCurrentlyNotInAnyProject(){
                Task task4 = new Task("Task4");

                Assertions.assertThrows(IndexOutOfBoundsException.class, () -> project.moveTaskbyTitle("task4", "Project2", project));
            }
        }

        @Nested
        class MoveTaskById{

            @Test
            void moveTaskbyThatIsInAProject() {
                project.moveTask(task1.getId(), project2.getId(), project);

                Assertions.assertEquals(task1, project2.getTask("Task1"));
                Assertions.assertNull(project.getTask("Task1"));
            }

            @Test
            void moveTaskThatIsCurrentlyNotInAnyProject(){
                Task task4 = new Task("Task4");

                Assertions.assertThrows(IndexOutOfBoundsException.class, () -> project.moveTask(task4.getId(), project2.getId(), project));
            }
        }

    }

}