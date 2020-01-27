/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Vision;

/**
 * Uses vision to line up.
 */
public class LineUpWithTarget implements Action {

    NavX navX = NavX.getInstance();
    Drive drive = Drive.getInstance();
    Vision vision = Vision.getInstance();
    

    public LineUpWithTarget() {

    }


    public void run() {
        
        drive.move(0, vision.getHorizontalOffset() * Constants.AUTO_STRAFE_P, navX.getAngle() * 0.01, 0, true);
    }
}