package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.EnumToStringConverter;
import edu.ntnu.idatt1002.k2_2.mitodo.data.FontSizeEnum;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.net.*;

/**
 * Class representing the settings page.
 * */
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

    /**
     * Initializes the page.
     */
    @FXML
    private void initialize()
    {
        boolean isSound = Client.getSettings().getIsSound();
        onSounds.setSelected(isSound);
        offSounds.setSelected(!isSound);

        selectFontSize.setConverter(new EnumToStringConverter<>());
        selectFontSize.getItems().setAll(FontSizeEnum.values());
        selectFontSize.setValue(Client.getSettings().getFontSize());
    }

    /**
     * Saves the changes.
     */
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

    /**
     * Handles the user manual button click.
     */
    @FXML
    private void userManual()
    {
        try
        {
            Desktop.getDesktop().browse(new URI("https://gitlab.stud.idi.ntnu.no/idatt1002_2021_k2-2/idatt1002_2021_k2-2/-/wikis/User-manual"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Handles the about button click.
     */
    @FXML
    private void about()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("MiTodo");
        alert.setContentText("Version 1.0.0 \nMade by MITOTO");

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
