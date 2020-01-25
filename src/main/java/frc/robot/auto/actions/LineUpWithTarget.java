/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;


/**
 * Uses vision to line up.
 */
public class LineUpWithTarget extends Action {

    public void run() {
        
        drive.move(0, 0, vision.getHorizontalOffset() * 0.02, 0, true);
    }
}