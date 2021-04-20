package edu.ntnu.idatt1002.k2_2.mitodo.effects;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class SoundEffects
{
    public static boolean toggleSound = true;

    public static void playPlingSound()
    {
        String musicFile = "src/main/resources/sounds/pling.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static void playErrorSound()
    {
        String musicFile = "src/main/resources/sounds/error.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        if(toggleSound){
            mediaPlayer.play();
        }
    }

    public static void setToggleSound(boolean bool){
        toggleSound = bool;
    }
}
