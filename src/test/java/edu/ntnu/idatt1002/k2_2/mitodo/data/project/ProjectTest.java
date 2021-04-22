package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    RootProject rootProject = new RootProject();
    Task task1 = new Task("Task1", rootProject);
    Task task2 = new Task("Task2", rootProject);
    Task task3 = new Task("Task3", rootProject);

    UserProject userProject1;
    UserProject subProjectUP1;
    UserProject userProject2;
    UserProject userProject3;
    @BeforeEach
    void init(){
        rootProject.addTask(task1.getTitle());
        rootProject.addTask(task2.getTitle());
        rootProject.addTask(task3.getTitle());

        userProject1 = rootProject.addProject("User Project1");
        subProjectUP1 = userProject1.addProject("Subproject UP1");
        userProject2 = rootProject.addProject("User Project2");
        userProject3 = rootProject.addProject("User Project3");
    }


    @Nested
    @DisplayName("Test to make for all getProjects methods ")
    class getProjectsAndGetAllProjects{
        ArrayList<Project> projects = new ArrayList<>();

        @Nested
        @DisplayName("Tests for getProjects")
        class getProjects {
            @BeforeEach
            void initGetProjects() {

                projects.add(userProject1);
                projects.add(userProject2);
                projects.add(userProject3);
            }

            @Test
            void getProjectsSame() {

                assertEquals(projects, rootProject.getProjects());
            }

            @Test
            void getProjectsDifferent() {
                projects.remove(userProject3);
                assertNotEquals(projects, rootProject.getProjects());
            }
        }

        @Nested
        @DisplayName("Tests for getAllProjects")
        class getAllProjects{
            @BeforeEach
            void initGetProjects() {

                projects.add(userProject1);
                projects.add(userProject2);
                projects.add(userProject3);
                projects.add(subProjectUP1);
            }

            @Test
            void getAllProjectsEquals() {
                assertEquals(projects, rootProject.getAllProjects());
            }

            @Test
            void getAllProjectsNotEquals(){
                projects.remove(subProjectUP1);
                assertNotEquals(projects, rootProject.getAllProjects());
            }

            @Test
            void getAllProjectsVsGetProjects(){
                assertNotEquals(rootProject.getAllProjects(), rootProject.getProjects());
            }
        }

        @Nested
        @DisplayName("Tests for getProject(UUID)")
        class getProject{
            @Test
            void getProjectEquals(){
                assertEquals(userProject1, rootProject.getProject(userProject1.getID()));
            }

            @Test
            void getProjectsNotEquals(){
                assertNotEquals(userProject1, rootProject.getProject(userProject2.getID()));
            }
        }
    }

    @Nested
    @DisplayName("Tests for add/remove project")
    class addRemoveProject{
        @Nested
        @DisplayName("Tests for addProject")
        class addProject{

            RootProject rootProjectAddProject = new RootProject();
            UserProject addProjectUP;
            @BeforeEach
            void initAddProject(){

            }
            @Test
            void addProjectTrue(){
                addProjectUP = rootProject.addProject("New Project");
                assertEquals(addProjectUP,rootProject.getProject(addProjectUP.getID()));
            }

            @Nested
            @DisplayName("Tests to make sure addProject throws exception under certain conditions")
            class addProjectThrows{
                @Test
                void addProjectNoNameThrowsIllEx(){
                    assertThrows(IllegalArgumentException.class, () -> {
                        rootProjectAddProject.addProject("");
                    });

                }

                @Test
                void addProjectNoNameThrowsMessage(){
                    try{
                        rootProjectAddProject.addProject("");
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), "Title of projects can't be empty");
                    }
                }

                @Test
                void addProjectSameProjectThrows(){
                    rootProjectAddProject.addProject("New Project");
                    assertThrows(IllegalArgumentException.class, () -> {
                        rootProjectAddProject.addProject("New Project");
                    });
                }

                @Test
                void addProjectSameProjectThrowsMessage(){
                    try{
                        rootProjectAddProject.addProject("New Project");
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), "Project already created");
                    }
                }


                @Test
                void addProjectLongerThan28CharThrows(){

                    assertThrows(IllegalArgumentException.class, () -> {
                        rootProjectAddProject.addProject("12345678901234567890123456789");
                    });
                }
                @Test
                void addProjectLongerThan28CharThrowsMessage(){
                    try{
                        rootProjectAddProject.addProject("12345678901234567890123456789");
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), "Project Name must be below 28.");
                    }
                }

                @Test
                void addProjectLessThan28CharOK(){
                    assertDoesNotThrow(() ->{
                        rootProjectAddProject.addProject("1234567890123456789012345678");
                    });
                }
            }
        }
    }

    @Test
    void addProject() {
    }

    @Test
    void removeProject() {
    }

    @Test
    void removeProjectFromAll() {
    }

    @Test
    void getAllTasks() {
    }

    @Test
    void getAllSubProjectTasks() {
    }

    @Test
    void removeTaskFromAll() {
    }

    @Test
    void getTask() {
    }

    @Test
    void addTask() {
    }

    @Test
    void moveTask() {
    }

    @Test
    void testMoveTask() {
    }

    @Test
    void testAddTask() {
    }

    @Test
    void removeTask() {
    }

    @Nested
    @DisplayName("Addproject tests")
    class addProject{
        @Test
        void addProjectTrue() {
            UserProject testpord = rootProject.addProject("Test");
            assertEquals(testpord.getParent(), rootProject);
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
                task2 = rootProject.getTasks().stream().filter(p -> p.getTitle().equals("Task2")).findFirst().get();
                UUID supposedId = rootProject.getTasks().stream().filter(p -> p.getTitle().equals("Task2")).findFirst().get().getID();

                assertEquals(task2, rootProject.getTask(supposedId));
            }

            @Test
            void idDoesNotBelongToAnyTaskInProject(){
                UUID id = UUID.randomUUID();

                assertNull(rootProject.getTask(id));
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
           rootProject.addProject("Project2");
        }

        @Nested
        class GetProjectById{

            @Test
            void projectWithIdIsInAnotherProject(){
                Project p = rootProject.getProjects().stream().filter(e -> e.getTitle().equals("Project2")).findFirst().get();

                assertNotNull(rootProject.getProject(p.getID()));
            }

            @Test
            void idDoesNotBelongToAnyProjectInAnotherProject(){
                UUID notAProjectId = UUID.randomUUID();

                assertNull(rootProject.getProject(notAProjectId));
            }
        }
    }

    @Nested
    class TestsForBothMoveProjectMethods {

        @BeforeEach
        void setup() {
            rootProject.addProject("project2");
            rootProject.addProject("project3");
            rootProject.addProject("project4");

        }
    }

    @Nested
    class TestsForBothRemoveProjectMethods {



        @BeforeEach
        void setup() {
            rootProject.addProject("project2");
            rootProject.addProject("project3");
            rootProject.addProject("project4");
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
                assertFalse(rootProject.removeProject(project5.getID()));
            }
        }
    }
}