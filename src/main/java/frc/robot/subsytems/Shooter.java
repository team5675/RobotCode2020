/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsytems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Shooter {

    Vision vision;

    CANSparkMax shootMotor;
    CANPIDController RPMController;
    CANEncoder RPMEncoder;

    double speed;
    double angle;
    double distanceLimelight;

    double height;
    double distance;
    double slope_constant;
    double tanLineSlope;
    double theta;
    double velocity;
    double thetaDegrees;

    public void init() {
        vision = new Vision();
        shootMotor = new CANSparkMax(Constants.SHOOTER_ID, MotorType.kBrushless);


        RPMController = shootMotor.getPIDController();
        RPMEncoder    = shootMotor.getEncoder();

        RPMController.setP(Constants.SHOOTER_FLYWHEEL_KP);
        RPMController.setD(Constants.SHOOTER_FLYWHEEL_KD);
        RPMController.setFF(Constants.SHOOTER_FLYWHEEL_KF);
        RPMController.setOutputRange(-1, 1);
    }

    /**
     * Sets the launcher's hood to an angle
     * @param hoodAngle the angle setpoint (in radians)
     */
    public void setAngle(double hoodAngle){
    }

    /**Sets the launcher's RPM
     * @param velocityRPM The setpoint velocity for the power cell
     */
    public void setRPM(double velocityRPM){

        double RPM = 60 * (velocity * Constants.SHOOTER_WHEEL_DIAMTER) / Math.PI;

        RPMController.setReference(RPM, ControlType.kVelocity);
    }

    public void autoAimAtTarget() { //Distance, angle, and velocity
        //angle = vision.getVerticalOffset() + Constants.CAM_ANGLE;
        //distanceLimelight = (Constants.PORT_HEIGHT - Constants.CAM_HEIGHT) / Math.tan(angle);

        double[] setpoints = getSetpoints(distanceLimelight);

        double angleSetpoint    = setpoints[0];
        double velocitySetpoint = setpoints[1];

        setAngle(angleSetpoint);
        setRPM(velocitySetpoint);
    }

    /**
     * Returns the launcher angle and exit velocity based on the distance
     * @apiNote
     * index 0 = angle (in radians) 
     * @apiNote
     * index 1 = velocity (in ft/s)
     * 
     * @param distance The distance from the port. (Use limelight generated distance)
     */
    public double[] getSetpoints(double distance) {

        //delcaring our distances as the same so no weird mixups
        this.distance = distance;

        //getting adjusted height
        height = (Constants.PORT_HEIGHT - Constants.SHOOTER_HEIGHT);

        //grabbing the slope constant to calculate tangent line with
        slope_constant = height / (distance * distance);

        tanLineSlope = 2 * distance * slope_constant;

        //The needed launcher velocity
        theta = Math.atan(tanLineSlope);

        velocity = distance / (Math.cos(theta) * Math.sqrt((height - (Math.tan(theta) * distance)) / -4.9));

        thetaDegrees = Math.toDegrees(theta);

        System.out.printf("Shooter angle: %f \nVelocity: %f", thetaDegrees, velocity);

        double[] setpoints = {theta, velocity};

        return setpoints;
    }

}