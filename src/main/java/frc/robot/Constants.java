/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Constants {
    
    public static final double PATHFINDER_KP = 0;
	public static final double PATHFINDER_KV = 0.0909090909;
	public static final double PATHFINDER_KA = 0.011;
   
    //Drive subsystem constants
    
	public static final int WHEEL_BASE_WIDTH = 25;
    public static final int WHEEL_BASE_DEPTH = 25;
    
    public static final int DRIVE_FRONT_LEFT_SPEED_ID = 5;
    public static final int DRIVE_FRONT_LEFT_AZIMUTH_ID = 6;
    public static final double FL_P = 0.86715;
    public static final double FL_I = 0.0003;
    public static final double FL_D = 0.01;
    public static final int FL_AZIMUTH_ENCODER_ID = 3;
	public static double FL_ANGLE_OFFSET = 4.648;

    public static final int DRIVE_FRONT_RIGHT_SPEED_ID = 1;
    public static final int DRIVE_FRONT_RIGHT_AZIMUTH_ID = 2;
    public static final double FR_P = 0.79715;
    public static final double FR_I = 0.0005;
    public static final double FR_D = 0.01;
    public static final int FR_AZIMUTH_ENCODER_ID = 2;
	public static double FR_ANGLE_OFFSET = 0.153;

    public static final int DRIVE_BACK_LEFT_SPEED_ID = 10;
    public static final int DRIVE_BACK_LEFT_AZIMUTH_ID = 11;
    public static final double BL_P = 0.80715;
    public static final double BL_I = 0.0006;
    public static final double BL_D = 0.01;
    public static final int BL_AZIMUTH_ENCODER_ID = 1;  
	public static double BL_ANGLE_OFFSET = 2.494;

    public static final int DRIVE_BACK_RIGHT_SPEED_ID = 7;
    public static final int DRIVE_BACK_RIGHT_AZIMUTH_ID = 8;
    public static final double BR_P = 0.79715;
    public static final double BR_I = 0.0004;
    public static final double BR_D = 0.01;
    public static final int BR_AZIMUTH_ENCODER_ID = 0;
    public static double BR_ANGLE_OFFSET = 0.134;

    //Shooter subsystem constants
    public static final int SHOOTER_ID = 17;
    public static final double SHOOTER_SETPOINT = 5000;
    public static final double SHOOTER_FLYWHEEL_KP = 0;
    public static final double SHOOTER_FLYWHEEL_KD = 0; 
    public static final double SHOOTER_FLYWHEEL_KF = 0; 
    public static final double SHOOTER_HEIGHT = 2;    //ft
    public static final double SHOOTER_WHEEL_DIAMETER = 0.5025; //ft
    public static final double CAM_HEIGHT = 0;
    public static final double CAM_ANGLE = 0;

    //Pizza spinner constants
    public static final int SPINNER_MOTOR_ID = 12;
    public static final int SPINNER_TICKS_PER_REV = 0;
    public static final double SPINNER_REVS_SETPOINT = 2000;
    public static final double ONE_COLOR_REVS = 50;
    public static final int SPINNER_ARM_IN_CHANNEL = 0;
    public static final int SPINNER_ARM_OUT_CHANNEL = 1;

    //Intake subsystem constants
    public static final int INTAKE_ID = 3;

    //Vision subsystem constants
    public static final double VISION_TARGET_HEIGHT = 8.1875;
    public static final double VISION_CAMERA_HEIGHT = 1.7493;
    public static final double VISION_CAMERA_ANGLE = 25.3778;

    //Auto Constants
    public static final double AUTO_FORWARD_P = 0.1;
    public static final double AUTO_ROTATE_P = 0.02;
    public static final double AUTO_STRAFE_P = 0.05;
    public static final double AUTO_GYRO_P = -0.03;
}