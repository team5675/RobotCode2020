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

import edu.wpi.first.wpilibj.Spark;

import frc.robot.DriverController;
import frc.robot.Constants;

/**
 * Shooter timing:
 * 
 * AUTOMATIC SHOT
 * If vision finds target...
 * SetRPM(desired_rpm);
 * IF RPM is okay && button pressed...
 * Shoot();
 * 
 */
public class Shooter {

    static Shooter instance;

    Vision vision;
    DriverController controller;

    Spark gateMotor;
    CANSparkMax shootMotor1;
    CANSparkMax shootMotor2;
    CANPIDController RPMController;
    CANEncoder RPMEncoder;

    double angle;
    double distanceLimelight;

    double RPM_TARGET;

    public Shooter() {
        
        vision        = Vision.getInstance();
        controller    = DriverController.getInstance();


        shootMotor1   = new CANSparkMax(Constants.SHOOTER_ID_1, MotorType.kBrushless);
        shootMotor2   = new CANSparkMax(Constants.SHOOTER_ID_2, MotorType.kBrushless);

        gateMotor     = new Spark(Constants.GATE_ID);

        RPMController = shootMotor1.getPIDController();
        RPMEncoder    = shootMotor1.getEncoder();

        RPMController.setP(Constants.SHOOTER_KP);
        RPMController.setD(Constants.SHOOTER_KD);
        RPMController.setFF(Constants.SHOOTER_KF);
        RPMController.setOutputRange(-1, 1);

        distanceLimelight = (Constants.VISION_TARGET_HEIGHT - Constants.VISION_CAMERA_HEIGHT)
        / Math.tan(angle);
    }

    public void shoot(double RPM_TARGET) {

        setRPM(RPM_TARGET);

        if (controller.getShoot()) {

            if(getRPM() >= RPM_TARGET) {

                gateMotor.set(-1);
            }

            else {

                gateMotor.set(0);
            }
        }

        gateMotor.set(0);
    }


    public void setRPM(double RPM_TARGET){

        this.RPM_TARGET = RPM_TARGET;

        RPMController.setReference(RPM_TARGET, ControlType.kVelocity);
        shootMotor2.follow(shootMotor1, false);
    }


    public double getRPM() {

        return shootMotor1.getEncoder().getVelocity();
    }

    
    public static Shooter getInstance() {

        if (instance == null) {
            
            instance = new Shooter();
        }

        return instance;
    }
}