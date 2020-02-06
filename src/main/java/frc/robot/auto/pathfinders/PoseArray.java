package frc.robot.auto.pathfinders;



public class PoseArray {

    double[][] poseArray;

    double angleSetpoint;
    
    double currentPoseX;
    double nextPoseX;
    double adjustedPoseX;
    double currentPoseY;
    double nextPoseY;
    double adjustedPoseY;

    int i;

    public PoseArray(double[][] array) {

        this.poseArray = array;

        i = 0;
    }

    public void addToArray(double xCoord, double yCoord) {

        poseArray[0][i] = xCoord;
        poseArray[1][i] = yCoord;
    }

    public int length() {

        return poseArray.length;
    }

    public double getAngle() {

        currentPoseX = poseArray[0][i];
        nextPoseX    = poseArray[0][i + 1];

        currentPoseY = poseArray[1][i];
        nextPoseY    = poseArray[1][i + 1];

        adjustedPoseX = nextPoseX - currentPoseX;
        adjustedPoseY = nextPoseY - currentPoseY;

        angleSetpoint = ((Math.atan2(adjustedPoseY, adjustedPoseX) / Math.PI) * 2.5) + 2.5 ;

        i++;

        return angleSetpoint;
    }
}