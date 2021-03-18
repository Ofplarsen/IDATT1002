package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import edu.ntnu.idatt1002.k2_2.mitodo.data.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Subproject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

    @Test
    void addProject() {
        assertEquals(DataManager.getProjects().size(), 2);
        Project projectTest = DataManager.addProject("Test project");
        assertEquals(DataManager.getProjects().size(), 2+1);
    }

    @Test
    void removeProject() {
        assertEquals(DataManager.getProjects().size(), 2);
        Project projectTest = DataManager.addProject("Test project");
        assertEquals(DataManager.getProjects().size(), 2+1);
        DataManager.removeProject(new Project("Test project"));
        assertEquals(DataManager.getProjects().size(), 2);
    }

    @Test
    void getProjects() {
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

    @Test
    void getSettings() {

    }
}