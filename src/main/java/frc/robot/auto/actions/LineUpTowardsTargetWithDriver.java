/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class LineUpTowardsTargetWithDriver extends Action {

    public void run(double forward, double strafe) {

        drive.move(forward, strafe, vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P, 0, true);
    }
}
