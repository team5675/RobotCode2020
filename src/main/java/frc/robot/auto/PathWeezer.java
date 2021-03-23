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
            json = "[{\"time\":0.0,\"velocity\":0.0,\"acceleration\":19.685000000000002,\"pose\":{\"translation\":{\"x\":3.8794345707815827,\"y\":-7.943113977904344},\"rotation\":{\"radians\":-0.12219517069135498}},\"curvature\":0.0},{\"time\":0.10877394050869953,\"velocity\":2.1412150189137504,\"acceleration\":19.685000000000002,\"pose\":{\"translation\":{\"x\":3.995059243805545,\"y\":-7.956988959461563},\"rotation\":{\"radians\":-0.11403616212861808}},\"curvature\":0.13529380586989045},{\"time\":0.15398140024501505,\"velocity\":3.031123863823122,\"acceleration\":19.685000000000002,\"pose\":{\"translation\":{\"x\":4.111342700714026,\"y\":-7.969116462427955},\"rotation\":{\"radians\":-0.09173696219224305}},\"curvature\":0.2410409161117139},{\"time\":0.1888915128692814,\"velocity\":3.718329430831805,\"acceleration\":19.68500000000001,\"pose\":{\"translation\":{\"x\":4.2288155992504075,\"y\":-7.978049884590216},\"rotation\":{\"radians\":-0.058577181205594436}},\"curvature\":0.31679853574434075}]";
            //json = readFileAsString(path);
            setSegments();

            trajectory = new double[SEGMENTS][VARIABLES];
            setTrajectory();
            return true;
        }
        else if(trajectory == null) {
            mabsBalls.runUntilTrajectory();
            if(path != "B" && path != "A") {

                json = readFileAsString(path);
                setSegments();

                trajectory = new double[SEGMENTS][VARIABLES];
                setTrajectory();
                return true;
            }
            else return false;

        }
        else return true;
        
    }

    public void setWhichPath(String path) {
        this.path += path;
    }

    public void fileChooser() {

        fileSelector = new SendableChooser<String>();
        pathTable = NetworkTableInstance.getDefault().getTable("path");

        fileSelector.addOption("A", "A");
        fileSelector.addOption("B", "B");
        fileSelector.addOption("slalom", "slalom.wpilib.json");
        fileSelector.addOption("bounce", "bounce.wpilib.json");
        fileSelector.addOption("barrel", "barrel.wpilib.json");

        SmartDashboard.putData(fileSelector);
    }


    public String getFile() {
        
        return fileSelector.getSelected();
    }

    public void setSegments() {

        SEGMENTS = (int)(json.chars().filter(ch -> ch == 'x').count());
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
