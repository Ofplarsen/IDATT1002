package edu.ntnu.idatt1002.k2_2.mitodo.data;

import edu.ntnu.idatt1002.k2_2.mitodo.controller.DataManager;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubprojectTest {

    Subproject ntnuMatte = new Subproject("NTNU Matte", Color.LIGHTBLUE);

    @Nested
    @DisplayName("Tests to test setTitle method")
    class setTitle{

        @Test
        void setTitleTrue() {
            assertTrue(ntnuMatte.getTitle().equals("NTNU Matte"));
            ntnuMatte.setTitle("NTNU Prog");
            assertEquals(ntnuMatte.getTitle(), "NTNU Prog");
        }

        @Test
        void setTitleFalse() {
            assertTrue(ntnuMatte.getTitle().equals("NTNU Matte"));
            ntnuMatte.setTitle("NTNU Prog");
            assertNotEquals(ntnuMatte.getTitle(), "NTNU Pro");
        }
    }
    @Nested
    @DisplayName("Tests for getTitle method")
    class getTitle{

        @Test
        void getTitleEquals() {
            assertEquals(ntnuMatte.getTitle(), "NTNU Matte");
        }

        @Test
        void getTitleDoesNotEquals(){
            assertNotEquals(ntnuMatte.getTitle(), "NTNU Prog");
        }
    }
    @Nested
    @DisplayName("Tests for setColor and getColor methods")
    class setColorAndGetColor{
        @Test
        void setColorAndGetColorTrue() {
            assertEquals(ntnuMatte.getColor(), Color.LIGHTBLUE);
            ntnuMatte.setColor(Color.LIGHTPINK);
            assertEquals(ntnuMatte.getColor(), Color.LIGHTPINK);
        }

        @Test
        void setColorAndGetColorFalse(){
            assertNotEquals(ntnuMatte.getColor(), Color.LIGHTPINK);
            ntnuMatte.setColor(Color.LIGHTPINK);
            assertNotEquals(ntnuMatte.getColor(), Color.LIGHTBLUE);
        }
    }


    @Test
    void addTask() {

    }

    @Test
    void removeTask() {
    }

    @Test
    void setTasks() {
    }

    @Test
    void getTasks() {
    }
}