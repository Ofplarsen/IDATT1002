module mitodo {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;

    opens edu.ntnu.idatt1002.k2_2.mitodo.view to javafx.fxml;
    opens edu.ntnu.idatt1002.k2_2.mitodo.view.components to javafx.fxml;

    exports edu.ntnu.idatt1002.k2_2.mitodo;
    exports edu.ntnu.idatt1002.k2_2.mitodo.data.project;
    exports edu.ntnu.idatt1002.k2_2.mitodo.data.task;
    exports edu.ntnu.idatt1002.k2_2.mitodo.file;
    exports edu.ntnu.idatt1002.k2_2.mitodo.view;
    exports edu.ntnu.idatt1002.k2_2.mitodo.view.components;
    exports edu.ntnu.idatt1002.k2_2.mitodo.effects;
    exports edu.ntnu.idatt1002.k2_2.mitodo.view.edittask;
    opens edu.ntnu.idatt1002.k2_2.mitodo.view.edittask to javafx.fxml;
    exports edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;
    opens edu.ntnu.idatt1002.k2_2.mitodo.view.editproject to javafx.fxml;
}