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
    CANPIDController RPMController1;
    CANPIDController RPMController2;
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

        RPMController1 = shootMotor1.getPIDController();
        RPMEncoder    = shootMotor1.getEncoder();

        RPMController1.setP(Constants.SHOOTER_KP);
        RPMController1.setD(Constants.SHOOTER_KD);
        RPMController1.setFF(Constants.SHOOTER_KF);
        RPMController1.setOutputRange(-1, 1);

        RPMController2 = shootMotor2.getPIDController();

        RPMController2.setP(Constants.SHOOTER_KP);
        RPMController2.setD(Constants.SHOOTER_KD);
        RPMController2.setFF(Constants.SHOOTER_KF);
        RPMController2.setOutputRange(-1, 1);

        distanceLimelight = vision.getDistanceFromTarget();
    }

    /**
     * Method that controls shooter and feeder timing
     * @param distanceLimelight limelight generated distance
     */
    public void shoot(double distanceLimelight) {

        this.distanceLimelight = distanceLimelight;

        setRPM(distanceLimelight);

        if (controller.getShoot()) {

            if(getRPM() >= RPM_TARGET) {

                gateMotor.set(-1);
            }

            else {

                gateMotor.set(0);
            }
        }

        else{
            
            gateMotor.set(0);
        }
    }

    /**
     * 
     * @param distance vision generated distance from limelight
     * @return the target rpm
     */
    public double setRPM(double distance){

        RPM_TARGET = distance * Constants.SHOOTER_RPM_GAIN;

        RPMController1.setReference(RPM_TARGET, ControlType.kVelocity);
        shootMotor2.follow(shootMotor1, false);

        return RPM_TARGET;
    }


    public double getRPM() {

        return shootMotor1.getEncoder().getVelocity();
    }


    public void test() {

        gateMotor.set(-1);
        shootMotor1.getPIDController().setReference(-3000, ControlType.kVelocity);
        shootMotor2.getPIDController().setReference(-3000, ControlType.kVelocity);
    }

    public void stop() {

        gateMotor.set(0);
        shootMotor1.getPIDController().setReference(0, ControlType.kVelocity);
        shootMotor2.getPIDController().setReference(0, ControlType.kVelocity);
    }
    
    public static Shooter getInstance() {

        if (instance == null) {
            
            instance = new Shooter();
        }

        return instance;
    }
}