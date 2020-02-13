/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Vision;

/**
 * Uses vision to line up.
 */
public class LineUpWithTarget implements Action {

    NavX navX = NavX.getInstance();
    Drive drive = Drive.getInstance();
    Vision vision = Vision.getInstance();
    

    public LineUpWithTarget() {

    }


    public void start() {

        vision.lightOn();
    }


    public boolean loop() {
        
        double distanceError = 10 - vision.getDistanceFromTarget();
        
        drive.move(distanceError * Constants.AUTO_FORWARD_P, vision.getHorizontalOffset() * Constants.AUTO_STRAFE_P, navX.getAngle() * Constants.AUTO_GYRO_P, 0, true);


        if (Math.abs(vision.getHorizontalOffset()) < 0.5 && //If conditions are met, stop
            Math.abs(distanceError) < 0 &&
            Math.abs(navX.getAngle()) < 3) {

            return false;
        } else {

            return true;
        }
    }

    
    public void stop() {
        
        vision.lightOff();
    }
}