package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.EnumToStringConverter;
import edu.ntnu.idatt1002.k2_2.mitodo.data.FontSizeEnum;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.*;

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

    public void initialize()
    {
        boolean isSound = Client.getSettings().getIsSound();
        onSounds.setSelected(isSound);
        offSounds.setSelected(!isSound);

        selectFontSize.setConverter(new EnumToStringConverter<>());
        selectFontSize.getItems().setAll(FontSizeEnum.values());
        selectFontSize.setValue(Client.getSettings().getFontSize());
    }

    @FXML
    private void save()
    {
        Client.getPrimaryView().setFontSize(selectFontSize.getValue());

        if(onSounds.isSelected())
        {
            Client.getSettings().setIsSound(true);
            offSounds.setSelected(false);
        }
        else
        {
            Client.getSettings().setIsSound(false);
            onSounds.setSelected(false);
            offSounds.setSelected(true);
        }
    }

    public void userManual() {
        try {
            Desktop.getDesktop().browse(new URI("https://gitlab.stud.idi.ntnu.no/idatt1002_2021_k2-2/idatt1002_2021_k2-2/-/wikis/User-manual"));
        } catch (Exception e){
            e.printStackTrace();
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
