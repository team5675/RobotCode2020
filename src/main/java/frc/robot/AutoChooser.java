/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.auto.modes.DoNothing;
import frc.robot.auto.modes.Mode;

/**
 * Add your docs here.
 */
public class AutoChooser {

    static AutoChooser instance;

    SendableChooser<Mode> modeSelector;


    public AutoChooser() {

        modeSelector = new SendableChooser<Mode>();

        modeSelector.addOption("Do nothing", new DoNothing());
    }


    public void runMode() {
        
    }
    

    public static AutoChooser getInstance() {

        if (instance == null) {

            instance = new AutoChooser();
        }

        return instance;
    }
}