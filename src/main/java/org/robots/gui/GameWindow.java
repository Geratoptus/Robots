package org.robots.gui;

import org.robots.model.GameModel;
import org.robots.state.SaveableWindow;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class GameWindow extends SaveableWindow {
    private final GameVisualizer visualizer;
    public GameWindow(GameModel gameModel) {
        super("Игровое поле", true, true, true, true);
        visualizer = new GameVisualizer(gameModel);
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
