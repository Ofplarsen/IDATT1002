package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.io.Serializable;

/**
 * A class representing the application settings.
 *
 * @version 1.0.0
 */
public class Settings implements Serializable
{
    private boolean isSound;
    private FontSizeEnum fontSize;

    public Settings(boolean isSound, FontSizeEnum fontSize)
    {
        this.isSound = isSound;
        this.fontSize = fontSize;
    }

    public void setIsSound(boolean isSound)
    {
        this.isSound = isSound;
    }

    public boolean getIsSound()
    {
        return isSound;
    }

    public void setFontSize(FontSizeEnum fontSize)
    {
        this.fontSize = fontSize;
    }

    public FontSizeEnum getFontSize()
    {
        return fontSize;
    }
}
