/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;

/**
 * Basic pathfinder made for our swerve
 */
public class Pathfinder {

    static Pathfinder instance;
    
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
    static double speedMultiplier;


    public Pathfinder() {
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
        double distanceBackLeft = drive.getBackLeft().getSpeedPosition();
        double distanceBackRight = drive.getBackRight().getSpeedPosition();
        totalDistance = (distanceFrontLeft + distanceFrontRight + distanceBackLeft + distanceBackRight) / 4;

        run = true;

        System.out.println("Running path");
        while (run) {

            try {
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
            double distanceBackLeft = drive.getBackLeft().getSpeedPosition();
            double distanceBackRight = drive.getBackRight().getSpeedPosition();
            double averageDistance = (distanceFrontLeft + distanceFrontRight + distanceBackLeft + distanceBackRight) / 4;
            double distanceTraveled = (averageDistance - totalDistance) / 6 * 9.42 / 12; 
            
            double rotationOffset = (7 * (rotationGoal % 360) / 360) / 4;

            distanceTraveled -= rotationOffset;

            xSpeed = xFeetGoal * speedMultiplier / hypDistance;
            ySpeed = yFeetGoal * speedMultiplier / hypDistance;
            //18.512 * rotationGoal / 360

            System.out.println(distanceTraveled + " > " + hypDistance);
            if (distanceTraveled > hypDistance) {
                drive.move(0, 0, 0, navX.getAngle(), false);
                run = false;
            } else {
                drive.move(xSpeed, ySpeed, (rotationGoal - navX.getAngle()) * 0.007, navX.getAngle() + 90, false);
            }
        }        
    }


    public static Pathfinder getInstance() {

        if (instance == null) {

            instance = new Pathfinder();
        }

        return instance;
    }
}
