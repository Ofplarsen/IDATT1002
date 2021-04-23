package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

import edu.ntnu.idatt1002.k2_2.mitodo.data.task.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.RepeatEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

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
    public class getProjectsAndGetAllProjects{
        ArrayList<Project> projects = new ArrayList<>();

        @Nested
        @DisplayName("Tests for getProjects")
        public class getProjects {
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
        public class getAllProjects{
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
        public class getProject{
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
    public class addRemoveProject{
        @Nested
        @DisplayName("Tests for addProject")
        public class addProject{

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
            public class addProjectThrows{
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
        @Nested
        @DisplayName("Tests for removeProject and removeProjectFromAll method in project")
        public class removeProjectAndRemoveProjectFromAll{


            @Nested
            @DisplayName("Tests for removeProject")
            public class removeProject {
                RootProject rootProjectRemoveP = new RootProject();
                UserProject userProjectRemove1;
                UserProject userProjectRemove2;

                @BeforeEach
                void initRemoveProject() {
                    userProjectRemove1 = rootProjectRemoveP.addProject("Project1");
                    userProjectRemove2 = rootProjectRemoveP.addProject("Project2");
                }

                @Test
                void removeProjectTrue() {
                    assertTrue(rootProjectRemoveP.removeProject(userProjectRemove1.getID()));
                    ArrayList<UserProject> userProjects = new ArrayList<>();
                    userProjects.add(userProjectRemove2);
                    assertEquals(userProjects, rootProjectRemoveP.getProjects());
                }

                @Test
                void removeProjectFalse() {
                    assertTrue(rootProjectRemoveP.removeProject(userProjectRemove1.getID()));
                    assertFalse(rootProjectRemoveP.removeProject(userProjectRemove1.getID()));
                }
            }

            @Nested
            @DisplayName("Tests for removeProjectFromAll")
            public class removeProjectFromAll{
                //TODO MAKE TESTS FOR THIS SHIT :))))
            }
        }
    }

    @Nested
    @DisplayName("Tests for task methods in Project")
    public class taskMethods{
        RootProject rootProjectTaskTest = new RootProject();
        UserProject userProjectTaskTest1;
        UserProject userProjectTaskTest2;
        UserProject subProjectTaskTest1;
        ArrayList<Task> allTasks;
        Task userTask1;
        Task userTask2;
        Task subTask;
        @BeforeEach
        void initTaskMethods(){
            userProjectTaskTest1 = rootProjectTaskTest.addProject("UserProdTest1");
            userProjectTaskTest2 = rootProjectTaskTest.addProject("UserProdTest2");
            subProjectTaskTest1 = userProjectTaskTest1.addProject("Subproject");
            userTask1 = userProjectTaskTest1.addTask("TestTask1");
            userTask2 = userProjectTaskTest2.addTask("TestTask2");
            subTask = subProjectTaskTest1.addTask("TaskToSub");
        }

        @Nested
        @DisplayName("Tests for getAllTasks")
        public class getAllTasks{
            @BeforeEach
            void initGetTasks(){

                allTasks = new ArrayList<>();
                allTasks.add(userTask1);
                allTasks.add(userTask2);
                allTasks.add(subTask);
            }
            @Test
            void getAllTasksTrue() {
                assertEquals(allTasks.size(), rootProjectTaskTest.getAllTasks().size());
                subProjectTaskTest1.addTask("newTask");
                assertNotEquals(allTasks.size(), rootProjectTaskTest.getAllTasks().size());
            }
            @Test
            void getAllTasksFalse(){
                allTasks.remove(subTask);
                assertNotEquals(allTasks, rootProjectTaskTest.getAllTasks());
            }


        }

        @Nested
        @DisplayName("Tests for add/move and remove task")
        public class addRemoveMoveTask{

            @Test
            void addTaskTrue() {
                Task testTask = rootProjectTaskTest.addTask("Task");
                Task testTask2 = rootProjectTaskTest.addTask("Task2", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT, "");
                assertTrue(rootProjectTaskTest.getAllTasks().stream().anyMatch(t -> t.equals(testTask)));
                assertTrue(rootProjectTaskTest.getAllTasks().stream().anyMatch(t -> t.equals(testTask2)));
            }
            /*
            //TODO skal jeg gjøre dette her? :P
            @Nested
            @DisplayName("Tests to check if the correct throws are made if addTask fails")
            class addTaskFalse{
                @Test
                void addTaskDateDueBeforeStartThrows(){
                    try{
                        Task testTask2 = rootProjectTaskTest.addTask("Task3", PriorityEnum.High, LocalDate.now(), LocalDate.of(1000,10,1), RepeatEnum.DoesNotRepeat, "");
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), );
                    }
                }
            }


             */

            @Test
            void moveTaskTrue() {
                Task localTask = rootProjectTaskTest.addTask("task1");
                assertEquals(rootProjectTaskTest.getTasks().size(), 1);
                assertEquals(subProjectTaskTest1.getTasks().size(), 1);
                assertTrue(rootProjectTaskTest.moveTask(localTask, subProjectTaskTest1));
                assertEquals(subProjectTaskTest1.getTasks().size(), 2);
            }

            @Test
            void moveTaskNull(){
                rootProjectTaskTest.addTask("Task2");
                assertEquals(rootProjectTaskTest.getTasks().size(), 1);
                assertFalse(rootProjectTaskTest.moveTask(task1, subProjectTaskTest1));
                assertEquals(rootProjectTaskTest.getTasks().size(), 1);
            }

            @Test
            void removeTaskTrue() {
                Task localTask = subProjectTaskTest1.addTask("localTask");
                assertEquals(subProjectTaskTest1.getTasks().size(), 2);
                assertTrue(subProjectTaskTest1.removeTask(localTask.getID()));
                assertEquals(subProjectTaskTest1.getTasks().size(), 1);
            }

            @Test
            void removeTaskFalse(){
                Task localTask = subProjectTaskTest1.addTask("localTask");
                assertTrue(subProjectTaskTest1.removeTask(localTask.getID()));
                assertFalse(subProjectTaskTest1.removeTask(localTask.getID()));
            }

        }
    }

}