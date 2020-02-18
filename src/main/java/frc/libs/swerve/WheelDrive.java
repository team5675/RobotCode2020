package frc.libs.swerve;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.controller.PIDController;

//import frc.robot.SwerveDrive.Encoder;

public class WheelDrive {
	
	
private CANSparkMax angleMotor;
private CANSparkMax speedMotor;

public CANEncoder driveEncoder;

private PIDController anglePID;

private AnalogInput azimuthEncoder;

double P;
double I;
double D;

double ANGLE_OFFSET;


double error;
double angleSetpoint;
double speedSetpoint;

	/**
	 * @param angleMotor The CAN ID of the azimuth controller
	 * @param speedMotor The CAN ID of the speed controller
	 * @param analogIn   The Analog ID of the azimuth encoder
	 * @param P          Proportional error for the PID loop
	 * @param I          Integrated error for the PID loop
	 * @param D          Derivative error for the PID loop
	 * @param ANGLE_OFFSET The module's encoder offset
	 */
	public WheelDrive(int angleMotor, int speedMotor, int analogIn, double P, double I, double D, double ANGLE_OFFSET) {

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

		this.ANGLE_OFFSET = ANGLE_OFFSET;
	}


	public void drive(double speed, double angle, boolean deadband) {
		
		//normalizes the encoder angle in case offsets caused it to go above 5

		//Azimuth algorithm
		//If the angle difference is greather than 90 degrees...
		if (Math.abs(angle - azimuthEncoder.getVoltage()) > 1.25) {

			//Invert so less than 90 away
			angle += 2.5;
			//Invert speed to match
			speed *= -1;
		}

		if (angle > 5) {angle -= 5;}
		if (angle < 0) {angle += 5;}

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

	public double getSpeedPosition() {
		return driveEncoder.getPosition();
	}

	public void resetSpeedDistance() {
		driveEncoder.setPosition(0);
	}

	public void stop() {

		speedMotor.set(0);
		angleMotor.set(0);
	}

	public double getOffset() {

		return ANGLE_OFFSET;
	}
}