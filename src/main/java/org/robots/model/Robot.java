package org.robots.model;

import java.util.Observable;
import static org.robots.model.GameMaths.*;
import java.awt.Point;

public class Robot extends Observable{
    private volatile double robotPositionX;
    private volatile double robotPositionY;
    private volatile double robotDirection = 0;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.003;
    public static final String robotMoved = "robot moved";

    public Robot(int x, int y){
        super();
        robotPositionX = x;
        robotPositionY = y;
    }

    public double getRobotPositionX(){
        return robotPositionX;
    }

    public double getRobotPositionY(){
        return robotPositionY;
    }

    public double getRobotDirection(){
        return robotDirection;
    }

    public double getMaxVelocity() { return maxVelocity; }

    public double getMaxAngularVelocity() { return maxAngularVelocity; }

    public void update(Point targetPosition){
        double distance = distance(targetPosition.x, targetPosition.y, robotPositionX, robotPositionY);
        if (distance < 0.5) {
            return;
        }

        double angularVelocity = countAngularVelocity(targetPosition, this);

        moveRobot(maxVelocity, angularVelocity, 10);
        setChanged();
        notifyObservers(robotMoved);
        clearChanged();
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX = robotPositionX + velocity / angularVelocity *
                (Math.sin(robotDirection  + angularVelocity * duration) -
                        Math.sin(robotDirection));

        if (!Double.isFinite(newX)) {
            newX = robotPositionX + velocity * duration * Math.cos(robotDirection);
        }

        double newY = robotPositionY - velocity / angularVelocity *
                (Math.cos(robotDirection  + angularVelocity * duration) -
                        Math.cos(robotDirection));

        if (!Double.isFinite(newY)) {
            newY = robotPositionY + velocity * duration * Math.sin(robotDirection);
        }

        robotPositionX = newX;
        robotPositionY = newY;
        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
    }

}
