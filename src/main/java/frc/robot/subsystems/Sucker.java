/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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

    DoubleSolenoid intakeArm;

    public Sucker() {
        control = DriverController.getInstance();
        roller = new CANSparkMax(Constants.INTAKE_ID, MotorType.kBrushed);

        intakeArm = new DoubleSolenoid(2, 3);
    }

    public void run() {

        if (control.getIntakeDeploy()) {

            intakeArm.set(Value.kForward);
        }

        if (control.getIntakeRetract()) {

            intakeArm.set(Value.kReverse);
        }
        
        vIntake = control.getOuttake() - control.getIntake();
        roller.set(vIntake); 
    }


    public static Sucker getInstance() {

        if (instance == null) {
            
            instance = new Sucker();
        }

        return instance;
    }
}
