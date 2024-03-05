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

    private LogWindow logWindow;
    private GameWindow gameWindow;


    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(inset, inset,
            screenSize.width  - inset * 2,
            screenSize.height - inset * 2);

        setContentPane(desktopPane);

        logWindow = createLogWindow();
        gameWindow = createGameWindow();
        addWindow(logWindow, 10, 10, 300, 800);
        addWindow(gameWindow, 300, 10, 400, 400);

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

            logWindow.dispose();
            gameWindow.dispose();
            this.dispose();
        }
    }

    protected GameWindow createGameWindow(){
        GameWindow gameWindow = new GameWindow();

        return gameWindow;
    }

    protected LogWindow createLogWindow() {

        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());


        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");

        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {

        desktopPane.add(frame);
        frame.setVisible(true);
    }

    protected void addWindow(JInternalFrame frame, int xLocation, int yLocation, int width, int height){
        frame.setSize(width, height);
        frame.setLocation(xLocation, yLocation);
        addWindow(frame);
    }

    public GameWindow getGameWindow(){
        return gameWindow;
    }

    public LogWindow getLogWindow(){
        return logWindow;
    }

}
