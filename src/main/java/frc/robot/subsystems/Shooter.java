/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.controller.PIDController;
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
    double distance;
    double height;
    double theta;
    double distanceLimelight;

    PIDController speedPID;

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

    /**
     * Sets the launcher's RPM
     * @param velocityRPM The setpoint velocity for the shooter
     */
    public void setRPM(double velocityRPM){

        RPMController.setReference(velocityRPM, ControlType.kVelocity);
    }

    public void autoAimAtTarget() { //Distance, angle, and velocity
        //angle = vision.getVerticalOffset() + Constants.CAM_ANGLE;
        //distanceLimelight = (Constants.PORT_HEIGHT - Constants.CAM_HEIGHT) / Math.tan(angle);

        double angleSetpoint = getHoodAngle(distanceLimelight);

        setAngle(angleSetpoint);
    }


    public void shoot() {

        setRPM(Constants.SHOOTER_SETPOINT);
    }
    /**
     * Returns the hood angle based on the distance from port
     * Note 
     * @return angle in radians 
     * 
     * @param distance The distance from the port. (Use limelight generated distance)
     */
    public double getHoodAngle(double distance) {

        //delcaring our distances as the same so no weird mixups
        this.distance = distance;

        //getting adjusted height
        height = (Constants.VISION_TARGET_HEIGHT - Constants.SHOOTER_HEIGHT);

        //angle calculated by modeling a triangle approx to the parabola
        theta = Math.atan(2 * height / distance);

        return theta;
    }

}