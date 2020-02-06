package frc.robot.auto.pathfinders;




public class TestPath {

    static TestPath instance;

    double angleSetpoint;
    double speedsetpoint;

    public Pose[] poseArray;

    int i;


    public TestPath() {
    }

    public double getAngle(int i) {

        this.i = i;

        angleSetpoint = ((Math.atan2(poseArray[i].getAdjustedPose(i).getY(), poseArray[i].getAdjustedPose(i).getX()) / Math.PI) * 2.5) + 2.5;

        return angleSetpoint;
    }

    public double getSpeed() {

        return 0.7;
    }

    public static TestPath getInstance() {

        if(instance == null) {

            instance = new TestPath();
        }

        return instance;
    }
}