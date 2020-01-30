/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.Constants;
import frc.robot.DriverController;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Vision;

/**
 * Rotate shooter side of robot towards target with driver forward and strafe input
 */
public class LineUpTowardsTargetWithDriver implements Action {
    
    DriverController driverController = DriverController.getInstance();
    Drive drive = Drive.getInstance();
    Vision vision = Vision.getInstance();


    public void start() {

    }


    public boolean loop() {

        drive.move(driverController.getForward(), driverController.getStrafe(), vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P, 0, true);

        return true;
    }

    
    public void stop() {

    }
}
