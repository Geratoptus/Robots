package org.robots.model;

public class Position {
    public double x;
    public double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Position(Position robotPosition){
        this.x = robotPosition.x;
        this.y = robotPosition.y;
    }

}
