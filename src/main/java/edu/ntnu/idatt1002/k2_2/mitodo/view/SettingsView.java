package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.util.SoundEffects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SettingsView extends View
{
    @FXML
    private VBox parent;
    @FXML
    private ChoiceBox<FontSizeEnum> selectFontSize;
    @FXML
    private ToggleButton onSounds;
    @FXML
    private ToggleButton offSounds;
    @FXML
    private Text savedText;

    public void initialize()
    {
        selectFontSize.getItems().setAll(FontSizeEnum.values());
        selectFontSize.setValue(Client.getPrimaryView().getCurrentFontSize());
        if(SoundEffects.toggleSound){
            onSounds.setSelected(true);
        } else {
            offSounds.setSelected(true);
        }
    }

    @FXML
    private void save()
    {
        Client.getPrimaryView().setFontSize(selectFontSize.getValue());

        if(onSounds.isSelected())
        {
            SoundEffects.setToggleSound(true);
        }
        else
        {
            SoundEffects.setToggleSound(false);
            onSounds.setSelected(false);
            offSounds.setSelected(true);
        }
    }

    public void about()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Mitodo");
        alert.setContentText("Version 1.0.0 \nMade by Mitoto");

        alert.showAndWait();
    }

    @Override
    public Node getParent()
    {
        return parent;
    }

    @Override
    public String getMainMenuTitle()
    {
        return "Settings";
    }

    @Override
    public boolean equals(View view)
    {
        return view instanceof SettingsView;
    }
}
