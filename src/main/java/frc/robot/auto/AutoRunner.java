/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import frc.robot.auto.modes.Mode;

/**
 * Add your docs here.
 */
public class AutoRunner extends Thread {

    Mode mode;


    public AutoRunner(Mode newMode) {

        mode = newMode;
    }


    public void run() {

        mode.run();
    }
}