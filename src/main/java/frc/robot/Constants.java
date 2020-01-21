/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Constants {

    public enum AUTO_MODE {

        SHOOT_THEN_TRENCH,
        SHOOT,
        TRENCH
    }

    public static final CharSequence SELECTED_AUTO = "DEFAULT";

    //Drive subsystem constants
    public static final int DRIVE_FRONT_LEFT_SPEED_ID = 0;
    public static final int DRIVE_FRONT_LEFT_AZIMUTH_ID = 0;
    public static final double FL_P = 0;
    public static final double FL_I = 0;
    public static final double FL_D = 0;
    public static final int FL_AZIMUTH_ENCODER_ID = 0;

    public static final int DRIVE_FRONT_RIGHT_SPEED_ID = 0;
    public static final int DRIVE_FRONT_RIGHT_AZIMUTH_ID = 0;
    public static final double RL_P = 0;
    public static final double FR_I = 0;
    public static final double FR_D = 0;
    public static final int FR_AZIMUTH_ENCODER_ID = 0;

    public static final int DRIVE_BACK_LEFT_SPEED_ID = 0;
    public static final int DRIVE_BACK_LEFT_AZIMUTH_ID = 0;
    public static final double BL_P = 0;
    public static final double BL_I = 0;
    public static final double BL_D = 0;
    public static final int BL_AZIMUTH_ENCODER_ID = 0;

    public static final int DRIVE_BACK_RIGHT_SPEED_ID = 0;
    public static final int DRIVE_BACK_RIGHT_AZIMUTH_ID = 0;
    public static final double BR_P = 0;
    public static final double BR_I = 0;
    public static final double BR_D = 0;
    public static final int BR_AZIMUTH_ENCODER_ID = 0;

    //Shooter subsystem constants
    public static final int SHOOTER_ID = 0;
    public static final double SHOOTER_SETPOINT = 5000;
    public static final double SHOOTER_FLYWHEEL_KP = 0;
    public static final double SHOOTER_FLYWHEEL_KD = 0; 
    public static final double SHOOTER_FLYWHEEL_KF = 0; 
    public static final double PORT_HEIGHT = 8.1875; //ft
    public static final double SHOOTER_HEIGHT = 2;    //ft
    public static final double SHOOTER_WHEEL_DIAMTER = 1/3; //ft
    //public static final double CAM_HEIGHT;
    //public static final double CAM_ANGLE;

}