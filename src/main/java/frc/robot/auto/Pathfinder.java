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

    }


    public void translate(double xFeet, double yFeet, double rotation) {

        xFeetGoal = xFeet;
        yFeetGoal = yFeet;
        rotationGoal = rotation;
        hypDistance = Math.hypot(xFeetGoal, yFeetGoal);

        drive.getFrontLeft().resetSpeedDistance();
        drive.getFrontRight().resetSpeedDistance();
        drive.getBackLeft().resetSpeedDistance();
        drive.getBackRight().resetSpeedDistance();

        run = true;

        if (run) {

            /*while (run) {

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }*/
        }
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

            drive.move(xSpeed, ySpeed, 0, navX.getAngle(), false);

            System.out.println(distanceTraveled + ">" + hypDistance);
            
            if (distanceTraveled > hypDistance) {

                run = false;
                System.out.println("stoped!");
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
