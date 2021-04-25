package edu.ntnu.idatt1002.k2_2.mitodo.util;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * Class responsible for sound effects.
 */
public class SoundEffects
{
    /**
     * Plays "pling" sound.
     */
    public static void playPlingSound()
    {
        String musicFile = "src/main/resources/sounds/pling.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        if(Client.getSettings().getIsSound())
        {
            mediaPlayer.play();
        }
    }

    /**
     * Plays error sound.
     */
    public static void playErrorSound()
    {
        String musicFile = "src/main/resources/sounds/error.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        if(Client.getSettings().getIsSound())
        {
            mediaPlayer.play();
        }
    }
}
