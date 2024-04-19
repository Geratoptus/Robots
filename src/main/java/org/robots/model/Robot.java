package org.robots.model;

import java.util.Observable;

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

    public Position getRobotPosition(){ return robotPosition; }

    public void setRobotPosition(Position robotPosition){
        this.robotPosition = new Position(robotPosition);
    }

    public double getRobotDirection(){
        return robotDirection;
    }

    public void setRobotDirection(double direction) {this.robotDirection = direction; }

    public double getMaxVelocity() { return maxVelocity; }

    public double getMaxAngularVelocity() { return maxAngularVelocity; }

}
