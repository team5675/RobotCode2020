package frc.libs.swerve;

public class SwerveDrive {

	boolean deadband;

	public double L = 25;//length of wheel axle distances
	public double W = 25;//width of wheel axle distances

	double r;

	//backright backleft frontright frontleft
	public double ANGLE_OFFSET1 = 3.035;//3.769; //from 0 to 5
	public double ANGLE_OFFSET2 = 4.086;//0.531;
	public double ANGLE_OFFSET3 = 3.647;//1.976;
	public double ANGLE_OFFSET4 = 0.297;//3.883;

	public final double CONTROLLER_DEADBAND = .05;
	
	/**
	 * Use for human input
	 * @param x1 Forward input
	 * @param y1 Strafe input
	 * @param rotation Azimuth input
	 * @param theta Gyro Yaw input
	 * @param robotCentric 
	 */
	public void drive (double x1, double y1, double rotation, double theta, boolean robotCentric) {

		if (CONTROLLER_DEADBAND * -1 < x1 && x1 < CONTROLLER_DEADBAND && CONTROLLER_DEADBAND * -1 < y1 && 
			y1 < CONTROLLER_DEADBAND && CONTROLLER_DEADBAND * -1 < rotation && rotation < CONTROLLER_DEADBAND){

			deadband = true;
		}

		else{ deadband = false; }

		double temp;
		double strafe;
		double forward;

		r = Math.hypot(L, W);
	
		if (robotCentric) {

			forward = -x1;
			strafe  = y1;
		}

		else {

			temp = y1 * Math.cos(Math.toRadians(theta)) + x1 * Math.sin(Math.toRadians(theta)); //allows for field centric control
		
			strafe = -y1 * Math.sin(Math.toRadians(theta)) + x1 * Math.cos(Math.toRadians(theta));

			forward = temp;
		}
		
		double a = strafe - rotation  * (L / r); //placeholder vector values
		
		double b = strafe + rotation  * (L / r);
		
		double c = forward - rotation * (W / r);
		
		double d = forward + rotation * (W / r);
		
		
		double backRightSpeed = 0; //calculating speed
				
		double backLeftSpeed = 0;
				
		double frontRightSpeed = 0;
				
		double frontLeftSpeed = 0;
		
		
		//Output is 0 to 1
		backRightSpeed = Math.hypot(a, c);
			
		backLeftSpeed = Math.hypot(a, d);

		frontRightSpeed = Math.hypot(b, c);
					
		frontLeftSpeed = Math.hypot(b, d);
		

		//Output is 0 to 360 degrees
		double backRightAngle 	= (((Math.atan2(a, c) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET1; 
		
		double backLeftAngle 	= (((Math.atan2(a, d) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET2;
		//									-1 to 1		   -2.5 to 2.5   0 to 5  
		double frontRightAngle	= (((Math.atan2(b, c) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET3;

		double frontLeftAngle	= (((Math.atan2(b, d) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET4;

		//normalize wheel speeds
        double max = backRightSpeed;

        if (backLeftSpeed > max)   { max = backLeftSpeed;}
        if (frontLeftSpeed > max)  { max = frontLeftSpeed;}
        if (frontRightSpeed > max) { max = frontRightSpeed;}

        if (max > 1) {

            backRightSpeed  /= max;
            backLeftSpeed   /= max;
            frontLeftSpeed  /= max;
            frontRightSpeed /= max;
		}
		
			backRight.drive(backRightSpeed, backRightAngle, deadband); //just using a class to organize modules together
		
			backLeft.drive(backLeftSpeed, backLeftAngle, deadband);
		
			frontRight.drive(frontRightSpeed, frontRightAngle, deadband);
		
			frontLeft.drive(frontLeftSpeed, frontLeftAngle, deadband);
	}

	/**Set the robot to a specified angle (in degrees)
	 * @param gyroAngle The navX's  measured angle
	 * @param setpointAngle The desired setpoint angle
	 */
	public void rotateToAngle(double gyroAngle, double setpointAngle) {

		r = Math.hypot(L, W);

		double gyroError = (gyroAngle % 360) - setpointAngle;

		double kP = 0.3;

				//Output is 0 to 1
		double gyroSpeedSetpoint = gyroError * kP;

		//3.125 = the roraion angle required
		double gyroAngleSetpoint = 3.125;

		backRight.drive(gyroSpeedSetpoint, gyroAngleSetpoint + ANGLE_OFFSET1, false);
		backRight.drive(gyroSpeedSetpoint, gyroAngleSetpoint + ANGLE_OFFSET2, false);
		backRight.drive(gyroSpeedSetpoint, gyroAngleSetpoint + ANGLE_OFFSET3, false);
		backRight.drive(gyroSpeedSetpoint, gyroAngleSetpoint + ANGLE_OFFSET4, false);

	}


		private WheelDrive backRight;
		
		private WheelDrive backLeft;
		
		private WheelDrive frontRight;
		
		private WheelDrive frontLeft;

		public void zeroEncoders(){

			ANGLE_OFFSET1 = backRight.getAzimuth();
	
			ANGLE_OFFSET2 = backLeft.getAzimuth();
	
			ANGLE_OFFSET3 = frontRight.getAzimuth();
	
			ANGLE_OFFSET4 = frontLeft.getAzimuth();
	
			System.out.println(ANGLE_OFFSET1 + " " + ANGLE_OFFSET2 + " " + ANGLE_OFFSET3 + " " + ANGLE_OFFSET4);
			System.out.println("Encoder Angles Set!");
		}

		
		
		public SwerveDrive (WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft ) {
			
			//passing into the full full swerve class
			this.backRight = backRight;
			
			this.backLeft = backLeft;
			
			this.frontRight = frontRight;
			
			this.frontLeft = frontLeft;
		}
}