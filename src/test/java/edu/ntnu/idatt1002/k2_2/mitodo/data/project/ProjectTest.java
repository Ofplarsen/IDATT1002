package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.RootProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import org.junit.jupiter.api.*;

import java.util.UUID;

class ProjectTest {

    RootProject project = new RootProject();
    Task task1 = new Task("Task1", project);
    Task task2 = new Task("Task2", project);
    Task task3 = new Task("Task3", project);

    @BeforeEach
    void init(){
        project.addTask(task1.getTitle());
        project.addTask(task2.getTitle());
        project.addTask(task3.getTitle());
    }

    @Nested
    @DisplayName("Addproject tests")
    class addProject{
        @Test
        void addProjectTrue() {
            UserProject testpord = project.addProject("Test");
            Assertions.assertEquals(testpord.getParent(), project);
        }
        @Test
        void addProjectFalse(){

        }
    }


    @Nested
    class TestsForBothGetTaskMethods{

        @Nested
        class GetTasksById{

            @Test
            void idBelongsToATaskInProject(){
                task2 = project.getTasks().stream().filter(p -> p.getTitle().equals("Task2")).findFirst().get();
                UUID supposedId = project.getTasks().stream().filter(p -> p.getTitle().equals("Task2")).findFirst().get().getID();

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

                //Assertions.assertEquals(task1, project.getTask(supposedName));
            }

            @Test
            void nameDoesNotBelongToAnyTaskInProject(){
                String task4 = "Task4";

               // Assertions.assertNull(project.getTask(task4));
            }
        }
    }

    @Nested
    class TestsForBothGetProjectMethods {

        RootProject project2 = new RootProject();
        Task task4 = new Task("Task4", project2);
        Task task5 = new Task("Task5", project2);
        Task task6 = new Task("Task6", project2);

        @BeforeEach
        void prep(){
            //project2.addTask(task4);
            //project2.addTask(task5);
            //project2.addTask(task6);
           project.addProject("Project2");
        }

        @Nested
        class GetProjectById{

            @Test
            void projectWithIdIsInAnotherProject(){
                Project p = project.getProjects().stream().filter(e -> e.getTitle().equals("Project2")).findFirst().get();

                Assertions.assertNotNull(project.getProject(p.getID()));
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

        @BeforeEach
        void setup() {
            project.addProject("project2");
            project.addProject("project3");
            project.addProject("project4");

        }
    }

    @Nested
    class TestsForBothRemoveProjectMethods {



        @BeforeEach
        void setup() {
            project.addProject("project2");
            project.addProject("project3");
            project.addProject("project4");
        }

        @Nested
        class RemoveAProjectById {

            @Test
            void removeASubprojectFromAProject(){
                //Assertions.assertTrue(project.removeProject(project2.getID()));
            }

            @Test
            void removeAProjectThatIsNotASubproject(){
                RootProject project5 = new RootProject();
                Assertions.assertFalse(project.removeProject(project5.getID()));
            }
        }
    }
}