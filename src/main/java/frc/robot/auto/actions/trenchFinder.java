/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.Constants;
import frc.robot.DriverController;
import frc.robot.auto.pathfinders.PathfinderCore;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Vision;

/**
 * Rotate shooter side of robot towards target with driver forward and strafe input
 */
public class trenchFinder implements Action {
    
    Drive drive = Drive.getInstance();
    PathfinderCore pathfinder = PathfinderCore.getInstance();


    public void start() {

    }


    public boolean loop() {

        return pathfinder.runPath(pathfinder.getPath("TEST")); 
    }

    
    public void stop() {

    }
}