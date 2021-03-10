/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;

/*
Get Limelight data and send Limelight data/interact with it
*/

public class Vision {

    static Vision instance;

    static NetworkTable limelightTable;
    static NetworkTableEntry ledMode;
    static NetworkTableEntry horizontalOffset;
    static NetworkTableEntry verticalOffset;
    static NetworkTableEntry distanceFromTarget;
    static NetworkTableEntry verticalAngleOffset;
    static NetworkTable dashboardTable;

    double distance;
    boolean lightOn = false;


    public Vision() {

        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        dashboardTable = NetworkTableInstance.getDefault().getTable("dashboard");
        ledMode = limelightTable.getEntry("ledMode");
        horizontalOffset = limelightTable.getEntry("tx");
        verticalOffset = limelightTable.getEntry("ty");
        distanceFromTarget = dashboardTable.getEntry("distanceFromTarget");
    }

    public void lightOn() {

        lightOn = true;

        ledMode.setDouble(3);
    }


    public void lightOff() {

        lightOn = false;
        
        ledMode.setDouble(1);
    }


    public boolean getLightOn() {

        if (ledMode.getDouble(0) == 3) {

            return true;
        } else {

            return false;
        }
    }


    public double getHorizontalOffset() {

        return horizontalOffset.getDouble(0);
    }
    

    public double getVerticalOffset() {

        return verticalOffset.getDouble(0);
    }


    public void loop() {
        
        if (lightOn) {
            distance = (Constants.VISION_TARGET_HEIGHT - Constants.VISION_CAMERA_HEIGHT) / Math.tan(Math.toRadians(Constants.VISION_CAMERA_ANGLE + getVerticalOffset())) * 0.8;
            distanceFromTarget.setDouble(distance);
        }
    }

    public double getDistanceFromTarget() {

        return distance;
    }


    public static Vision getInstance() {

        if (instance == null) {
            
            instance = new Vision();
        }

        return instance;
    }
}