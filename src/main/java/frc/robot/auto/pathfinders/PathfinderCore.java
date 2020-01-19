package frc.robot.auto.pathfinders;

import frc.libs.swerve.WheelDrive;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.SwerveModifier;

public class PathfinderCore {

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

    public PathfinderCore(WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft) {

        this.backRight = backRight;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
    }

    //TODO: Add functionality to write paths to csv files, so robot doesn't have to create the spline evry time
    public void config() {

        // Type of spline generated, how many refinement samples, Time(5ms), Max Velocity,
        // accel, jerk MAX VELOCITY FOR TEST SWERVE BOT = 3.3528 m/s
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
                Trajectory.Config.SAMPLES_FAST, 0.05, 1.5, 2.0, 60.0);

        // Points we want the robot to pass through
        Waypoint[] points = new Waypoint[] { 
            new Waypoint(0, 0, 0), 
            new Waypoint(2, 2, 0) };

        // Generate the spline trajectory for the robot
        trajectory = Pathfinder.generate(points, config);
        
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