/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.subsystems.Shooter;

/**
 * Add your docs here.
 */
public class ShootAllBalls implements Action {

    Shooter shooter = Shooter.getInstance();

    
    public void start() {

    }


    public boolean loop() {

        shooter.shoot();

        return true;
    }


    public void stop() {

    }
}