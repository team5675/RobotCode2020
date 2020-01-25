/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.Constants;

/**
 * Uses vision to line up.
 */
public class LineUpWithTarget implements Action {

    public LineUpWithTarget() {

    }

    public void run() {
        
        System.out.println(navX.getAngle() * 0.01);
        drive.move(0, vision.getHorizontalOffset() * Constants.AUTO_STRAFE_P, navX.getAngle() * 0.01, 0, true);
    }
}