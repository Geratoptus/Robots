package org.robots.state;

import javax.swing.*;
import java.util.prefs.Preferences;
import java.beans.PropertyVetoException;

public class SaveableWindow extends JInternalFrame implements WindowState{
    private static final String windowPreferences = "Preferences";
    private final String windowPositionX = "X";
    private final String windowPositionY = "Y";
    private final String windowSizeWidth = "Width";
    private final String windowSizeHeight = "Height";
    private final String windowIsMinimised = "IsIcon";
    private final JInternalFrame window = this;

    public SaveableWindow(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable){
        super(title, resizable, closable, maximizable, iconifiable);
    }

    private static Preferences getPreferences() {
        return Preferences.userRoot().node(windowPreferences);
    }
    public void saveState(){
        Preferences preferences = getPreferences();

        String title = window.getTitle();

        preferences.putInt(windowPositionX + title, window.getX());
        preferences.putInt(windowPositionY + title, window.getY());
        preferences.putInt(windowSizeWidth + title, window.getWidth());
        preferences.putInt(windowSizeHeight + title, window.getHeight());
        preferences.putBoolean(windowIsMinimised + title, window.isIcon());
    }

    public void restoreState(){
        Preferences preferences = getPreferences();
        final int def = -1;

        String title = window.getTitle();

        int x = preferences.getInt(windowPositionX + title, def);
        int y = preferences.getInt(windowPositionY + title, def);
        int width = preferences.getInt(windowSizeWidth + title, def);
        int height = preferences.getInt(windowSizeHeight + title, def);
        boolean isMinimised = preferences.getBoolean(windowIsMinimised + title, false);

        if (x == -1 || y == -1 || width == -1 || height == -1)
            return;

        window.setBounds(x, y, width, height);
        try {
            window.setIcon(isMinimised);
        } catch (PropertyVetoException e) {
            e.printStackTrace(System.out);
        }

    }

}
