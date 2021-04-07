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
    class TestsForBothMoveTaskMethods {

        Project project2 = new Project("Project2");

        @BeforeEach
        void setup(){
            project.addProject(project2);
        }

        @Nested
        class MoveTaskByTitle{

            @Test
            void moveTaskThatIsInAProject(){
                project.moveTaskbyTitle("Task1", project2.getTitle(), project);

                Assertions.assertEquals(task1, project2.getTask("Task1"));
                Assertions.assertNull(project.getTask("Task1"));
            }

            @Test
            void moveTaskThatIsCurrentlyNotInAnyProject(){
                Task task4 = new Task("Task4");
                String task4Title = task4.getTitle();

                Assertions.assertThrows(IndexOutOfBoundsException.class, () -> project.moveTaskbyTitle(task4Title, "Project2", project));
            }
        }

        @Nested
        class MoveTaskById{

            @Test
            void moveTaskByThatIsInAProject() {
                project.moveTask(task1.getID(), project2.getID(), project);

                Assertions.assertEquals(task1, project2.getTask("Task1"));
                Assertions.assertNull(project.getTask("Task1"));
            }

            @Test
            void moveTaskThatIsCurrentlyNotInAnyProject(){
                Task task4 = new Task("Task4");

                Assertions.assertThrows(IndexOutOfBoundsException.class, () -> project.moveTask(task4.getID(), project2.getID(), project));
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

        @Nested
        class GetProjectByTitle {

            @Test
            void projectWithTitleIsInAnotherProject(){
                String project2Title = project2.getTitle();

                Assertions.assertNotNull(project.getProjectbyTitle(project2Title));
            }

            @Test
            void titleDoesNotBelongToAnyProjectInAnotherProject(){
                String notAProjectName = "NotAProjectName";

                Assertions.assertNull(project.getProjectbyTitle(notAProjectName));
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
        class MoveProjectById {

            @Test
            void moveASubprojectToAnotherIndexWithinAProject(){
                UUID project4Id = project4.getID();
                int project4IndexBeforeMoving = project.getProjects().indexOf(project4);

                project.moveProject(project4Id, 0);
                int project4IndexAfterMoving = project.getProjects().indexOf(project4);

                Assertions.assertEquals(2, project4IndexBeforeMoving);
                Assertions.assertEquals(0, project4IndexAfterMoving);
            }

            @Test
            void moveAProjectThatIsNotInAnotherProject(){
                Project project5 = new Project("Project5");
                UUID project5Id = project5.getID();

                int project2IndexBeforeMoving = project.getProjects().indexOf(project2);
                int project3IndexBeforeMoving = project.getProjects().indexOf(project3);
                int project4IndexBeforeMoving = project.getProjects().indexOf(project4);

                project.moveProject(project5Id, 1);

                int project2IndexAfterMoving = project.getProjects().indexOf(project2);
                int project3IndexAfterMoving = project.getProjects().indexOf(project3);
                int project4IndexAfterMoving = project.getProjects().indexOf(project4);

                Assertions.assertEquals(project2IndexBeforeMoving, project2IndexAfterMoving);
                Assertions.assertEquals(project3IndexBeforeMoving, project3IndexAfterMoving);
                Assertions.assertEquals(project4IndexBeforeMoving, project4IndexAfterMoving);
            }
        }

        @Nested
        class MoveProjectByTitle {

            @Test
            void moveASubprojectToAnotherIndexWithinAProject(){
                String project4Title = project4.getTitle();
                int project4IndexBeforeMoving = project.getProjects().indexOf(project4);

                project.moveProjectbyTitle(project4Title, 0);
                int project4IndexAfterMoving = project.getProjects().indexOf(project4);

                Assertions.assertEquals(2, project4IndexBeforeMoving);
                Assertions.assertEquals(0, project4IndexAfterMoving);
            }

            @Test
            void moveAProjectThatIsNotInAnotherProject(){
                Project project5 = new Project("Project5");
                String project5Title = project5.getTitle();

                int project2IndexBeforeMoving = project.getProjects().indexOf(project2);
                int project3IndexBeforeMoving = project.getProjects().indexOf(project3);
                int project4IndexBeforeMoving = project.getProjects().indexOf(project4);

                project.moveProjectbyTitle(project5Title, 1);

                int project2IndexAfterMoving = project.getProjects().indexOf(project2);
                int project3IndexAfterMoving = project.getProjects().indexOf(project3);
                int project4IndexAfterMoving = project.getProjects().indexOf(project4);

                Assertions.assertEquals(project2IndexBeforeMoving, project2IndexAfterMoving);
                Assertions.assertEquals(project3IndexBeforeMoving, project3IndexAfterMoving);
                Assertions.assertEquals(project4IndexBeforeMoving, project4IndexAfterMoving);
            }
        }
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

        @Nested
        class RemoveAProjectByTitle {

            @Test
            void removeASubprojectFromAProject(){
                Assertions.assertTrue(project.removeProjectbyTitle(project2.getTitle()));
            }

            @Test
            void removeAProjectThatIsNotASubproject(){
                Project project5 = new Project("Project5");
                Assertions.assertFalse(project.removeProjectbyTitle(project5.getTitle()));
            }
        }
    }
}