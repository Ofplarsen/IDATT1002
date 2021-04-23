package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserProjectTest {

    RootProject rootProject = new RootProject();
    UserProject userProject1;
    UserProject userProject11;
    UserProject userProject2;
    @BeforeEach
    void init(){
        userProject1 = rootProject.addProject("Project1");
        userProject2 = rootProject.addProject("Project2");
        userProject11 = userProject1.addProject("Project11");
    }


    @Test
    void setTitle() {
    }

    @Nested
    @DisplayName("Tests for the method projectAlreadyCreated")
    public class projectAlreadyCreated{
        @Test
        void projectNotCreated() {

            assertFalse(userProject1.projectAlreadyCreated("Project12"));
        }

        @Test
        void projectCreated(){
            assertTrue(userProject1.projectAlreadyCreated("Project1"));
        }

        @Test
        @DisplayName("Test where we check what in the method is wrong")
        void projectCreatedMethod(){
            assertFalse(userProject1.getParent()==null);
            assertFalse(userProject1.getParent().getProjects().size() == 0);
            assertTrue(userProject1.getParent().getProjects().stream().anyMatch(p -> p.getTitle().equalsIgnoreCase("Project1")));
            assertTrue(userProject1.projectAlreadyCreated("Project1"));
        }
    }
    @Nested
    public class getParent{
        @Test
        void getParentTest() {
            assertEquals(userProject1, userProject11.getParent());
        }
    }
}