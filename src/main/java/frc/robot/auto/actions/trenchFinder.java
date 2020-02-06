/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.actions;

import frc.robot.auto.pathfinders.PathfinderCore;
import frc.robot.auto.pathfinders.Pose;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.auto.pathfinders.TestPath;

/**
 * Rotate shooter side of robot towards target with driver forward and strafe input
 */
public class trenchFinder implements Action {

    
    Drive drive = Drive.getInstance();
    PathfinderCore pathfinder = PathfinderCore.getInstance();
    TestPath path = TestPath.getInstance();
    NavX navX = NavX.getInstance();

    int i;


    public void start() {

        i = 0;

        
    }


    public boolean loop() {

        drive.getSwerve().rotateToAngle(navX.getAngle(), 0);
        
        for(int j = 0; j < path.poseArray.length; j++){

        drive.getBackRight().drive(path.getSpeed(), path.getAngle(i), false);

        }

        i++;
        drive.getSwerve().rotateToAngle(navX.getAngle(), 90);

        return true;
    }

    
    public void stop() {

    }
}