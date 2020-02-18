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
    
    static DriverController instance;

    XboxController mainController;
    XboxController auxController;

    public DriverController() {
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

    public boolean getResetYaw() {

        return mainController.getRawButton(4);
    }

    public boolean getStayStraight() {

        return mainController.getAButton();
    }

    public boolean getX() {

        return mainController.getXButton();
    }

    public boolean getLineUp() {

        return mainController.getBButton();
    }

    public boolean getOnLineUpPressed() {

        return mainController.getBButtonPressed();
    }

    public boolean getOnLineUpReleased() {

        return mainController.getBButtonReleased();
    }

    public double getIntake() {

        return auxController.getTriggerAxis(Hand.kRight);
    }

    public double getOuttake() {
        
        return auxController.getTriggerAxis(Hand.kLeft);
    }

    public boolean getIntakeDeploy() {

        return auxController.getBumper(Hand.kLeft);
    }

    public boolean getIntakeRetract() {

        return auxController.getBumper(Hand.kRight);
    }

    public boolean getSpinnerDeploy() {

        return auxController.getAButton();
    }

    public boolean getColor() {

        return auxController.getXButton();
    }

    public boolean getSpin() {
        return auxController.getBButton();
    }

    public double getColorManual() {
        return auxController.getRawAxis(0);
    }


    public static DriverController getInstance() {

        if (instance == null) {
            
            instance = new DriverController();
        }

        return instance;
    }
}