package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.FontSizeEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.PriorityEnum;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
        selectFontSize.setValue(FontSizeEnum.Medium);
    }

    public void save(){
        if(selectFontSize.getValue().equals(FontSizeEnum.Small)){
            Client.getPrimaryView().setSmallText();
        } else if(selectFontSize.getValue().equals(FontSizeEnum.Big)){
            Client.getPrimaryView().setBigText();
        }
    }

    @Override
    public Node getParent() {
        return parent;
    }
}
