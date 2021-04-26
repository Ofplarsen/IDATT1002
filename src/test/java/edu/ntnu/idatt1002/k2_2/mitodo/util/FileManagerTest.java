package edu.ntnu.idatt1002.k2_2.mitodo.util;

import edu.ntnu.idatt1002.k2_2.mitodo.data.project.RootProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Tests for Persistence")
public class FileManagerTest {
    RootProject rootProject = new RootProject();
    @Test
    void getComponent() {

    }

    @Nested
    @DisplayName("Tests for saving and loading objects to/from file")
    public class SaveAndLoadSerializableObject{

        @Test
        void saveTask(){
            Task testTask = rootProject.addTask("Test task");
            FileManager.saveSerializableObject(testTask,"UnitTestFile");
            assertNotNull(FileManager.loadSerializableObject("UnitTestFile"));
            assertEquals(testTask,FileManager.loadSerializableObject("UnitTestFile"));
        }
        @Test
        void saveProject(){
            UserProject userProject = rootProject.addProject("UserProject");
            FileManager.saveSerializableObject(userProject, "UnitTestFile");
            assertNotNull(FileManager.loadSerializableObject("UnitTestFile"));
            assertEquals(userProject,FileManager.loadSerializableObject("UnitTestFile"));
        }
        @Test
        void saveProjectWithTask(){
            UserProject userProject = rootProject.addProject("UserProject");
            Task testTask = userProject.addTask("Test task");
            FileManager.saveSerializableObject(userProject, "UnitTestFile");
            assertNotNull(FileManager.loadSerializableObject("UnitTestFile"));
            assertEquals(userProject,FileManager.loadSerializableObject("UnitTestFile"));
            UserProject userProjectCopy = (UserProject) FileManager.loadSerializableObject("UnitTestFile");
            assertTrue(userProjectCopy.getTasks().stream().anyMatch(t-> t.equals(testTask)));
        }
        @Test
        void saveRootProject(){
            UserProject userProject = rootProject.addProject("UserProject");
            UserProject userProject1 = rootProject.addProject("UserProject1");
            UserProject userProject2 = rootProject.addProject("UserProject2");
            Task userProject1Task = userProject1.addTask("Task1");
            Task userProject2Task = userProject2.addTask("Task2");
            UserProject subProjectUP1 = userProject1.addProject("subProject");
            Task subProUP1Task = subProjectUP1.addTask("task11");
            FileManager.saveSerializableObject(rootProject, "UnitTestFile");
            RootProject rootProjectCopy = (RootProject) FileManager.loadSerializableObject("UnitTestFile");
            assertNotNull(rootProjectCopy);
            assertTrue(rootProjectCopy.getProjects().stream().anyMatch(p-> p.equals(userProject1)));
            assertTrue(rootProjectCopy.getProjects().stream().anyMatch(p-> p.equals(userProject2)));
            assertTrue(rootProjectCopy.getProjects().stream().anyMatch(p-> p.equals(userProject)));
            assertTrue(rootProjectCopy.getProject(userProject1.getID()).getProjects().stream().anyMatch(p-> p.equals(subProjectUP1)));
            assertTrue(rootProjectCopy.getProject(userProject1.getID()).getTasks().stream().anyMatch(t->t.equals(userProject1Task)));
            assertTrue(rootProjectCopy.getProject(userProject1.getID()).getProject(subProjectUP1.getID()).getTasks().stream().anyMatch(t-> t.equals(subProUP1Task)));
        }
    }
}