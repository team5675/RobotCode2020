/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;

/**
 * Basic pathfinder made for our swerve
 */
public class Pathfinderold {

    static Pathfinderold instance;
    
    PathWeezer pathWeezer = PathWeezer.getInstance();
    static Drive drive = Drive.getInstance();
    static NavX navX = NavX.getInstance();

    static double xFeetGoal;
    static double yFeetGoal;
    static double rotationGoal;
    static double hypDistance;
    static boolean run = false;
    static double totalDistance;
    static double xSpeed;
    static double ySpeed;
    static double zSpeed;
    static double speedMultiplier;
    static double distanceTraveled;


    public Pathfinderold() {
        drive.getFrontLeft().resetSpeedDistance();
        drive.getFrontRight().resetSpeedDistance();
        drive.getBackLeft().resetSpeedDistance();
        drive.getBackRight().resetSpeedDistance();
    }


    public void translate(double xFeet, double yFeet, double angle, double newSpeedMultiplier) {

        xFeetGoal = xFeet;
        yFeetGoal = yFeet;
        rotationGoal = angle;
        hypDistance = Math.hypot(xFeetGoal, yFeetGoal);
        speedMultiplier = newSpeedMultiplier;

        double distanceFrontLeft = drive.getFrontLeft().getSpeedPosition();
        double distanceFrontRight = drive.getFrontRight().getSpeedPosition();
        double distanceBackLeft = drive.getBackLeft().getSpeedPosition() * -1;
        double distanceBackRight = drive.getBackRight().getSpeedPosition();
        totalDistance = (distanceFrontLeft + distanceFrontRight + distanceBackLeft + distanceBackRight) / 4;

        run = true;

        System.out.println("Running path");
        while (run) {

            try {
                loop();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Stopping path");
    }

    
    public void loop() {


        if (run) {

            double distanceFrontLeft = drive.getFrontLeft().getSpeedPosition();
            double distanceFrontRight = drive.getFrontRight().getSpeedPosition();
            double distanceBackLeft = drive.getBackLeft().getSpeedPosition() * -1;
            double distanceBackRight = drive.getBackRight().getSpeedPosition();
            double averageDistance = (distanceFrontLeft + distanceFrontRight + distanceBackLeft + distanceBackRight) / 4;
            distanceTraveled = (averageDistance - totalDistance) / 6 * 9.42 / 12; 
            
            double rotationOffset = (8.9 * (rotationGoal % 360) / 360) / 4;

            distanceTraveled -= rotationOffset;

            double slowDown = (hypDistance - distanceTraveled) * Constants.PATHFINDER_SLOWDOWN_P;

            xSpeed = xFeetGoal / hypDistance;
            ySpeed = yFeetGoal / hypDistance;
            zSpeed = (rotationGoal - navX.getAngle()) * 0.007;

            if (slowDown > 1) {
                
            } else if (slowDown < 0.3 / speedMultiplier) {

                xSpeed = 0;
                ySpeed = 0;
                
                if (Math.abs(zSpeed) < 0.1) {

                    zSpeed = 0;
                    run = false;
                }
            } else {
                xSpeed = xSpeed * slowDown;
                ySpeed = ySpeed * slowDown;
            }

            drive.move(xSpeed * speedMultiplier, ySpeed * speedMultiplier, zSpeed, navX.getAngle() + 90, false);
        }
    }

    public void runPath() {
        double[][] points = pathWeezer.getTrajectory();

        int currentPoint = 1; 
        double pastX = points[0][3];
        double pastY = points[0][4];

        while(currentPoint < points.length) {
            if(!run) {
                currentPoint++;
                translate(points[currentPoint][3] - pastX, points[currentPoint][4] - pastY, points[currentPoint][5], 0.5);
            }
        }
    }

    public double getFtMoved() {
        return distanceTraveled;
    }
    
    public boolean getRun() {
        return run;
    }

    public static Pathfinderold getInstance() {

        if (instance == null) {

            instance = new Pathfinderold();
        }

        return instance;
    }
}
