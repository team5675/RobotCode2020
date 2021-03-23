//pathweezer.java

package frc.robot.auto;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

//import frc.robot.subsystems.Drive;
    


public class PathWeezer {

    static PathWeezer instance;
    MabsBalls mabsBalls;

    public static Scanner in;
    File file;
    
    final static double P = 0.1; //change dis
    final static double I = 0.1;
    final static double D = 0.1;
    
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
        System.out.println(trajectory == null);
        mabsBalls = MabsBalls.getInstance();
        fileChooser();

        path = "barrel.wpilib.json";
        if(path == null) path = "";

        if(path.equals("A") || path.equals("B")) {
            isSearch = true;
            if(path.equals("A")) mabsBalls.setIsFirstMap(true);
            else mabsBalls.setIsFirstMap(false);
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
            mabsBalls.runUntilTrajectory();
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

        for (int a = 0; a < SEGMENTS; a++) {

            for (int x = 0; x < VARIABLES; x++) {
                int currI = json.indexOf(".", lastI);

                int endI = json.indexOf(",", currI);
                if(json.indexOf("}", currI) < endI) endI = json.indexOf("}", currI);
                if(endI < 0) {
                    trajectory[a][x] = Double.parseDouble(json.substring(currI - 1, json.length() - 6));
                    break;
                }

                trajectory[a][x] = Double.parseDouble(json.substring(currI - 1, endI));
                lastI = currI + 1;
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
