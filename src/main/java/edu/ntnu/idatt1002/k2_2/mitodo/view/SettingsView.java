package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.FontSizeEnum;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

public class SettingsView extends View{
    @FXML
    private Label label;
    @FXML
    private VBox parent;
    @FXML
    private ToggleButton on;
    @FXML
    private ToggleButton off;
    @FXML
    private ChoiceBox<FontSizeEnum> selectFontSize;

    public void initialize(){
        selectFontSize.getItems().setAll(FontSizeEnum.values());
        selectFontSize.setValue(Client.getPrimaryView().getCurrentFontSize());
    }

    public void save(){
        Client.getPrimaryView().setFontSize(selectFontSize.getValue());
    }

    public void about(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Mitodo");
        alert.setContentText("Version 1.0.0 \nMade by Mitoto");

        alert.showAndWait();

    }

    @Override
    public Node getParent() {
        return parent;
    }
}
