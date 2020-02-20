/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;

import frc.robot.Constants;
import frc.robot.DriverController;

/**
 * Sucks
 */

 
public class Sucker {

    static Sucker instance;

    DriverController controller;

    DoubleSolenoid intakeSolenoid;
    
    Spark intake;

    public Sucker() {

        intakeSolenoid = new DoubleSolenoid(Constants.DEPLOY_ID_1, Constants.DEPLOY_ID_2);

        intake         = new Spark(Constants.INTAKE_ID);
    }

    public void run() {

        if(controller.getIntakeDeploy()) {

            expand();
        }

        if(controller.getIntakeRetract()) {

            collapse();
        }

        suck();
    }


    public void expand() {

        intakeSolenoid.set(Value.kForward);
    }


    public void collapse() {

        intakeSolenoid.set(Value.kReverse);
    }


    public void suck() {

        intake.set(controller.getIntake() - controller.getOuttake());
    }

    public static Sucker getInstance() {

        if (instance == null) {
            
            instance = new Sucker();
        }

        return instance;
    }
}
