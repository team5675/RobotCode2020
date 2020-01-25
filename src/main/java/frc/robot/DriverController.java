/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Organize inputs from the Xbox Controllers
 */
public class DriverController {
    
    XboxController mainController;
    XboxController auxController;

    void init() {
        mainController = new XboxController(0);
        auxController = new XboxController(1);
    }

    public double getForward() {

        return mainController.getRawAxis(1);
    }

    public double getStrafe() {

        return mainController.getRawAxis(0);
    }

    public double getRotation() {

        return mainController.getRawAxis(4);
    }

    public boolean isFieldOriented() {

        return mainController.getBumper(Hand.kRight);
    }

    public boolean getA() {

        return mainController.getAButton();
    }

    public boolean getLineUp() {

        return mainController.getBButton();
    }

    public double getIntake() {
        return auxController.getTriggerAxis(Hand.kRight);
    }

    public double getOuttake() {
        return auxController.getTriggerAxis(Hand.kLeft);
    }
}