package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import frc.robot.Constants;


/**
 * Controls the pizza spinner and color sensor
 */
public class Spinner {

    static Spinner instance;

    CANEncoder spinEncoder;
    CANSparkMax spinMotor;
    CANPIDController spinnerController;

    DoubleSolenoid spinDeployArm;

    double revSetpoint;

    public Spinner() {
        
        spinMotor = new CANSparkMax(Constants.SPINNER_MOTOR_ID, MotorType.kBrushed);

        spinEncoder = spinMotor.getEncoder(EncoderType.kQuadrature, Constants.SPINNER_TICKS_PER_REV);

        spinnerController = spinMotor.getPIDController();

        spinnerController.setP(0.457);

        spinnerController.setFeedbackDevice(spinEncoder);

        //spinDeployArm = new DoubleSolenoid(Constants.SPINNER_ARM_IN_CHANNEL, Constants.SPINNER_ARM_OUT_CHANNEL);
    }


    
    public void runRotationSequence() {

        deploySpinnerMotor();

        spinWheel();

        retractSpinnerMotor();
    }

    public void runColorSequence() {

        deploySpinnerMotor();

        spinWheelColor();

        retractSpinnerMotor();
    }


    public void deploySpinnerMotor() {

        spinDeployArm.set(Value.kForward);
    }

    public void retractSpinnerMotor() {

        spinDeployArm.set(Value.kReverse);
    }
    
    public void spinWheel() {

        spinnerController.setReference(Constants.SPINNER_REVS_SETPOINT, ControlType.kPosition);
    }

    public void spinWheelColor() {
        /*
        Code for taking in FMS color and reading sensor goes here
        */ 
        spinnerController.setReference(revSetpoint, ControlType.kPosition);
    }


    public static Spinner getInstance() {

        if (instance == null) {

            instance = new Spinner();
        }

        return instance;
    }
}