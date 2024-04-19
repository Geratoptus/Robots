package org.robots.gui;

import org.robots.model.Robot;
import org.robots.state.SaveableWindow;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class RobotStateWindow extends SaveableWindow implements Observer {
    private Robot robot;
    private JTextArea textField;

    public RobotStateWindow(Robot robot){
        super("Окно состояния робота", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());

        textField = new JTextArea();
        panel.add(textField, BorderLayout.CENTER);

        getContentPane().add(panel);
        pack();

        this.robot = robot;
        robot.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(robot)) {
            if (arg.equals(Robot.robotMoved))
                onRobotMoved();
        }
    }

    private void onRobotMoved() {
        textField.setText(String.format("Position: (%f, %f)\nDirection: %f",
                robot.getRobotPosition().x, robot.getRobotPosition().y, robot.getRobotDirection()));
    }

}
