package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
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

    String target;
    String realTarget;

    final Color blue;
    final Color green;
    final Color red;
    final Color yellow;
    final String colors = "BYRG";

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

        blue = ColorMatch.makeColor(0, 1, 1); // Might be 0 to 255
        green = ColorMatch.makeColor(0, 1, 0);
        red = ColorMatch.makeColor(1, 0, 0);
        yellow = ColorMatch.makeColor(1, 1, 0);
    }


    
    public void runRotationSequence() {

        deploySpinnerMotor();

        spinWheel();

        retractSpinnerMotor();
    }

    public void runColorSequence() {

        deploySpinnerMotor();

        setTargets();

        if(getCurrentColor() != realTarget) {
            spinMotor.set(0.5);
        }
        else {
            spinMotor.set(0);
        }

        //spinWheelColor();

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
        spinnerController.setReference(getRevs(), ControlType.kPosition);
    }

    public void setTargets()
    {
        target = DriverStation.getInstance().getGameSpecificMessage();

        if(target.equals("B")) { //accounts for offset in wheel
            realTarget = "R";
        }
        else if(target.equals("G")) {
            realTarget = "Y";
        }
        if(target.equals("R")) {
            realTarget = "B";
        }
        else {
            realTarget = "G";
        }
    }

    /**
     * Gives the current closest color from colorSensor
     * 
     * @return color
     */
    public String getCurrentColor() {
        ColorMatchResult match = colorMatch.matchClosestColor(colorSensor.getColor());

        if(match.color == blue) {
            return "B"; 
        }
        else if( match.color == green) {
            return "G";
        }
        else if(match.color == red) {
            return "R";
        }
        else {
            return "Y";
        }
    }

    /**
     * Gives revolutions to target color
     * 
     * @return revs to target
     */
    public double getRevs() {
        setTargets();

        return Constants.ONE_COLOR_REVS * //this gets the distance in wedges between current and target
        Math.abs(colors.indexOf(realTarget) - colors.indexOf(getCurrentColor())); 

    }

    public static Spinner getInstance() {

        if (instance == null) {

            instance = new Spinner();
        }

        return instance;
    }
}