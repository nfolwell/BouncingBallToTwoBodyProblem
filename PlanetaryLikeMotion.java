/**
 * Calculates positions, velocities and accerations of two bodies using
 * equations kinda like planetary motion formulas.
 * Runs the animation of the simulation
 * Modified from this nice example: https://www.javacodex.com/Swing/Bouncing-Ball
 */

import java.awt.*;
import javax.swing.*;

public class PlanetaryLikeMotion extends JPanel {

    Planet p1 = new Planet(150, 100, 1, -1);
    Planet p2 = new Planet(150, 150, -1, 1);
    Planet baryCenter = new Planet(150, 125, 0, 0);
    // Box height and width
    int width;
    int height;

    public PlanetaryLikeMotion() {
        p1.setMass(1);
        p2.setMass(1);
        Thread thread = new Thread() {
            public void run() {
                while (true) {

                    width = getWidth();
                    height = getHeight();
                    //To move planet 1: change location by velocity
                    //(set location to p1 location + p1 velocity)
                    p1.setXLoc(p1.getXLoc() + p1.getXVel());
                    p1.setYLoc(p1.getYLoc() + p1.getYVel());

                    //To move planet 1: change location by velocity
                    //(set location to p1 location + p1 velocity)
                    p2.setXLoc(p2.getXLoc() + p2.getXVel());
                    p2.setYLoc(p2.getYLoc() + p2.getYVel());

                    //There is a gravitational constant that we can tinker with
                    //to adjust the responsiveness of the simulation
                    double constant = 40.0;

                    //The acceleration (the change in the velocity) for planet 1
                    // is (mass of p1/(mass of p1 + mass of p2)) * (location of p2 - location of p2) / square of the disance between p1 and p2
                    p1.setXAcc(p2.getMass() / (p1.getMass() + p2.getMass()) * constant * (p2.getXLoc() - p1.getXLoc()) / (p1.distance(p2) * p1.distance(p2)));
                    p1.setYAcc(p2.getMass() / (p1.getMass() + p2.getMass()) * constant * (p2.getYLoc() - p1.getYLoc()) / (p1.distance(p2) * p1.distance(p2)));

                    //The acceleration (the change in the velocity) for planet 2
                    // is (mass of p2/(mass of p1 + mass of p2)) * (location of p2 - location of p2) / square of the disance between p1 and p2
                    p2.setXAcc(p1.getMass() / (p1.getMass() + p2.getMass()) * constant * (p1.getXLoc() - p2.getXLoc()) / (p1.distance(p2) * p1.distance(p2)));
                    p2.setYAcc(p1.getMass() / (p1.getMass() + p2.getMass()) * constant * (p1.getYLoc() - p2.getYLoc()) / (p1.distance(p2) * p1.distance(p2)));

                    //Change the velocity by the acceleration for planet 1
                    p1.setXVel(p1.getXVel() + p1.getXAcc());
                    p1.setYVel(p1.getYVel() + p1.getYAcc());

                    //Change the velocity by the acceleration for planet 2
                    p2.setXVel(p2.getXVel() + p2.getXAcc());
                    p2.setYVel(p2.getYVel() + p2.getYAcc());
                    repaint();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }

                }
            }
        };
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillOval((int) (p1.getXLoc() - p1.radius), (int) (p1.getYLoc() - p1.radius), (int) (2 * p1.radius), (int) (2 * p1.radius));
        g.setColor(Color.RED);
        g.fillOval((int) (p2.getXLoc() - p2.radius), (int) (p2.getYLoc() - p2.radius), (int) (2 * p2.radius), (int) (2 * p2.radius));

    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Planet Thing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setContentPane(new PlanetaryLikeMotion());
        frame.setVisible(true);
    }
}

