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
import frc.robot.auto.actions.Wait;

/**
 * Add your docs here.
 */
public class ShootEightBalls extends Mode {

    public static double waitTime;
    public static double startOffset;
    
    ActionRunner actionRunner = ActionRunner.getInstance();
    Pathfinder pathfinder = Pathfinder.getInstance();


    public void run() {
        
        pathfinder.translate(0, -7.5, -90, 1);
        pathfinder.translate(17, 5, 0, 1);
        actionRunner.run(new Wait(3000));
        //actionRunner.run(new LineUpWithTarget());
       // actionRunner.run(new ShootAllBalls());
        pathfinder.translate(6, -4, -90, 1);
        pathfinder.translate(0, -5.5, -90, 1);
        pathfinder.translate(-6, 10, 0, 1);
        //actionRunner.run(new LineUpWithTarget());
        //actionRunner.run(new ShootAllBalls());
    }
}