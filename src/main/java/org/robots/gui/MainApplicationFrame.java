package org.robots.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import org.robots.log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(inset, inset,
            screenSize.width  - inset * 2,
            screenSize.height - inset * 2);

        setContentPane(desktopPane);

        addWindow(createLogWindow());
        addWindow(createGameWindow());

        setJMenuBar(new MenuBar(this));

        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent event){
                MainApplicationFrame.this.confirmWindowClose();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void confirmWindowClose(){
        if (JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите закрыть приложение?",
                "Закрыть?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    protected GameWindow createGameWindow(){
        GameWindow gameWindow = new GameWindow();
        gameWindow.setLocation(300, 10);
        gameWindow.setSize(400, 400);

        return gameWindow;
    }

    protected LogWindow createLogWindow() {

        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);

        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");

        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {

        desktopPane.add(frame);
        frame.setVisible(true);
    }

}
