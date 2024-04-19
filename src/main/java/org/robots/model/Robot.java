package org.robots.model;

import java.util.Observable;
import static org.robots.model.GameMaths.*;
import java.awt.Point;

public class Robot extends Observable{
    private volatile Position robotPosition;
    private volatile double robotDirection = 0;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.003;
    public static final String robotMoved = "robot moved";

    public Robot(double x, double y){
        super();
        robotPosition = new Position(x, y);
    }

    public Position getRobotPosition(){
        return robotPosition;
    }

    public void setRobotPosition(Position robotPosition){
        this.robotPosition = new Position(robotPosition);
    }

    public double getRobotDirection(){
        return robotDirection;
    }

    public double getMaxVelocity() { return maxVelocity; }

    public double getMaxAngularVelocity() { return maxAngularVelocity; }

    public void update(Point targetPosition, int width, int height){
        double distance = distance(targetPosition.x, targetPosition.y, robotPosition.x, robotPosition.y);
        if (distance < 0.5) {
            return;
        }

        double angularVelocity = countAngularVelocity(targetPosition, this);
        checkBounds(width, height);
        moveRobot(maxVelocity, angularVelocity, 10);

        setChanged();
        notifyObservers(robotMoved);
        clearChanged();
    }

    private void checkBounds(int width, int height) {
        if (robotPosition.x < 0) {
            robotDirection = Math.PI - robotDirection;
        }
        if (robotPosition.x > width) {
            robotDirection = Math.PI - robotDirection;
        }
        if (robotPosition.y < 0) {
            robotDirection = -robotDirection;
        }
        if (robotPosition.y > height) {
            robotDirection = -robotDirection;
        }
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX = robotPosition.x + velocity / angularVelocity *
                (Math.sin(robotDirection  + angularVelocity * duration) -
                        Math.sin(robotDirection));

        if (!Double.isFinite(newX)) {
            newX = robotPosition.x + velocity * duration * Math.cos(robotDirection);
        }

        double newY = robotPosition.y - velocity / angularVelocity *
                (Math.cos(robotDirection  + angularVelocity * duration) -
                        Math.cos(robotDirection));

        if (!Double.isFinite(newY)) {
            newY = robotPosition.y + velocity * duration * Math.sin(robotDirection);
        }

        robotPosition.x = newX;
        robotPosition.y = newY;
        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
    }
}
