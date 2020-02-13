/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.modes;

import frc.robot.auto.ActionRunner;
import frc.robot.auto.actions.LineUpWithTarget;

/**
 * Add your docs here.
 */
public class ShootThreeBalls implements Mode {

    static ActionRunner actionRunner = ActionRunner.getInstance();

    public ShootThreeBalls() {
        System.out.println("I'm running no");
    }
    
    public void run() {
        System.out.println("Starting mode");

        actionRunner.run(new LineUpWithTarget());

        System.out.println("Mode complete!");
    }
}