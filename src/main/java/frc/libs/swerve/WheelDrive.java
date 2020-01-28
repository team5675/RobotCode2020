package frc.libs.swerve;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.controller.PIDController;

import frc.robot.Constants;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

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

Segment moduleSegment;

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

	/**
	 * Use this method for interfacing with pathfinder
	 * @param traj The module's trajectory
	 * @param ANGLE_OFFSET The module's encoder offset
	 * @param index Index that the pathfinder is currently on
	 */
	public void setModule(Trajectory traj, double ANGLE_OFFSET, int index) {

		moduleSegment = traj.get(index);

		error = moduleSegment.position - driveEncoder.getPosition();

		speedSetpoint = Constants.PATHFINDER_KP * error +
						Constants.PATHFINDER_KV * moduleSegment.velocity +
						Constants.PATHFINDER_KA *moduleSegment.acceleration;

		//			0 to 2PI			-PI to PI    -1 to 1    -2.5  0 to 5
		angleSetpoint = (((moduleSegment.heading - Math.PI) / Math.PI) * 2.5) + 2.5 + ANGLE_OFFSET;

		//using the wheelDrive drive method for simplicity
		//pass in the speed and angle value calculated, and set no deadband
		drive(speedSetpoint, angleSetpoint, false);
    }

	public void drive(double speed, double angle, boolean deadband) {
		
		//normalizes the encoder angle in case offsets caused it to go above 5
		if (angle > 5) {angle = angle - 5;}

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