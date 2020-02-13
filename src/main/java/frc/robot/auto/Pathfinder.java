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


    public Pathfinder() {
        drive.getFrontLeft().resetSpeedDistance();
        drive.getFrontRight().resetSpeedDistance();
        drive.getBackLeft().resetSpeedDistance();
        drive.getBackRight().resetSpeedDistance();
    }


    public void translate(double xFeet, double yFeet, double angle) {

        xFeetGoal = xFeet;
        yFeetGoal = yFeet;
        rotationGoal = angle;
        hypDistance = Math.hypot(xFeetGoal, yFeetGoal);

        drive.getFrontLeft().resetSpeedDistance();
        drive.getFrontRight().resetSpeedDistance();
        drive.getBackLeft().resetSpeedDistance();
        drive.getBackRight().resetSpeedDistance();

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
            double distanceTraveled = averageDistance / 6 * 9.42 / 12;

            double xSpeed = xFeetGoal / hypDistance;
            double ySpeed = yFeetGoal / hypDistance;

            double rotationSpeed = (rotationGoal - (navX.getAngle() % 360)) / 360;

            drive.move(xSpeed, ySpeed, 0 - navX.getAngle() * 0.03, navX.getAngle(), false);

            //System.out.println(distanceTraveled + ">" + hypDistance);
            //System.out.println("xSpeed: " + xSpeed);
            //System.out.println("ySpeed: " + ySpeed);
            
            if (distanceTraveled > hypDistance) {

                run = false;
                System.out.println("stoped!");
                drive.move(0, 0, 0, navX.getAngle(), false);
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
