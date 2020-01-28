/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.modes;

import frc.robot.auto.actions.Action;
import frc.robot.auto.actions.LineUpWithTarget;

/**
 * Add your docs here.
 */
public class ShootThreeBalls implements Mode {

    Action lineUpWithTarget = new LineUpWithTarget();

    Action currentAction; 

    public void run() {

        lineUpWithTarget.start();
        while (lineUpWithTarget.run()) {} //temporary
        lineUpWithTarget.stop();
    }
}