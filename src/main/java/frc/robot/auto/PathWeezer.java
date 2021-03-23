//pathweezer.java

package frc.robot.auto;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Sucker;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

//import frc.robot.subsystems.Drive;
    


public class PathWeezer {

    static PathWeezer instance;

    public static Scanner in;
    File file;
    
    Drive drive;
	Sucker sucker;

	double ySpeed;
	double ftMoved;
	static final double slowFactor = 1; //between 0 and 1

	boolean isFirstMap;
	boolean cont;
	boolean isRed;
	boolean isBlue;
    
    int SEGMENTS; // obv not the real number
    final static int VARIABLES = 7;

    long count;
    String path;
    String json;
    boolean isSearch;

    SendableChooser<String> fileSelector;
    NetworkTable pathTable;

    //static pathChooser instance;


    double[][] trajectory;

    public PathWeezer(){
        cont = true;
		isRed = false;
		isBlue = false;
        drive = Drive.getInstance();
        sucker = Sucker.getInstance();

        System.out.println(trajectory == null);
        fileChooser();

        path = "barrel.wpilib.json";
        if(path == null) path = "";

        if(path.equals("A") || path.equals("B")) {
            isSearch = true;
            if(path.equals("A")) isFirstMap = true;
            else isFirstMap = false;
        }

    }

    enum Modes {
        A,
        B,
        slalom,
        bounce,
        barrel,
    }

    public boolean loopUntilTrajectory() { //returns if we have a path yet
        if(!isSearch && trajectory == null) {
            json = readFileAsString(path);
            setTrajectory();
            return true;
        }
        else if(trajectory == null) {
            runUntilTrajectory();

            if(path != "B" && path != "A") {

                json = readFileAsString(path);
                setTrajectory();
                return true;
            }
            return false;

        }
        return true;
        
    }

    public void setWhichPath(String path) {
        this.path += path;
    }

    public void fileChooser() {

        fileSelector = new SendableChooser<String>();
        pathTable = NetworkTableInstance.getDefault().getTable("path");

        fileSelector.addOption("A", "A");
        fileSelector.addOption("B", "B");
        fileSelector.addOption("slalom", "paths/slalom.wpilib.json");
        fileSelector.addOption("bounce", "paths/bounce.wpilib.json");
        fileSelector.addOption("barrel", "paths/barrel.wpilib.json");

        SmartDashboard.putData(fileSelector);
    }


    public String getFile() {
        
        return fileSelector.getSelected();
    }

    public static String readFileAsString(String file){
        String s = "";
        try{

            s = new String(Files.readAllBytes(Paths.get(file)));
        } catch(IOException e) {

        }
        return s;
    }

    public void setTrajectory() {
        SEGMENTS = (int)(json.chars().filter(ch -> ch == 'x').count());
        trajectory = new double[SEGMENTS][VARIABLES];

        int lastI = 0;
        int currI;
        int endI;

        for (int a = 0; a < SEGMENTS; a++) {

            for (int x = 0; x < VARIABLES; x++) {
                currI = json.indexOf(".", lastI);
                if(json.indexOf(":", currI - 4) > 0) currI = json.indexOf(":", currI - 4) + 1;

                endI = json.indexOf(",", currI);
                if(json.indexOf("}", currI) < endI) endI = json.indexOf("}", currI);
                if(endI < 0) {
                    trajectory[a][x] = Double.parseDouble(json.substring(currI, json.length() - 6));
                    break;
                }

                trajectory[a][x] = Double.parseDouble(json.substring(currI, endI));
                lastI = currI + 4;
            }
        }
    }

    public void moveOneAndAHalfFeet(){
		double flEnc = drive.getFrontLeft().getSpeedPosition();
		double frEnc = drive.getFrontRight().getSpeedPosition();
		double blEnc = drive.getBackLeft().getSpeedPosition();
		double brEnc = drive.getBackRight().getSpeedPosition();
		double avg = ((flEnc + frEnc +blEnc + brEnc)/4);
		ySpeed = slowFactor*(-0.33 * avg + 1); 
		drive.move(0, ySpeed, 0,  0, true);
    }
    
    public void runUntilTrajectory(){
		double flEnc = drive.getFrontLeft().getSpeedPosition();
		double frEnc = drive.getFrontRight().getSpeedPosition();
		double blEnc = drive.getBackLeft().getSpeedPosition();
		double brEnc = drive.getBackRight().getSpeedPosition();
		double avg = ((flEnc + frEnc +blEnc + brEnc)/4);

		sucker.suckOrBlow(-1); //or whatever speed value we want it at

		if(cont) moveOneAndAHalfFeet();

		sucker.ballIn(); //basically just keeps rechecking if a ball is found

		ftMoved = avg / Constants.ETPF;

		if(!isRed && !isBlue) {  //kinda to not waste computing power on the below
			if(ftMoved > 1.3 && ftMoved < 1.7 && sucker.getBallIn()) {//we need to figure out the encoder ticks per foot on the robot
				cont = false;
				isRed = true;
			}
			else if(ftMoved > 1.7) {
				cont = false;
				isBlue = true;
			}
		}
		if((isRed || isBlue) && isFirstMap) {
			if(isRed) {
				path = "paths/reda.wpilib.json";
			}
			
			if(isBlue) {
				path = "paths/bluea.wpilib.json";
			}
		}
		else if((isRed || isBlue) && !isFirstMap) {
			if(isRed) {
				path = "paths/redb.wpilib.json";
			}
			
			if(isBlue) {
				path = "paths/blueb.wpilib.json";
			}
		}
	}

    public double[][] getTrajectory() {
        return trajectory;
    }

    public static PathWeezer getInstance(){
        if(instance == null) {
            instance = new PathWeezer();
        }
        return instance;
    }

}
