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
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

/**
 * Add your docs here.
 */
public class ShootBalls implements Action {

    Shooter shooter = Shooter.getInstance();
    NavX navX = NavX.getInstance();
    Drive drive = Drive.getInstance();
    Vision vision = Vision.getInstance();

    int amount;
    int ballsShot = 0;
    boolean debounce = false;

    
    public ShootBalls(int newAmount) {

        amount = newAmount;
    }


    public void start() {

    }


    public boolean loop() {
        
        drive.move(0, 0, vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P, 0, true);

        shooter.shoot();

        if (shooter.getRPMTarget() > shooter.getVelocity() + 300 && debounce) {

            ballsShot++;

            debounce = false;
        }

        if (shooter.getVelocity() >= shooter.getRPMTarget() - 300 && !debounce) {

            debounce = true;
        }

        if (amount == ballsShot) {
            return false;
        } else {
            return true;
        }
    }


    public void stop() {
        
        shooter.stop();
    }
}