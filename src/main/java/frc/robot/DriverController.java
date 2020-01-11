/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

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
}
