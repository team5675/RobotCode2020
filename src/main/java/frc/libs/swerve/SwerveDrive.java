package frc.libs.swerve;

public class SwerveDrive {

	boolean deadband;

	public double L = 25;//length of wheel axle distances
	public double W = 25;//width of wheel axle distances

	//backright backleft frontright frontleft
	public double ANGLE_OFFSET1 = 0.5725;//3.769; //from 0 to 5
	public double ANGLE_OFFSET2 = 4.114;//0.531;
	public double ANGLE_OFFSET3 = 4.351;//1.976;
	public double ANGLE_OFFSET4 = 0.279;//3.883;

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

		double r = Math.hypot(L, W);
	
		if (robotCentric) {

			forward = x1;
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

		if (backRightAngle > 5) {backRightAngle = backRightAngle - 5;}
		if (backRightAngle < 0) {backRightAngle = 5 - backRightAngle;}
		
		double backLeftAngle 	= (((Math.atan2(a, d) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET2;

		if (backLeftAngle > 5) {backLeftAngle = backLeftAngle - 5;}
		if (backLeftAngle < 0) {backLeftAngle = 5 - backLeftAngle;}
		//									-1 to 1		   -2.5 to 2.5   0 to 5  
		double frontRightAngle	= (((Math.atan2(b, c) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET3;
		
		if (frontRightAngle > 5) {frontRightAngle = frontRightAngle - 5;}
		if (frontRightAngle < 0) {frontRightAngle = 5 - frontRightAngle;}

		double frontLeftAngle	= (((Math.atan2(b, d) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET4;

		if (frontLeftAngle > 5) {frontLeftAngle = frontLeftAngle - 5;}
		if (frontLeftAngle < 0) {frontLeftAngle = 5 - frontLeftAngle;}

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

		/**
		 * Use for interfacing swerve modules with vision data
		 * @param horError  Horizontal error 
		 * @param vertError Vertical error
		 * @param rotError 	Rotational error
		 */
		public void visionInterface(double horError, double vertError, double rotError) {

			double r = Math.hypot(L, W);
		
			double a = horError  -   rotError * (L / r); //placeholder vector values
			
			double b = horError  +   rotError * (L / r);
		
			double c = vertError -  rotError * (W / r);
		
			double d = vertError +  rotError * (W / r);
		
		
			double autoBackRightSpeed = 0; //calculating speed
				
			double autoBackLeftSpeed = 0;
				
			double autoFrontRightSpeed = 0;
				
			double autoFrontLeftSpeed = 0;
		
		
			//Output is 0 to 1
			autoBackRightSpeed = Math.hypot(a, c);
			
			autoBackLeftSpeed = Math.hypot(a, d);

			autoFrontRightSpeed = Math.hypot(b, c);
					
			autoFrontLeftSpeed = Math.hypot(b, d);
		

			//Output is 0 to 360 degrees
			double autoBackRightAngle 	= (((Math.atan2(a, c) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET1; 

			if (autoBackRightAngle > 5) {autoBackRightAngle = autoBackRightAngle - 5;}
			if (autoBackRightAngle < 0) {autoBackRightAngle = 5 - autoBackRightAngle;}
		
			double autoBackLeftAngle 	= (((Math.atan2(a, d) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET2;

			if (autoBackLeftAngle > 5) {autoBackLeftAngle = autoBackLeftAngle - 5;}
			if (autoBackLeftAngle < 0) {autoBackLeftAngle = 5 - autoBackLeftAngle;}
			//									-1 to 1		   -2.5 to 2.5   0 to 5  whatever the fuck it wants to be
			double autoFrontRightAngle	= (((Math.atan2(b, c) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET3;
		
			if (autoFrontRightAngle > 5) {autoFrontRightAngle = autoFrontRightAngle - 5;}
			if (autoFrontRightAngle < 0) {autoFrontRightAngle = 5 - autoFrontRightAngle;}

			double autoFrontLeftAngle	= (((Math.atan2(b, d) / Math.PI) * 2.5) + 2.5) + ANGLE_OFFSET4;

			if (autoFrontLeftAngle > 5) {autoFrontLeftAngle = autoFrontLeftAngle - 5;}
			if (autoFrontLeftAngle < 0) {autoFrontLeftAngle = 5 - autoFrontLeftAngle;}

			//normalize wheel speeds
       	    double max = autoBackRightSpeed;

        	if (autoBackLeftSpeed > max)   { max = autoBackLeftSpeed;}
       		if (autoFrontLeftSpeed > max)  { max = autoFrontLeftSpeed;}
        	if (autoFrontRightSpeed > max) { max = autoFrontRightSpeed;}

        	if (max > 1) {

            	autoBackRightSpeed  /= max;
            	autoBackLeftSpeed   /= max;
            	autoFrontLeftSpeed  /= max;
            	autoFrontRightSpeed /= max;
			}

			backRight.drive(autoBackRightSpeed, autoBackRightAngle, false);
			backLeft.drive(autoBackLeftSpeed, autoBackLeftAngle, false);
			frontRight.drive(autoFrontRightSpeed, autoFrontRightAngle, false);
			frontLeft.drive(autoFrontLeftSpeed, autoFrontLeftAngle, false);
		}
		
		
		public SwerveDrive (WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft ) {
			
			//passing into the full full swerve class
			this.backRight = backRight;
			
			this.backLeft = backLeft;
			
			this.frontRight = frontRight;
			
			this.frontLeft = frontLeft;
		}
}