//pathweezer.java

package frc.robot.auto;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

//import frc.robot.subsystems.Drive;
    


public class PathWeezer {

    static PathWeezer instance;

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

    SendableChooser<String> fileSelector;
    NetworkTable pathTable;

    //static pathChooser instance;


    double[][] trajectory = new double[SEGMENTS][VARIABLES];

    public PathWeezer(){

        fileChooser();

        path = getFile();
        file = new File(path);
        json = readFileAsString(path);
        setSegments();

        try {
            in = new Scanner(file);
        } catch(IOException e) {
        }

        setTrajectory();

    }

    enum Modes {
        blueA,
        blueB,
        redA,
        redB,
        slalom,
        bounce,
        barrel,
    }

    public void fileChooser() {

        fileSelector = new SendableChooser<String>();
        pathTable = NetworkTableInstance.getDefault().getTable("path");

        fileSelector.addOption("blueA", "blueA");
        fileSelector.addOption("blueB", "blueB");
        fileSelector.addOption("redA", "redA");
        fileSelector.addOption("redB", "redB");
        fileSelector.addOption("slalom", "slalom");
        fileSelector.addOption("bounce", "bounce");
        fileSelector.addOption("barrel", "barrel");

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
