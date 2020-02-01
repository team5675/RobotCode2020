package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import com.revrobotics.ColorSensorV3;

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

    I2C.Port i2c;

    ColorSensorV3 colorSensor;

    ColorMatch colorMatch;

    double revSetpoint;

    public Spinner() {
        
        spinMotor = new CANSparkMax(Constants.SPINNER_MOTOR_ID, MotorType.kBrushed);

        spinEncoder = spinMotor.getEncoder(EncoderType.kQuadrature, Constants.SPINNER_TICKS_PER_REV);

        spinnerController = spinMotor.getPIDController();

        spinnerController.setP(0.457);

        spinnerController.setFeedbackDevice(spinEncoder);

        //spinDeployArm = new DoubleSolenoid(Constants.SPINNER_ARM_IN_CHANNEL, Constants.SPINNER_ARM_OUT_CHANNEL);

        i2c = I2C.Port.kOnboard;

        colorSensor = new ColorSensorV3(i2c);

        colorMatch = new ColorMatch();
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