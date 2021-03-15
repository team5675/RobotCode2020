/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*package frc.robot.auto.actions;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

/**
 * Add your docs here.
 */
/*public class ShootBalls implements Action {

    Shooter shooter = Shooter.getInstance();
    NavX navX = NavX.getInstance();
    Drive drive = Drive.getInstance();
    Vision vision = Vision.getInstance();

    int amount;
    int ballsShot = 0;
    boolean debounce = false;
    double lastError = 0;

    NetworkTable logTable;
    NetworkTableEntry ballsShotLog;

    
    public ShootBalls(int newAmount) {

        logTable = NetworkTableInstance.getDefault().getTable("log");
        ballsShotLog = logTable.getEntry("ballsShot");
        ballsShotLog.setDouble(0);
        amount = newAmount;
    }


    public void start() {

        vision.lightOn();
    }


    public boolean loop() {

        double error = 0 - vision.getHorizontalOffset();
        double d = (error - lastError) / .04;
        drive.move(0, 0, vision.getHorizontalOffset() * Constants.AUTO_ROTATE_P + d * Constants.AUTO_ROTATE_D, 0, true);

        shooter.shoot();

        if (shooter.getRPMTarget() > shooter.getVelocity() + 100 && debounce) {

            ballsShot++;

            ballsShotLog.setDouble(ballsShot);

            debounce = false;
        }

        if (shooter.getVelocity() >= shooter.getRPMTarget() - 100 && !debounce) {

            debounce = true;
        }

        lastError = error;
        
        if (amount == ballsShot) {
            return false;
        } else {
            return true;
        }
    }


    public void stop() {
        
        vision.lightOff();
        shooter.stop();
    }
} */