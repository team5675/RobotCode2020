package frc.robot.auto;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Sucker;
import frc.robot.auto.Pathfinder;
import frc.robot.Constants;
import frc.robot.auto.Waypoint;

import com.fasterxml.jackson.databind.node.ContainerNode;

import org.apache.commons.math3.ode.FirstOrderFieldIntegrator;

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
	//FirstOrderFieldIntegrator

	Pathfinder pathfinder;
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

		Waypoint[] firstPointsRed = new Waypoint[1];
		Waypoint[] firstPointsBlue = new Waypoint[1];
		Waypoint[] secondPointsRed = new Waypoint[1];
		Waypoint[] secondPointsBlue = new Waypoint[1];
		

		firstPointsRed = new Waypoint[] {

			new Waypoint(12.5, 5, 0),
			new Waypoint(15,12.5,0),
			new Waypoint(27.5, 7.5,0)
		};

		firstPointsBlue = new Waypoint[] {

			new Waypoint(15.5, 2.5, 0),
			new Waypoint(17.5,10,0),
			new Waypoint(22.5,7.5,0),
			new Waypoint(27.5,7.5,0)
		};
		
		secondPointsRed = new Waypoint[] {

			new Waypoint(7.5, 10, 0),
			new Waypoint(12.5,10,0),
			new Waypoint(17.5, 10,0),
		};
		secondPointsBlue = new Waypoint[] {

			new Waypoint(15.5, 5, 0),
			new Waypoint(20,10,0),
			new Waypoint(25,10,0),
		};
		


		drive = Drive.getInstance();
		sucker = Sucker.getInstance();
		pathfinder = new Pathfinder(0.25, 1, 1, 1); //resolution, azimuthP, slowDownP, startSlowDownP
	}
	


	public void moveOneAndAHalfFeetBitch(){
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

		if(cont) moveOneAndAHalfFeetBitch();

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