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


    double[][] trajectory = new double[SEGMENTS][VARIABLES];

    public PathWeezer(){
        mabsBalls = new MabsBalls();

        fileChooser();

        path = getFile();
        if(path.equals("A") && path.equals("B")) {
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

            file = new File(path);
            json = readFileAsString(path);
            setSegments();

            try {

                in = new Scanner(file);

            } catch(IOException e) {
            }
            
            setTrajectory();
            return true;
        }
        else if(trajectory == null) {
            mabsBalls.uhhhhh();
            if(path != "B" && path != "A") {
                file = new File(path);
                json = readFileAsString(path);
                setSegments();

                try {

                    in = new Scanner(file);

                } catch(IOException e) {
                }  
            
                setTrajectory();
                return true;
            }
            else return false;

        }
        else return false;
        
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

        for (int a = 0; a < SEGMENTS; a++) {

            for (int x = 0; x < VARIABLES; x++) {
                trajectory[a][x] = in.nextDouble();

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
