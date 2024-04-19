package org.robots.gui;

import org.robots.model.GameModel;
import org.robots.model.Robot;
import org.robots.state.SaveableWindow;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class GameStateWindow extends SaveableWindow implements Observer {
    private GameModel gameModel;
    private JTextArea textField;

    public GameStateWindow(GameModel gameModel){
        super("Окно состояния игры", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());

        textField = new JTextArea();
        panel.add(textField, BorderLayout.CENTER);

        getContentPane().add(panel);
        pack();

        this.gameModel = gameModel;
        gameModel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(gameModel)) {
            if (arg.equals(Robot.robotMoved))
                onRobotMoved();
        }
    }

    private void onRobotMoved() {
        textField.setText
                (String.format("Position: (%f, %f)\nDirection: %f",
                    gameModel.getRobot().getRobotPosition().x,
                    gameModel.getRobot().getRobotPosition().y,
                    gameModel.getRobot().getRobotDirection())
                );
    }

}
