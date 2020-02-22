package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ControlType;
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
    Spark spinMotor;
    CANPIDController spinnerController;

    DoubleSolenoid spinDeployArm;

    I2C.Port i2c = I2C.Port.kOnboard;

    ColorSensorV3 colorSensor;

    ColorMatch colorMatch;

    String past;
    String current;
    boolean isOut;

    public int changes = 0;

    String realTarget;

    final Color blue = ColorMatch.makeColor(0.143, 0.427, 0.429);
    final Color green = ColorMatch.makeColor(0.197, 0.561, 0.240);
    final Color red = ColorMatch.makeColor(0.561, 0.232, 0.114);
    final Color yellow = ColorMatch.makeColor(0.361, 0.524, 0.113);
    final String colors = "BYRG";

    public Spinner() {
        
        spinMotor = new Spark(Constants.SPINNER_MOTOR_ID);

        spinDeployArm = new DoubleSolenoid(Constants.SPINNER_ARM_IN_CHANNEL, Constants.SPINNER_ARM_OUT_CHANNEL);

        colorSensor = new ColorSensorV3(i2c); //try instantiating outside of contructor

        colorMatch = new ColorMatch();

        colorMatch.addColorMatch(blue);
        colorMatch.addColorMatch(green);
        colorMatch.addColorMatch(red);
        colorMatch.addColorMatch(yellow);

        isOut = false;
        past = "";
        current = "";
    }

    public void runRotation() {

        current = getCurrentColor();

        System.out.println("Changes: " + changes + "Current: " + getCurrentColor());

        if(!current.equals(past)) {
            changes++;
        }

        if(changes < 30) {
            spinMotor.set(1);
        }
        else {
            spinMotor.set(0);
        }

        past = current;
    }

    public void runColor() {
        System.out.println("Target: " + realTarget + ", Current: " + getCurrentColor());

        if(getCurrentColor().equals(realTarget)) {
        spinMotor.set(0);
        }
        else {
            spinMotor.set(0.4);
        }
        
    }

    public void deploySpinner() {
        if(isOut) {
            spinDeployArm.set(Value.kReverse);
        }
        else{
            spinDeployArm.set(Value.kForward);
        }
        isOut = !isOut;
    }

    public void setTargets()
    {
        String target = DriverStation.getInstance().getGameSpecificMessage();

        if(target.equals("B")) { //accounts for offset in sensor and target color
            realTarget = "R";
        }
        else if(target.equals("G")) {
            realTarget = "Y";
        }
        else if(target.equals("R")) {
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
        Color detected = colorSensor.getColor();
        ColorMatchResult match = colorMatch.matchClosestColor(detected);

        if(match.color == blue) {
            return "B"; 
        }
        else if( match.color == yellow) {
            return "Y";
        }
        else if(match.color == red) {
            return "R";
        }
        else if(match.color == green) {
            return "G";
        }
        else {
            return realTarget;
        }
    }

    public static Spinner getInstance() {

        if (instance == null) {

            instance = new Spinner();
        }

        return instance;
    }
}