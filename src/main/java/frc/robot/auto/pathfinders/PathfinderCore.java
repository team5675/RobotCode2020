package frc.robot.auto.pathfinders;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;

import frc.libs.swerve.WheelDrive;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.SwerveModifier;

public class PathfinderCore {

    File pathFiles;

    File[] pathFilesList;

    Trajectory trajectory;
    Trajectory.Segment seg;

    Trajectory br;
    Trajectory bl;
    Trajectory fr;
    Trajectory fl;

    Drive driveBase;
    //MAX VELOCITY FOR TEST SWERVE BOT = 3.3528 m/s

    SwerveModifier modifier;

    public PathfinderCore(Drive drive) {

        this.driveBase = drive;
    }


    public void config() {

        //Grabbing all the pathfinder csvs from the RIO directory
        pathFiles = Filesystem.getDeployDirectory();
        pathFilesList = pathFiles.listFiles();

        try {

            //Index over all the pathfinder files
            for (int i = 0; i < pathFilesList.length; i++){

                //grab the file we're indexing
                String pathFileName = pathFilesList[i].getCanonicalPath();

                //If the file is the one we want...
                if (pathFileName.contains(Constants.SELECTED_AUTO)) {

                    //Load the trajectory in
                    trajectory = Pathfinder.readFromCSV(pathFilesList[i]);

                    //Break out early if needed
                    break;
                }
            }
            
        } catch (IOException e) {

            System.out.printf("Can't find the pathfinder file! \n %f", e);
        }
        
        // Modify so it interfaces with swerve
        // Wheelbase Width = 0.5m, Wheelbase Depth = 0.5m, Swerve Mode = Default
        modifier = new SwerveModifier(trajectory).modify(0.635, 0.635, SwerveModifier.Mode.SWERVE_DEFAULT);

        //Get individual trajectories from swerve modifier
        br = modifier.getBackRightTrajectory();
        bl = modifier.getBackLeftTrajectory();
        fr = modifier.getFrontRightTrajectory();
        fl = modifier.getFrontLeftTrajectory();

        for(int i = 0; i < trajectory.length(); i++) {

            //yoink the segements from the trajectory
            seg = trajectory.get(i);

            //Print out segments for debugging
            System.out.printf("%f,%f,%f\n", 
            seg.velocity, seg.acceleration, seg.heading);
        }
    }

    public void runpathfinder() {

            //give this method the modified trajectories and the encoder offset
            driveBase.getBackRight().setModule(br, 3.029);
            driveBase.getBackLeft().setModule(bl, 4.084);
            driveBase.getFrontRight().setModule(fr, 3.302);
            driveBase.getFrontLeft().setModule(fl, 0.057);
    }
}