/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Shooter {

    static Shooter instance;

    Vision vision;

    CANSparkMax shootMotor;
    CANPIDController RPMController;
    CANEncoder RPMEncoder;

    double speed;
    double angle;
    double distance;
    double height;
    double theta;
    double distanceLimelight;

    PIDController speedPID;

    public Shooter() {
        
        vision = new Vision();
        shootMotor = new CANSparkMax(Constants.SHOOTER_ID, MotorType.kBrushless);


        RPMController = shootMotor.getPIDController();
        RPMEncoder    = shootMotor.getEncoder();

        RPMController.setP(Constants.SHOOTER_FLYWHEEL_KP);
        RPMController.setD(Constants.SHOOTER_FLYWHEEL_KD);
        RPMController.setFF(Constants.SHOOTER_FLYWHEEL_KF);
        RPMController.setOutputRange(-1, 1);
    }

    public void autoAimAtTarget() { //Distance, angle, and velocity
        angle = vision.getVerticalOffset() + Constants.VISION_CAMERA_ANGLE;
        distanceLimelight = (Constants.VISION_TARGET_HEIGHT - Constants.VISION_CAMERA_HEIGHT)
                            / Math.tan(angle);

        double angleSetpoint = getAngle(distanceLimelight);

        double speedSetpoint = getRPM(angleSetpoint);

        setRPM(speedSetpoint);
    }


    public void shoot() {


    }
    
    /**
     * Returns the hood angle based on the distance from port
     * @return angle in radians 
     * 
     * @param distance The distance from the port. (Use limelight generated distance)
     */
    public double getAngle(double distance) {

        //angle calculated by modeling a triangle approx to the parabola
        theta = Math.atan(2 * (Constants.VISION_TARGET_HEIGHT - Constants.SHOOTER_HEIGHT) / distance);

        return theta;
    }

    public double getRPM(double theta) { //line 1 is ball velocity
        return (Math.sqrt((64 * Constants.VISION_TARGET_HEIGHT) / Math.pow(Math.sin(theta), 2)) 
        / (Math.PI * 0.5 * Constants.SHOOTER_WHEEL_DIAMETER)) * 60; //* 60 is for sec to min
    }

    public void setRPM(double RPM){

        RPMController.setReference(RPM, ControlType.kVelocity);
    }

    public static Shooter getInstance() {

        if (instance == null) {
            
            instance = new Shooter();
        }

        return instance;
    }
}