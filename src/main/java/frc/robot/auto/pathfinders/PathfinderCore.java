package frc.robot.auto.pathfinders;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.modifiers.SwerveModifier;

public class PathfinderCore {

    static PathfinderCore instance;

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

    public PathfinderCore() {

        driveBase = Drive.getInstance();
    }


    /**Use to configure the path
     * @param pathfinderName The name of the pathfinder file we want to load in
     */
    public void config(String pathfinderName) {

        //Grabbing all the pathfinder csvs from the RIO directory
        pathFiles = Filesystem.getDeployDirectory();
        pathFilesList = pathFiles.listFiles();

        try {

            //Index over all the pathfinder files
            for (int i = 0; i < pathFilesList.length; i++){

                //grab the file we're indexing
                String pathFileName = pathFilesList[i].getCanonicalPath();

                //If the file is the one we want...
                if (pathFileName.contains(pathfinderName)) {

                    //Load the trajectory in
                    trajectory = Pathfinder.readFromCSV(pathFilesList[i]);

                    br = trajectory;
                    bl = trajectory;
                    fr = trajectory;
                    fl = trajectory;

                    //Break out early if needed
                    break;
                }
            }
            
        } catch (IOException e) {

            System.out.printf("Can't find the pathfinder file! \n %f", e);
        }

        //Create the 4 individual trajectories for swerve
        for (int i = 0; i < trajectory.length(); i++) {
            
            Segment seg = trajectory.get(i);
            Segment fls = seg;
            Segment frs = seg;
            Segment bls = seg;
            Segment brs = seg;
            
            fls.x = seg.x - Constants.WHEEL_BASE_WIDTH / 2;
            fls.y = seg.y + Constants.WHEEL_BASE_DEPTH / 2;
            frs.x = seg.x + Constants.WHEEL_BASE_WIDTH / 2;
            frs.y = seg.y + Constants.WHEEL_BASE_DEPTH / 2;
            
            bls.x = seg.x - Constants.WHEEL_BASE_WIDTH / 2;
            bls.y = seg.y - Constants.WHEEL_BASE_DEPTH / 2;
            brs.x = seg.x + Constants.WHEEL_BASE_WIDTH / 2;
            brs.y = seg.y - Constants.WHEEL_BASE_DEPTH / 2;
            
            fl.segments[i] = fls;
            fr.segments[i] = frs;
            bl.segments[i] = bls;
            br.segments[i] = brs;

            System.out.println(fl.segments[i]);
        }
    }

    /**Sends the trajectory points to the swerve modules
     * @param traj The trajectory we want to run
     */
    public void runpathfinder(Trajectory traj) {


            //give this method the modified trajectories and the encoder offset
            for (int i = 0; i < traj.length(); i++) {


                driveBase.getBackRight().setModule(br, 3.029, i);
                driveBase.getBackLeft().setModule(bl, 4.084, i);
                driveBase.getFrontRight().setModule(fr, 3.302, i);
                driveBase.getFrontLeft().setModule(fl, 0.057, i);
            }
            
    }

    public static PathfinderCore getInstance() {

        if(instance == null) {

            instance = new PathfinderCore();
        }

        return instance;
    }
}