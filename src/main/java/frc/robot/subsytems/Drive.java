/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsytems;

import frc.libs.swerve.SwerveDrive;
import frc.libs.swerve.WheelDrive;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Drive {

    SwerveDrive chassis;
    WheelDrive backLeft;
    WheelDrive backRight;
    WheelDrive frontLeft;
    WheelDrive frontRight;

    void init() {
        backRight = new WheelDrive(Constants.DRIVE_BACK_RIGHT_AZIMUTH_ID, Constants.DRIVE_BACK_RIGHT_SPEED_ID, Constants.BR_AZIMUTH_ENCODER_ID, Constants.BR_P, Constants.BR_I, Constants.BR_D);

        chassis = new SwerveDrive();
    }
}