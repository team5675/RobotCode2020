package frc.robot.auto.pathfinders;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;

import frc.libs.swerve.WheelDrive;

import frc.robot.Constants;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.SwerveModifier;

public class PathfinderCore {

    File pathFiles;
    File selectedFile;

    File[] pathFilesList;

    Trajectory trajectory;
    Trajectory.Segment seg;

    Trajectory br;
    Trajectory bl;
    Trajectory fr;
    Trajectory fl;

    WheelDrive backRight;
    WheelDrive backLeft;
    WheelDrive frontRight;
    WheelDrive frontLeft;

    SwerveModifier modifier;

    public PathfinderCore() {

        backRight = new WheelDrive(Constants.DRIVE_BACK_RIGHT_AZIMUTH_ID, Constants.DRIVE_BACK_RIGHT_SPEED_ID, Constants.BR_AZIMUTH_ENCODER_ID, 
        Constants.BR_P, Constants.BR_I, Constants.BR_D);

        backLeft = new WheelDrive(Constants.DRIVE_BACK_LEFT_AZIMUTH_ID, Constants.DRIVE_BACK_LEFT_SPEED_ID, Constants.BL_AZIMUTH_ENCODER_ID, 
        Constants.BL_P, Constants.BL_I, Constants.BL_D);

        frontRight = new WheelDrive(Constants.DRIVE_FRONT_RIGHT_AZIMUTH_ID, Constants.DRIVE_FRONT_RIGHT_SPEED_ID, Constants.FR_AZIMUTH_ENCODER_ID, 
        Constants.FR_P, Constants.FR_I, Constants.FR_D);

        frontLeft = new WheelDrive(Constants.DRIVE_FRONT_LEFT_AZIMUTH_ID, Constants.DRIVE_FRONT_LEFT_SPEED_ID, Constants.FL_AZIMUTH_ENCODER_ID, 
        Constants.FL_P, Constants.FL_I, Constants.FL_D);
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
                if (pathFileName.contains("Trench")) {//Constants.SELECTED_AUTO)) {

                    //Load the trajectory in
                    trajectory = Pathfinder.readFromCSV(pathFilesList[i]);

                    System.out.println("Found the file at: " + pathFilesList[i]);
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

        
        for(int j = 0; j < trajectory.length(); j++) {

            //yoink the segements from the trajectory
            seg = trajectory.get(j);

            //Print out segmentts for debugging
            System.out.printf("%f,%f,%f\n", 
            seg.velocity, seg.acceleration, seg.heading);
        }
    }

    public void runpathfinder() {

            //give this method the modified trajectories and the encoder offset
            backRight.setModule(br, 3.029);
            backLeft.setModule(bl, 4.084);
            frontRight.setModule(fr, 3.302);
            frontLeft.setModule(fl, 0.057);
    }
}