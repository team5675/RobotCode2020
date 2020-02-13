/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.modes;

import frc.robot.auto.ActionRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.LineUpWithTarget;
import frc.robot.auto.actions.ShootAllBalls;

/**
 * Add your docs here.
 */
public class ShootEightBalls extends Mode {

    public static double waitTime;
    public static double startOffset;
    
    ActionRunner actionRunner = ActionRunner.getInstance();
    Pathfinder pathfinder = Pathfinder.getInstance();


    public void run() {
        
        actionRunner.run(new LineUpWithTarget());
        pathfinder.translate(5.67, -3.5, 0);
        pathfinder.translate(0, -12.75, 0);
        pathfinder.translate(0, 12.75, 0);
        pathfinder.translate(-5.67, 3.5, 0);
        actionRunner.run(new LineUpWithTarget());
        actionRunner.run(new ShootAllBalls());
    }
}