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

public class Shooter {

    static Shooter instance;

    SparkMaxMotor motorOne;
    SparkMaxMotor motorTwo;
    Spark gate;

    Vision vision                     = Vision.getInstance();
    DriverController driverController = DriverController.getInstance();
    Drive drive                       = Drive.getInstance();

    double rpm = 0;

    NetworkTable logTable;
    NetworkTableEntry currentVelocity;
    NetworkTableEntry velocityGoal;
    
    
    public Shooter() {

        motorOne = new SparkMaxMotor(Constants.SHOOTER_ID_1);
        motorTwo = new SparkMaxMotor(Constants.SHOOTER_ID_2);
        gate = new Spark(Constants.SHOOTER_GATE_ID);

        motorOne.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);
        motorTwo.configurePID(Constants.SHOOTER_KP, 0, Constants.SHOOTER_KD, Constants.SHOOTER_KF);

        motorOne.burnFlash();
        motorTwo.burnFlash();

        logTable = NetworkTableInstance.getDefault().getTable("log");
        currentVelocity = logTable.getEntry("currentVelocity");
        velocityGoal = logTable.getEntry("velocityGoal");
    }
    

    public void shoot() {

        rpm = -0.4402 * Math.pow(vision.getDistanceFromTarget(), 3) + 28.024 * Math.pow(vision.getDistanceFromTarget(), 2) - 549.46 * vision.getDistanceFromTarget() + 5924.2 + 100; //1.162
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
        velocityGoal.setDouble(rpm);
    }


    public void stop() {

        motorOne.setRPMVelocity(0);
        motorTwo.setRPMVelocity(0);
        gate.set(0);

        rpm = 0;
    }


    public double getVelocity() {

        return (motorOne.getVelocity() + motorTwo.getVelocity()) / 2;
    }


    public double getRPMTarget() {

        return rpm;
    }
    

    public static Shooter getInstance() {

        if (instance == null) {
            
            instance = new Shooter();
        }

        return instance;
    }
}