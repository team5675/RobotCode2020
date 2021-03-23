package frc.robot.auto;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Sucker;
import frc.robot.Constants;
/*
drive forward 5 feet to c3 
	SwerveDrive.drive();
	use elijahs 'check current' code
	if encoder values between 4.8 ft amd 5.2 ft && current spike == true 
				run path a
				
	else 
			run path b
*/
public class MabsBalls {
	
	Waypoint[] firstPointsRed, firstPointsBlue, secondPointsRed, secondPointsBlue;
	//FirstOrderFieldIntegrator

	static MabsBalls instance;
	PathWeezer pathWeezer;
	Drive drive;
	Sucker sucker;

	double ySpeed;
	double ftMoved;
	static final double slowFactor = 1; //between 0 and 1

	boolean isFirstMap;
	boolean cont;
	boolean pathA;
	boolean pathB;
	

	public MabsBalls() {
		cont = true;
		pathA = false;
		pathB = false;

		drive = Drive.getInstance();
		sucker = Sucker.getInstance();
	}

	public void moveOneAndAHalfFeet(){
		double flEnc = drive.getFrontLeft().getSpeedPosition();
		double frEnc = drive.getFrontRight().getSpeedPosition();
		double blEnc = drive.getBackLeft().getSpeedPosition();
		double brEnc = drive.getBackRight().getSpeedPosition();
		double avg = ((flEnc + frEnc +blEnc + brEnc)/4);
		ySpeed = slowFactor*(-0.33 * avg + 1); 
		drive.move(0, ySpeed, 0,  0, true);
	}

	public void runUntilTrajectory(){
		pathWeezer = PathWeezer.getInstance();
		double flEnc = drive.getFrontLeft().getSpeedPosition();
		double frEnc = drive.getFrontRight().getSpeedPosition();
		double blEnc = drive.getBackLeft().getSpeedPosition();
		double brEnc = drive.getBackRight().getSpeedPosition();
		double avg = ((flEnc + frEnc +blEnc + brEnc)/4);

		sucker.suckOrBlow(-1); //or whatever speed value we want it at

		if(cont) moveOneAndAHalfFeet();

		sucker.ballIn(); //basically just keeps rechecking if a ball is found

		ftMoved = avg / Constants.ETPF;

		if(!pathA && !pathB) {  //kinda to not waste computing power on the below
			if(ftMoved > 1.3 && ftMoved < 1.7 && sucker.getBallIn()) {//we need to figure out the encoder ticks per foot on the robot
				cont = false;
				pathA = true;
			}
			else if(ftMoved > 1.7) {
				cont = false;
				pathB = true;
			}
		}
		if((pathA || pathB) && isFirstMap) {
			if(pathA) {
				pathWeezer.setWhichPath("paths/reda.wpilib.json");
			}
			
			if(pathB) {
				pathWeezer.setWhichPath("paths/bluea.wpilib.json");
			}
		}
		else if((pathA || pathB) && !isFirstMap) {
			if(pathA) {
				pathWeezer.setWhichPath("paths/redb.wpilib.json");
			}
			
			if(pathB) {
				pathWeezer.setWhichPath("paths/blueb.wpilib.json");
			}
		}
	}	

	public void setIsFirstMap(boolean isFirstMap) {
		this.isFirstMap = isFirstMap;
	}

	public static MabsBalls getInstance() {
		if (instance == null) {

            instance = new MabsBalls();
        }

        return instance;
	}

	
}