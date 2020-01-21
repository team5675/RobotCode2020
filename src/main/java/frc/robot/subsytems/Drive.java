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
import frc.robot.DriverController;
import frc.robot.NavX;

/**
 * Add your docs here.
 */
public class Drive {

    SwerveDrive swerve;

    WheelDrive backRight;
    WheelDrive backLeft;
    WheelDrive frontRight;
    WheelDrive frontLeft;

    DriverController controller;

    NavX navX;


    public Drive() {

        backRight = new WheelDrive(Constants.DRIVE_BACK_RIGHT_AZIMUTH_ID, Constants.DRIVE_BACK_RIGHT_SPEED_ID, Constants.BR_AZIMUTH_ENCODER_ID, 
        Constants.BR_P, Constants.BR_I, Constants.BR_D);

        backLeft = new WheelDrive(Constants.DRIVE_BACK_LEFT_AZIMUTH_ID, Constants.DRIVE_BACK_LEFT_SPEED_ID, Constants.BL_AZIMUTH_ENCODER_ID, 
        Constants.BL_P, Constants.BL_I, Constants.BL_D);

        frontRight = new WheelDrive(Constants.DRIVE_FRONT_RIGHT_AZIMUTH_ID, Constants.DRIVE_FRONT_RIGHT_SPEED_ID, Constants.FR_AZIMUTH_ENCODER_ID, 
        Constants.FR_P, Constants.FR_I, Constants.FR_D);

        frontLeft = new WheelDrive(Constants.DRIVE_FRONT_LEFT_AZIMUTH_ID, Constants.DRIVE_FRONT_LEFT_SPEED_ID, Constants.FL_AZIMUTH_ENCODER_ID, 
        Constants.FL_P, Constants.FL_I, Constants.FL_D);

        swerve = new SwerveDrive(backRight, backLeft, frontRight, frontLeft);

        controller = new DriverController();

        navX = new NavX();
    }

    /**
     * drive the *Configured* swerve in teleop
     */
    public void teleopDrive() {

        swerve.drive(controller.getForward(), controller.getStrafe(), controller.getRotation(), navX.getAngle(), controller.isFieldOriented());
    }

    public WheelDrive backRight() {

        return backRight;
    }
    
    public WheelDrive backLeft() {

        return backLeft;
    }

    public WheelDrive frontRight() {

        return frontRight;
    }

    public WheelDrive frontLeft() {

        return frontLeft;
    }
}
