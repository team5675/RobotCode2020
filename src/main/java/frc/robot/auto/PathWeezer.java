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

    public static Scanner in;
    File pathWeaver;
    
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
    // modeSelector = new SendableChooser<Modes>();

    public PathWeezer() throws IOException {

        fileChooser();

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

    public static String readFileAsString(String file) throws IOException {
        String s = "baby";
        try{

            s= new String(Files.readAllBytes(Paths.get(file)));
        }
        catch(IOException a) {

        }
        return s;
    }

    public int getSegments() {

        count = path.chars().filter(ch -> ch == 'x').count();
    
        return (int)count;
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




}
