/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsytems;

import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Shooter {

    public double calcDistance() {
        double angleToPort = Constants.camAngle + Vision.getVerticalOffset();
        return Constants.heightOffset / Math.tan(angleToPort);

        
    }
}
