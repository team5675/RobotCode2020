package frc.robot.auto.pathfinders;




public class TestPath {

    static TestPath instance;

    double angleSetpoint;
    double speedsetpoint;

    public PoseArray poseArray;

    int i;


    public TestPath() {
    }

    public double setSpeed() {

        return 0.7;
    }

    public double setAngle() {

        return poseArray.getAngle();
    }

    public static TestPath getInstance() {

        if(instance == null) {

            instance = new TestPath();
        }

        return instance;
    }
}