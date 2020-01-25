package frc.libs.swerve;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.controller.PIDController;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.DistanceFollower;

//import frc.robot.SwerveDrive.Encoder;

public class WheelDrive {
	
	
private CANSparkMax angleMotor;
private CANSparkMax speedMotor;

public CANEncoder driveEncoder;

private PIDController anglePID;

private AnalogInput azimuthEncoder;

DistanceFollower follower;

final double kV = 1 / 11.1;
final double kA = 0.000003;
final double kP = 0.01;

double P;
double I;
double D;

private final double kREVS_PER_METER = 29;

double error;
double error_last;
double error_deriv;

double speed;
double angle;
double totalDistance;
double offset;

boolean first = true;

	/**
	 * @param angleMotor The CAN ID of the azimuth controller
	 * @param speedMotor The CAN ID of the speed controller
	 * @param analogIn   The Analog ID of the azimuth encoder
	 * @param P          Proportional error for the PID loop
	 * @param I          Integrated error for the PID loop
	 * @param D          Derivative error for the PID loop
	 */
	public WheelDrive(int angleMotor, int speedMotor, int analogIn, double P, double I, double D) {

		// create our "wheels"
		this.angleMotor = new CANSparkMax(angleMotor, MotorType.kBrushless);
		this.speedMotor = new CANSparkMax(speedMotor, MotorType.kBrushless);

		anglePID = new PIDController(P, I, D);

		anglePID.enableContinuousInput(0, 5);

		this.azimuthEncoder = new AnalogInput(analogIn);

		this.driveEncoder = this.speedMotor.getEncoder();

		this.P = P;
		this.I = I;
		this.D = D;
	}

	public void setModule(Trajectory traj, double ANGLE_OFFSET) {

		//Run through config once
		if(first){

			//set conversion factor to scale rotations to meters
			driveEncoder.setPositionConversionFactor(1 / kREVS_PER_METER);

			//create an offset in case encoders mover before pathfinder starts
			offset = driveEncoder.getPosition();

			//creeate the new distance follower
			follower = new DistanceFollower(traj);

			//configure the PIDVAs for ff and fb control
			follower.configurePIDVA(0.0, 0.0, 0.0, 1/3, 0.0);

			//so we only run this once
			first = false;

			System.out.println("First Done");
		}
		
		//calculates the speed using the configured PIDVAs and the integrated encoder positon (which is scaled)
		speed = follower.calculate((driveEncoder.getPosition() - offset));

		//calculate the azimuth for the modules
		angle = ((Pathfinder.boundHalfDegrees(Pathfinder.r2d(follower.getHeading())) / 180) * 2.5) + 2.5 + ANGLE_OFFSET;
		
		//using the wheelDrive drive method for simplicity
		//pass in the speed and angle value calculated, and set no deadband
		drive(speed, angle, false);
    }

	public void drive(double speed, double angle, boolean deadband) {
		
		//normalizes the encoder angle in case offsets caused it to go above 5 or below 0
		if (angle > 5) {angle = angle - 5;}
		if (angle < 0) {angle = 5 - angle;}

		if (deadband) {

			speedMotor.set(0);
			angleMotor.set(0);
		}

		else {

			speedMotor.set(speed);
			angleMotor.set(anglePID.calculate(azimuthEncoder.getVoltage(), angle));
		}
	}
	
	public double getAzimuth() {

		return azimuthEncoder.getVoltage();
	}

	public void stop() {

		speedMotor.set(0);
		angleMotor.set(0);
	}
}