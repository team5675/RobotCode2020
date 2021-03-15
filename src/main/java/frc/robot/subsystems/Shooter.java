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
import edu.wpi.first.wpilibj.Spark;
import frc.libs.motors.SparkMaxMotor;
import frc.robot.Constants;
import frc.robot.DriverController;
import edu.wpi.first.wpilibj.DigitalInput;

public class Shooter {

    static Shooter instance;

    enum ShooterState
    {
        StartUp,
        Shooting,
        Idle
    }

    static ShooterState shooterState;
    SparkMaxMotor flywheelOne;
    SparkMaxMotor flywheelTwo;
    Spark hoodMotor;
    Spark gate;

    Vision vision;
    DriverController driverController = DriverController.getInstance();
    Drive drive = Drive.getInstance();

    NetworkTable logTable;
    NetworkTableEntry currentVelocity;
    NetworkTableEntry velocityGoal;

    double hoodVelocity;

    double hoodP;
    double hoodD;
    boolean highTarget;

    DigitalInput hoodLowLimit;
    DigitalInput hoodHighLimit;
    
    
    public Shooter() {
        vision = Vision.getInstance();

        hoodLowLimit = new DigitalInput(0);
        hoodHighLimit = new DigitalInput(1);

        flywheelOne = new SparkMaxMotor(Constants.SHOOTER_ID_1);
        flywheelTwo = new SparkMaxMotor(Constants.SHOOTER_ID_2);
        hoodMotor = new Spark(Constants.HOOD_ID);
        gate = new Spark(Constants.SHOOTER_GATE_ID);

        flywheelOne.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);
        flywheelTwo.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);

        logTable = NetworkTableInstance.getDefault().getTable("log");
        currentVelocity = logTable.getEntry("currentVelocity");
        velocityGoal = logTable.getEntry("velocityGoal");
    }
/*
    public void newRun(){

        //if we need close position
        if(vision.getDistanceFromTarget() < 7.5) {

            if(!hoodLowLimit.get()) {

                hoodMotor.set(-1);
            }

            else {

                hoodMotor.set(0);
            }
        }

        //otherwise far
        else {

            if(!hoodHighLimit.get()) {

                hoodMotor.set(1);
            }

            else {

                hoodMotor.set(0);
            }
        }
    }*/
     
    public void run()
    {
        //System.out.println("Encoder: " + hoodEncoder.getDistance());
        //System.out.println("Distance" + vision.getDistanceFromTarget());

        if (shooterState == ShooterState.StartUp)
        {

            hoodMotor.set(0.40);

            if (hoodLowLimit.get() == false)
            {

                shooterState = ShooterState.Idle;
                //hoodEncoder.reset();
                hoodMotor.set(0);
            }
        }
        else if (shooterState == ShooterState.Shooting)
        {
            alignHood();

            if(!highTarget && !hoodLowLimit.get()) gate.set(1);
            else if(highTarget && !hoodHighLimit.get()) gate.set(1);
            else gate.set(0);

            flywheelOne.setRPMVelocity(Constants.SHOOTER_FLYWHEEL_RPM * -1);
            flywheelTwo.setRPMVelocity(Constants.SHOOTER_FLYWHEEL_RPM * -1);

            //if(Math.abs(hoodAngleTarget - hoodAngle) < 5) gate.set(1);
            //else gate.set(0);

            shooterState = ShooterState.Idle;
        }
        else
        {

            flywheelOne.setRPMVelocity(0);
            flywheelTwo.setRPMVelocity(0);
            alignHood();
        }
    }

    public void alignHood() {
        if(vision.getDistanceFromTarget() < 7.5) highTarget = true;
        else highTarget = false;
            
        if(!highTarget) {
            if(!hoodHighLimit.get()) hoodMotor.set(0);
            else hoodMotor.set(-0.6);
        }
        else {
            if(!hoodLowLimit.get()) hoodMotor.set(0);
            else hoodMotor.set(0.6);
        }
    }

    public void shoot() {

        if (shooterState != ShooterState.StartUp)
        {

            shooterState = ShooterState.Shooting;
        }
    }

    public void stop() {
        shooterState = ShooterState.Idle;
    }


    public double getVelocity() {
        return (flywheelOne.getVelocity() + flywheelTwo.getVelocity()) / 2;
    }

    public String getState() {
        if(shooterState == ShooterState.StartUp) return "StartUp";
        else if(shooterState == ShooterState.Shooting) return "Shooting";
        else return "Idle";
    }

    public static Shooter getInstance() {

        if (instance == null) {
            
            instance = new Shooter();
        }

        return instance;
    }
}