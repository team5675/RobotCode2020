package frc.robot.auto;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Sucker;
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

	Drive drive = new Drive();

	double ySpeed;
	static final double slowFactor = 1; //between 0 and 1
	


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
		
	}	


}