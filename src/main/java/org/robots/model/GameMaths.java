package org.robots.model;
import java.awt.Point;

public class GameMaths {

    public static double countAngularVelocity(Point targetPosition, Robot robot){
        double angleToTarget = angleTo(robot.getRobotPositionX(), robot.getRobotPositionY(),
                targetPosition.x, targetPosition.y);
        double angularVelocity = 0;

        double diff = asNormalizedRadians(angleToTarget - robot.getRobotDirection());

        if (diff < Math.PI)
            angularVelocity = robot.getMaxAngularVelocity();

        if (diff > Math.PI)
            angularVelocity = -robot.getMaxAngularVelocity();

        if (unreachable(targetPosition, robot))
            angularVelocity = 0;

        return angularVelocity;
    }

    private static boolean unreachable(Point targetPosition, Robot robot) {
        double dx = targetPosition.x - robot.getRobotPositionX();
        double dy = targetPosition.y - robot.getRobotPositionY();


        double newDX = Math.cos(robot.getRobotDirection()) * dx + Math.sin(robot.getRobotDirection()) * dy;
        double newDY = Math.cos(robot.getRobotDirection()) * dy - Math.sin(robot.getRobotDirection()) * dx;

        double maxCurve = robot.getMaxVelocity() / robot.getMaxAngularVelocity();
        double dist1 = distance(newDX, newDY, 0, maxCurve);
        double dist2 = distance(newDX, newDY + maxCurve, 0, 0);

        return !(dist1 > maxCurve) || !(dist2 > maxCurve);
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double distance(Point p1, Point p2) {
        double diffX = p1.x - p2.x;
        double diffY = p1.y - p2.y;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double angleTo(Point fromPos, Point toPos) {
        double diffX = toPos.x - fromPos.x;
        double diffY = toPos.y - fromPos.y;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2*Math.PI;
        }

        while (angle >= 2*Math.PI) {
            angle -= 2*Math.PI;
        }

        return angle;
    }
    public static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;

        if (value > max)
            return max;

        return value;
    }
    public static int round(double value)
    {
        return (int)(value + 0.5);
    }
}
