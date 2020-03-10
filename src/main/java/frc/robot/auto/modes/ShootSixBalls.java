/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.modes;

import frc.robot.auto.ActionRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.ShootBalls;
import frc.robot.subsystems.Sucker;

/**
 * Add your docs here.
 */
public class ShootSixBalls extends Mode {

    ActionRunner actionRunner = ActionRunner.getInstance();
    Pathfinder pathfinder = Pathfinder.getInstance();
    Sucker sucker = Sucker.getInstance();
    
    public void run() {

        pathfinder.translate(0, -2, -5, 1);
        sucker.suckOrBlow(-0.5);
        actionRunner.run(new ShootBalls(3));
        sucker.deploy();
        sucker.suckOrBlow(-1);
        pathfinder.translate(-0.25, -7, -85, 0.25);
        sucker.suckOrBlow(-0.5);
        pathfinder.translate(-0.75, 2, -5, 1);
        actionRunner.run(new ShootBalls(3));
    }
}