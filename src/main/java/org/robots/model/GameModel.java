package org.robots.model;

import java.awt.*;
import java.util.Observable;

import static org.robots.model.GameMaths.*;

public class GameModel extends Observable {
    private final Robot robot;
    private volatile Position targetPosition = new Position(150, 100);
    public static final String robotMoved = "robot moved";

    public GameModel(Robot robot){
       this.robot = new Robot(robot.getRobotPosition().x, robot.getRobotPosition().y);
    }

    public Robot getRobot(){
        return robot;
    }

    public Position getTargetPosition(){ return targetPosition; }


    public void setTargetPosition(Point p) {
        targetPosition.x = p.x;
        targetPosition.y = p.y;
    }

    public void update(int width, int height){
        double distance = distance(targetPosition.x, targetPosition.y, robot.getRobotPosition().x, robot.getRobotPosition().y);
        if (distance < 0.5) {
            return;
        }

        double angularVelocity = countAngularVelocity(new Point((int)targetPosition.x, (int)targetPosition.y), robot);

        moveRobot(robot.getMaxVelocity(), angularVelocity, 10);
        robot.setRobotDirection(checkBounds(width, height, robot.getRobotDirection()));
        setChanged();
        notifyObservers(robotMoved);
        clearChanged();
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, robot.getMaxVelocity());
        angularVelocity = applyLimits(angularVelocity, -robot.getMaxAngularVelocity(), robot.getMaxAngularVelocity());

        double newX = robot.getRobotPosition().x + velocity / angularVelocity *
                (Math.sin(robot.getRobotDirection()  + angularVelocity * duration) -
                        Math.sin(robot.getRobotDirection()));

        if (!Double.isFinite(newX)) {
            newX = robot.getRobotPosition().x + velocity * duration * Math.cos(robot.getRobotDirection());
        }

        double newY = robot.getRobotPosition().y - velocity / angularVelocity *
                (Math.cos(robot.getRobotDirection()  + angularVelocity * duration) -
                        Math.cos(robot.getRobotDirection()));

        if (!Double.isFinite(newY)) {
            newY = robot.getRobotPosition().y + velocity * duration * Math.sin(robot.getRobotDirection());
        }

        robot.setRobotPosition(new Position(newX, newY));
        double newDirection = asNormalizedRadians(robot.getRobotDirection() + angularVelocity * duration);
        robot.setRobotDirection(newDirection);
    }
    private double checkBounds(int width, int height, double robotDirection) {
        if (width + height != 0) {
            if (robot.getRobotPosition().x < 0) {
                robotDirection = Math.PI - robot.getRobotDirection();
            }
            if (robot.getRobotPosition().x > width) {
                robotDirection = Math.PI - robot.getRobotDirection();
            }
            if (robot.getRobotPosition().y < 0) {
                robotDirection = -robot.getRobotDirection();
            }
            if (robot.getRobotPosition().y > height) {
                robotDirection = -robot.getRobotDirection();
            }
        }
        return robotDirection;
    }
}
