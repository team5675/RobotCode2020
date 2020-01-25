/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.DriverController;

/**
 * Sucks
 */
public class Sucker {

    static Sucker instance;
    
    DriverController control;
    CANSparkMax roller;
    double vIntake;

    public Sucker() {
        control = new DriverController();
        roller = new CANSparkMax(Constants.INTAKE_ID, MotorType.kBrushless);
    }

    public void run() {
        vIntake = control.getIntake() - control.getOuttake();
        roller.set(vIntake); 
    }


    public static Sucker getInstance() {
        if (instance == null) {
            instance = new Sucker();
        }

        return instance;
    }
}
