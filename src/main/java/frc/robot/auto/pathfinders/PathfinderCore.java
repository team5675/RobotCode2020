package frc.robot.auto.pathfinders;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

public class PathfinderCore {

    static PathfinderCore instance;

    File pathFiles;

    File[] pathFilesList;

    CharSequence chosenFile;

    Trajectory trajectory;
    Trajectory.Segment seg;

    Drive driveBase;
    //MAX VELOCITY FOR TEST SWERVE BOT = 3.3528 m/s

    public PathfinderCore() {

        driveBase = Drive.getInstance();
    }


    /**Use to configure the path
     * @param pathfinderName The name of the pathfinder file we want to load in
     */
    public Trajectory config(String pathfinderName) {

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

                    //Break out early if needed
                    break;
                }
            }
            
        } catch (IOException e) {

            System.out.printf("Can't find the pathfinder file! \n %f", e);
        }

        

        return trajectory;
    }

    /**Sends the trajectory points to the swerve modules
     * @param traj The trajectory we want to run
     */
    public void runpathfinder(Trajectory traj) {

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

            driveBase.getBackRight().setModule(brs, 3.029);
            driveBase.getBackLeft().setModule(bls, 4.084);
            driveBase.getFrontRight().setModule(frs, 3.302);
            driveBase.getFrontLeft().setModule(fls, 0.057);
        }
    }

    public static PathfinderCore getInstance() {

        if(instance == null) {

            instance = new PathfinderCore();
        }

        return instance;
    }
}