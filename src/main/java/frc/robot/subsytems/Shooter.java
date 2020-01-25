/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsytems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;
import frc.robot.subsystems.Vision;

/**
 * Add your docs here.
 */
public class Shooter {

    Vision vision;

    CANSparkMax shootMotor;

    double speed;
    double angle;
    double distance;

    PIDController speedPID;

    public void init() {
        vision = new Vision();
        shootMotor = new CANSparkMax(Constants.SHOOTER_ID, MotorType.kBrushless);
    }

    public void autoAimAtTarget() { 
        //angle = vision.getVerticalOffset() + Constants.CAM_ANGLE;
        //distance = (Constants.PORT_HEIGHT - Constants.CAM_HEIGHT) / Math.tan(angle);
        //velocity = Constants.PORT_HEIGHT /

    }

}