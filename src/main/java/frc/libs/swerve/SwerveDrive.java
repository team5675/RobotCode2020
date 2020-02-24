package frc.libs.swerve;

public class SwerveDrive {

	boolean deadband;

	public double L = 25;//length of wheel axle distances
	public double W = 25;//width of wheel axle distances

	double r;

	public final double CONTROLLER_DEADBAND = .05;

	double a;
	double b;
	double c;
	double d;

	double aOld = 0;
	double bOld = 0;
	double cOld = 0;
	double dOld = 0;

	double braOld = 0;
	double blaOld = 0;
	double fraOld = 0;
	double flaOld = 0;

	
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

		aOld = a;

		bOld = b;

		cOld = c;

		dOld = d;
		

		a = forward + rotation  * (L / r); 
		
		b = strafe + rotation  * (W / r);
		
		c = forward - rotation * (L / r);
		
		d = strafe - rotation * (W / r);
		
		
		double backRightSpeed = 0;
				
		double backLeftSpeed = 0;
				
		double frontRightSpeed = 0;
				
		double frontLeftSpeed = 0;
		
		
		//Output is 0 to 1
		backRightSpeed = Math.hypot(a, c);
			
		backLeftSpeed = Math.hypot(a, d);

		frontRightSpeed = Math.hypot(b, c);
					
		frontLeftSpeed = Math.hypot(b, d);
		

		//Output is 0 to 5
		double backRightAngle 	= (((Math.atan2(a, c) / Math.PI) * 2.5) + 2.5) + backRight.getOffset(); 
		
		double backLeftAngle 	= (((Math.atan2(a, d) / Math.PI) * 2.5) + 2.5) + backLeft.getOffset();
		//									-1 to 1		   -2.5 to 2.5   0 to 5  
		double frontRightAngle	= (((Math.atan2(b, c) / Math.PI) * 2.5) + 2.5) + frontRight.getOffset();

		double frontLeftAngle	= (((Math.atan2(b, d) / Math.PI) * 2.5) + 2.5) + frontLeft.getOffset();


		if (braOld != backRightAngle) {//If the angle is different

			braOld = backRightAngle;//set it to the new angle

			if (((bOld * b) + (cOld * c)) < 0) {//if the angles are more than 90 apart

				backRightAngle += 2.5;//flip angle to other side to trvale less

				backRightSpeed *= -1;//invert wheel speed
			}
		}

		if (blaOld != backLeftAngle) {//If the angle is different

			blaOld = backLeftAngle;//set it to the new angle

			if (((dOld * d) + (cOld * c)) < 0) {//if the angles are more than 90 apart

				backLeftAngle += 2.5;//flip angle to other side to trvale less

				backLeftSpeed *= -1;//invert wheel speed
			}
		}

		if (fraOld != frontRightAngle) {//If the angle is different

			fraOld = frontRightAngle;//set it to the new angle

			if (((bOld * b) + (aOld * a)) < 0) {//if the angles are more than 90 apart

				frontRightAngle += 2.5;//flip angle to other side to trvale less

				frontRightSpeed *= -1;//invert wheel speed
			}
		}

		if (flaOld != frontLeftAngle) {//If the angle is different

			flaOld = frontLeftAngle;//set it to the new angle

			if (((dOld * d) + (aOld * a)) < 0) {//if the angles are more than 90 apart

				frontLeftAngle += 2.5;//flip angle to other side to trvale less

				frontLeftSpeed *= -1;//invert wheel speed
			}
		}

		//normalize wheel speeds
        double max = Math.abs(backRightSpeed);

        if (Math.abs(backLeftSpeed)   > max) { max = Math.abs(backLeftSpeed);}
        if (Math.abs(frontLeftSpeed)  > max) { max = Math.abs(frontLeftSpeed);}
        if (Math.abs(frontRightSpeed) > max) { max = Math.abs(frontRightSpeed);}

        if (max > 1) {

            backRightSpeed  /= max;
            backLeftSpeed   /= max;
            frontLeftSpeed  /= max;
            frontRightSpeed /= max;
		}
		
			backRight.drive(backRightSpeed, backRightAngle, deadband);
		
			backLeft.drive(backLeftSpeed, backLeftAngle, deadband);
		
			frontRight.drive(frontRightSpeed, frontRightAngle, deadband);
		
			frontLeft.drive(frontLeftSpeed, frontLeftAngle, deadband);
	}


		private WheelDrive backRight;
		
		private WheelDrive backLeft;
		
		private WheelDrive frontRight;
		
		private WheelDrive frontLeft;
		
		
		public SwerveDrive (WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft ) {
			
			//passing into the full full swerve class
			this.backRight = backRight;
			
			this.backLeft = backLeft;
			
			this.frontRight = frontRight;
			
			this.frontLeft = frontLeft;
		}
}