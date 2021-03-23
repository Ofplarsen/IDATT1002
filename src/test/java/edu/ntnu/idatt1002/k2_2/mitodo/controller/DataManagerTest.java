package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import edu.ntnu.idatt1002.k2_2.mitodo.data.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Subproject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {

    @BeforeEach
    public void init() {

        DataManager.getQuickTasks().addTask("Buy milk", PriorityEnum.HIGH);
        DataManager.getQuickTasks().addTask("Do this", PriorityEnum.HIGH);
        DataManager.getQuickTasks().addTask("Do that", PriorityEnum.HIGH);

        Project projectNTNU = DataManager.addProject("NTNU");
        Subproject subprojectNTNUMatte = projectNTNU.addSubproject("Matematiske metoder", Color.LIGHTBLUE);
        Subproject subprojectNTNUSystem = projectNTNU.addSubproject("Systemutvikling", Color.LIGHTYELLOW);
        Subproject subprojectNTNUProgram = projectNTNU.addSubproject("Programmering", Color.LIGHTPINK);

        Project projectPersonal = DataManager.addProject("Personal");
        Subproject subprojectPersonalWorkOut = projectPersonal.addSubproject("Work out plan", Color.LIGHTSEAGREEN);
    }
    @AfterEach
    public void delete(){
        DataManager.getQuickTasks().removeTask(new Task("Buy milk", PriorityEnum.HIGH));
        DataManager.getQuickTasks().removeTask(new Task("Do this", PriorityEnum.HIGH));
        DataManager.getQuickTasks().removeTask(new Task("Do that", PriorityEnum.HIGH));

        DataManager.removeProject(new Project("Personal"));
        DataManager.removeProject(new Project("NTNU"));
    }
    @Nested
    @DisplayName("Tests to add and remove projects")
    class addAndRemoveProjects {

        @Test
        void addProject() {

            assertEquals(DataManager.getProjects().size(), 2);
            Project projectTest = DataManager.addProject("Test project");
            assertEquals(DataManager.getProjects().size(), 3);
        }

        @Test
        void removeProject() {

            assertEquals(DataManager.getProjects().size(), 2);
            Project projectTest = DataManager.addProject("Test project");
            assertEquals(DataManager.getProjects().size(), 2 + 1);
            DataManager.removeProject(new Project("Test project"));
            assertEquals(DataManager.getProjects().size(), 2);
        }
    }


    @Nested
    @DisplayName("Tests to make sure Quicktasks in DataManager works")
    class quickTaskTests {

        @Test
        void getQuickTasksDoesNotContainTask(){

            assertFalse(DataManager.getQuickTasks().getTasks().contains(new Task("milk", PriorityEnum.HIGH)));
            assertFalse(DataManager.getQuickTasks().getTasks().contains(new Task("this", PriorityEnum.HIGH)));
            assertFalse(DataManager.getQuickTasks().getTasks().contains(new Task("o that", PriorityEnum.HIGH)));
        }

        @Test
        void getQuickTasksContainsTask() {

            assertTrue(DataManager.getQuickTasks().getTasks().contains(new Task("Buy milk", PriorityEnum.HIGH)));
            assertTrue(DataManager.getQuickTasks().getTasks().contains(new Task("Do this", PriorityEnum.HIGH)));
            assertTrue(DataManager.getQuickTasks().getTasks().contains(new Task("Do that", PriorityEnum.HIGH)));
        }


    }

}