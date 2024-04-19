package org.robots.gui;

import org.robots.model.Robot;
import org.robots.model.Position;

import static org.robots.model.GameMaths.round;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class GameVisualizer extends JPanel implements Observer {
    private final Timer timer = initTimer();
    
    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private volatile Position robotPosition = new Position(100, 100);
    private volatile double robotDirection = 0;

    private volatile Point targetPosition = new Point(150, 100);

    private Robot robot;
    
    public GameVisualizer(Robot robot) {
        this.robot = robot;
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                robot.update(targetPosition, getWidth(), getHeight());
            }
        }, 0, 10);

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                setTargetPosition(e.getPoint());
                repaint();
            }
        });

        setDoubleBuffered(true);
        robot.addObserver(this);
    }

    protected void setTargetPosition(Point p) { targetPosition = new Point(p); }
    
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void update(Observable o, Object arg){
        if (o.equals(robot))
            if (arg.equals(Robot.robotMoved)){
                robotPosition = new Position(robot.getRobotPosition());
                robotDirection = robot.getRobotDirection();
            }
    }

    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 

        drawRobot(g2d, round(robotPosition.x), round(robotPosition.y), robotDirection);
        drawTarget(g2d, targetPosition.x, targetPosition.y);
    }
    
    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = round(x);
        int robotCenterY = round(y);

//        if (robotCenterX < 0){
//            robotCenterX = 0;
//        }
//        if (robotCenterX > getWidth()){
//            robotCenterX = getWidth();
//        }
//        if (robotCenterY < 0){
//            robotCenterY = 0;
//        }
//        if (robotCenterY > getHeight()){
//            robotCenterY = getHeight();
//        }

        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);

        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);

        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);

        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);

        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }
    
    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0); 

        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);

        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
