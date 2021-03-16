/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.Constants;
import frc.robot.DriverController;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Vision;

/**
 * Rotate shooter side of robot towards target with driver forward and strafe input
 */
public class LineUpTowardsTargetWithDriver implements Action {
    
    DriverController driverController = DriverController.getInstance();
    Drive drive = Drive.getInstance();
    Vision vision = Vision.getInstance();
    NavX gyro = NavX.getInstance();

    //double lastError = 0;

    double targetAngle;
    double error;


    public void start() {

        targetAngle = gyro.getAngle() - vision.getHorizontalOffset();
    }


    public boolean loop() {

        error = -vision.getHorizontalOffset() * Constants.AUTO_STRAFE_P;

        drive.getBackRight().drive(error, 0, false); //rotate 3.2275
        drive.getBackLeft().drive(error, 0, false);//rotate 1.8066
        drive.getFrontRight().drive(error, 0, false);//rotate 2.0568
        drive.getFrontLeft().drive(error, 0, false);//rotate 2.7307

        return true; 
    }

    
    public void stop() {

        //lastError = 0;
    }
}
