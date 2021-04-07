module mitodo {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    opens edu.ntnu.idatt1002.k2_2.mitodo.view to javafx.fxml;
    opens edu.ntnu.idatt1002.k2_2.mitodo.data to com.fasterxml.jackson.databind;
    exports edu.ntnu.idatt1002.k2_2.mitodo;
    exports edu.ntnu.idatt1002.k2_2.mitodo.data to com.fasterxml.jackson.databind;
}