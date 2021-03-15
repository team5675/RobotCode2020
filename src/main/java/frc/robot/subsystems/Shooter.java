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
import edu.wpi.first.wpilibj.Encoder;

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

    double hoodAngle;
    double hoodVelocity;
    double desiredAngle;

    double hoodP;
    double hoodD;

    Encoder hoodEncoder;
    DigitalInput hoodLowLimit;
    DigitalInput hoodHighLimit;
    
    
    public Shooter() {
        vision = Vision.getInstance();

        hoodLowLimit = new DigitalInput(0);
        hoodHighLimit = new DigitalInput(1);

        hoodEncoder = new Encoder(1, 2);
        //hoodEncoder.setDistancePerPulse(9/29.6); //in degrees

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
        System.out.println("Encoder: " + hoodEncoder.getDistance());
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

            hoodAngle = hoodEncoder.getDistance();
            System.out.println(hoodAngle);
            boolean highTarget = false;

            if(vision.getDistanceFromTarget() < 7.5) highTarget = true;
            else highTarget = false;
            
            if(!highTarget) {
                if(!hoodHighLimit.get()) hoodMotor.set(0); //is triggered
                else hoodMotor.set(-0.6);
            }
            else {
                if(!hoodLowLimit.get()) hoodMotor.set(0);
                else hoodMotor.set(0.6);
            }

            flywheelOne.setRPMVelocity(Constants.SHOOTER_FLYWHEEL_RPM * -1);
            flywheelTwo.setRPMVelocity(Constants.SHOOTER_FLYWHEEL_RPM * -1);

            gate.set(1);

            //if(Math.abs(hoodAngleTarget - hoodAngle) < 5) gate.set(1);
            //else gate.set(0);

            shooterState = ShooterState.Idle;
        }
        else
        {

            flywheelOne.setRPMVelocity(0);
            flywheelTwo.setRPMVelocity(0);
            hoodMotor.set(0);
        }
    }

    public void shoot() {

        if (shooterState != ShooterState.StartUp)
        {

            shooterState = ShooterState.Shooting;
        }

        //desiredAngle = limelightx^3 * a etc regression in desmos

        //hoodVelocity = ((currentHoodAngle - desiredAngle) * hoodP) + ((currentHoodAngle - pastAngle) * hoodD)
        //motorOne.setSpeed(0.9);
        //motorTwo.setSpeed(0.9);
        /*hoodVelocity = driverController.getHood(); //comment this out

        if(hoodLimit.get()) {
            hoodMotor.set(0);
            System.out.println("Limit Switch Hit!");
        } else {
            hoodMotor.set(hoodVelocity*0.5);
        }*/

        //Do limit switch stuff here Logan
        
        //if(Math.abs(currentAngle - desiredAngle ) < 1 (or whatever threshold value))) {

        //    gate.set(-1, 0);
        //} else {

        //    System.out.println("ANGLE: " + currentHoodAngle + "GOAL: " + desiredAngle);
        //    gate.set(0, 0);
        //}

        //pastHoodAngle = currentHoodAngle;
        //currentHoodAngle = hoodEncoder.getDistance();
        //System.out.println(currentHoodAngle);

        /*rpm = -0.4402 * Math.pow(vision.getDistanceFromTarget(), 3) + 28.024 * Math.pow(vision.getDistanceFromTarget(), 2) - 549.46 * vision.getDistanceFromTarget() + 5924.2 + 100; //1.162
        //rpm = 2820;

        motorOne.setRPMVelocity((int)rpm * -1);
        motorTwo.setRPMVelocity((int)rpm * -1);

        if ((motorOne.getVelocity() + motorTwo.getVelocity()) / 2 > rpm && rpm != 0) {

            gate.set(-1);
        } else {

            System.out.println("CURRENT VELOCITY: " + getVelocity() + " GOALS: " + rpm);
            gate.set(0);
        }

        currentVelocity.setDouble(getVelocity());
        velocityGoal.setDouble(rpm);*/
    }

    public void stop() {

        flywheelOne.setRPMVelocity(0);
        flywheelTwo.setRPMVelocity(0);
        gate.set(0);
    }


    public double getVelocity() {
        return (flywheelOne.getVelocity() + flywheelTwo.getVelocity()) / 2;
    }

    public static Shooter getInstance() {

        if (instance == null) {
            
            instance = new Shooter();
        }

        return instance;
    }
}