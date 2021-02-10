package frc.robot.auto;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Sucker;
import frc.robot.auto.Pathfinder;
import frc.robot.Constants;

import com.fasterxml.jackson.databind.node.ContainerNode;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

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

	Pathfinder pathfinder;
	Drive drive;
	Sucker sucker;

	double ySpeed;
	static final double slowFactor = 1; //between 0 and 1

	boolean isFirstMap;
	boolean cont;
	boolean pathA;
	boolean pathB;
	double ftMoved;

	public MabsBalls() {
		cont = true;
		pathA = false;
		pathB = false;

		drive = Drive.getInstance();
		sucker = Sucker.getInstance();
		pathfinder = new Pathfinder(0.25, 1, 1, 1); //resolution, azimuthP, slowDownP, startSlowDownP
	}
	


	public void move5FeetBitch(){
		double flEnc = drive.getFrontLeft().getSpeedPosition();
		double frEnc = drive.getFrontRight().getSpeedPosition();
		double blEnc = drive.getBackLeft().getSpeedPosition();
		double brEnc = drive.getBackRight().getSpeedPosition();
		double avg = ((flEnc + frEnc +blEnc + brEnc)/4);
		ySpeed = slowFactor*((avg*avg / -25) + 1); //just a quadratic that is 1 at x = 0 and 0 at x = 5 for mapping
		drive.move(0, ySpeed, 0,  0, true);
	}

	public void uhhhhh(){
		double flEnc = drive.getFrontLeft().getSpeedPosition();
		double frEnc = drive.getFrontRight().getSpeedPosition();
		double blEnc = drive.getBackLeft().getSpeedPosition();
		double brEnc = drive.getBackRight().getSpeedPosition();
		double avg = ((flEnc + frEnc +blEnc + brEnc)/4);

		sucker.suckOrBlow(-1); //or whatever speed value we want it at

		if(cont) move5FeetBitch();

		sucker.ballIn(); //basically just keeps rechecking if a ball is found

		ftMoved = avg / Constants.ETPF;

		if(!pathA && !pathB) {  //kinda to not waste computing power on the below
			if(ftMoved > 4.8 && ftMoved < 5.2 && sucker.getBallIn()) {//we need to figure out the encoder ticks per foot on the robot
				cont = false;
				pathA = true;
			}
			else if(ftMoved > 5.2) {
				cont = false;
				pathB = true;
			}
		}
		if((pathA || pathB) && isFirstMap) {
			if(pathA) {
				pathfinder.translate(ftMoved, 5, firstPointsRed); //offsetx, offsety, Waypoint[] points
			}
			
			if(pathB) {
				pathfinder.translate(ftMoved, 5, firstPointsBlue); //offsetx, offsety, Waypoint[] points
			}
		}
		if((pathA || pathB) && !isFirstMap) {
			if(pathA) {
				pathfinder.translate(ftMoved, 7.5, secondPointsRed); //offsetx, offsety, Waypoint[] points
			}
			
			if(pathB) {
				pathfinder.translate(ftMoved, 7.5, secondPointsBlue); //offsetx, offsety, Waypoint[] points
			}
		}
	}	

}