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

/*
Get Limelight data and send Limelight data/interact with it
*/

public class Vision {
    NetworkTable limelightTable;
    NetworkTableEntry horizontalOffset;
    NetworkTableEntry verticalOffset;

    public void init() {
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    }

    double getHorizontalOffset() {
        return horizontalOffset.getDouble(0);
    }

    double getVerticalOffset() {
        return verticalOffset.getDouble(0);
    }
}