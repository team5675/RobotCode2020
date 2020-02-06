package frc.robot.auto.pathfinders;



public class Pose {

    double[] pose;
    
    Pose currentPose;
    Pose nextPose;
    Pose adjustedPose;

    Pose[] poseArray;

    int i;

    public Pose(double xCoord, double yCoord) {

        pose[0] = xCoord;
        pose[1] = yCoord;
    }

    /**
     * 
     * @param i index of Pose array
     * @return The adjusted vector; use for angle calculation
     */
    public Pose getAdjustedPose(int i) {

        this.i = i;

        currentPose = poseArray[i];
        nextPose = poseArray[i + 1]; 

        adjustedPose.setXCoord(nextPose.getX() - currentPose.getX());
        adjustedPose.setYCoord(nextPose.getY() - currentPose.getY());

        return adjustedPose;
    }


    public double getX(){

        return pose[0];
    }

    public double getY() {

        return pose[1];
    }

    public double setXCoord(double xCoord) {

        pose[0] = xCoord;

        return pose[0];
    }

    public double setYCoord(double yCoord) {

        pose[1] = yCoord;

        return pose[1];
    }
}