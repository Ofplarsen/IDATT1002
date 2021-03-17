module mitodo {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    opens edu.ntnu.idatt1002.k2_2.mitodo.view to javafx.fxml;
    exports edu.ntnu.idatt1002.k2_2.mitodo;
}