/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsytems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;

/*
Get Limelight data and send Limelight data/interact with it
*/

public class Vision {
    NetworkTable limelightTable;
    NetworkTableEntry ledMode;
    NetworkTableEntry horizontalOffset;
    NetworkTableEntry verticalOffset;
    NetworkTableEntry distanceFromTarget;
    NetworkTable dashboardTable;

    public void init() {
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        dashboardTable = NetworkTableInstance.getDefault().getTable("limelight");
        ledMode = limelightTable.getEntry("ledMode");
        horizontalOffset = limelightTable.getEntry("tx");
        verticalOffset = limelightTable.getEntry("ty");
        distanceFromTarget = dashboardTable.getEntry("distanceFromTarget");
    }

    void lightOn() {
        ledMode.setDouble(3);
    }

    void lightOff() {
        ledMode.setDouble(1);
    }

    double getHorizontalOffset() {
        return horizontalOffset.getDouble(0);
    }
    
    double getVerticalOffset() {
        return verticalOffset.getDouble(0);
    }

    public void loop() {
        double distance = (Constants.VISION_TARGET_HEIGHT - Constants.VISION_CAMERA_HEIGHT) / Math.tan(Math.toRadians(Constants.VISION_CAMERA_ANGLE + getVerticalOffset()));
        distanceFromTarget.setDouble(distance);
    }
}