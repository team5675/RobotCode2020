package frc.robot.auto.pathfinders;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;

import frc.libs.swerve.WheelDrive;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
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

    Drive driveBase;

    WheelDrive backRight;
    WheelDrive backLeft;
    WheelDrive frontRight;
    WheelDrive frontLeft;

    SwerveModifier modifier;

    public PathfinderCore(Drive driveBase) {

        this.driveBase = driveBase;

        this.backRight  = driveBase.getBackRight();
        this.backLeft   = driveBase.getBackLeft();
        this.frontRight = driveBase.getFrontRight();
        this.frontLeft  = driveBase.getFrontLeft();
    }

    // TODO: Add functionality to write paths to csv files, so robot doesn't have to
    // create the spline every time
    public void config() {

        //Grabbing all the pathfinder csvs from the RIO directory
        pathFiles = Filesystem.getDeployDirectory();
        pathFilesList = pathFiles.listFiles();

        // Type of spline generated, how many refinement samples, Time(5ms), Max
        // Velocity,
        // accel, jerk MAX VELOCITY FOR TEST SWERVE BOT = 3.3528 m/s
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
                Trajectory.Config.SAMPLES_FAST, 0.05, 1.5, 2.0, 60.0);

        // Points we want the robot to pass through
        Waypoint[] points = new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(2, 2, 0) };

        // Generate the spline trajectory for the robot
        trajectory = Pathfinder.generate(points, config);

        //Constants.Selected_Auto = networkTable.getautoCharSequence("");

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
            backRight.setModule(br, 3.029);
            backLeft.setModule(bl, 4.084);
            frontRight.setModule(fr, 3.302);
            frontLeft.setModule(fl, 0.057);
    }
}