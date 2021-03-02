//pathweezer.java

package frc.robot.auto;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.modes.Mode;
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
    
    static int SEGMENTS = setSegments(); // obv not the real number
    final static int VARIABLES = 7;

    static long count;
    String path = setFile();
    String json;

    static double[][] trajectory = new double[SEGMENTS][VARIABLES];
    // modeSelector = new SendableChooser<Modes>();

    public PathWeezer() throws IOException {
        json = readFileAsString(path);
        Scanner in = new Scanner(path);
    }

    public static String setFile() {

        return "buh";

    }

    public static void setTrajectory() {

        for (int a = 0; a < SEGMENTS; a++) {

            for (int x = 0; x < VARIABLES; x++) {
                trajectory[a][x] = in.nextDouble();

            }
        }
    }

    public static double[][] getTrajectory() {
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

    public static int setSegments() {
        int segs = 0;
        while (in.hasNext()) {
            final int doubles = 0;

            final String fileX = setFile();
            count = fileX.chars().filter(ch -> ch == 'e').count();
        
           
            segs = (int)count/VARIABLES;
                
        }
    

        return segs;
    }







    public fileChooser() {

        modeSelector = new SendableChooser<Modes>();
        autoTable = NetworkTableInstance.getDefault().getTable("auto");
        waitTime = autoTable.getEntry("waitTime");
        startOffset = autoTable.getEntry("startOffset");

        modeSelector.addOption("DoNothing", Modes.DoNothing);
        modeSelector.addOption("blueA", Modes.blueA);
        modeSelector.addOption("blueB", Modes.blueB);
        modeSelector.addOption("redA", Modes.redA);

        SmartDashboard.putData(modeSelector);
    }


    public Mode getMode() {

        Modes result = modeSelector.getSelected();
        Mode modeToReturn = new DoNothing();
        
        switch (result) {
            case blueA:
                modeToReturn = new blueA();
                break;
            case blueB:
                modeToReturn = new blueB();
                break;
            case redA:
                modeToReturn = new redA();
                break;
            default:
                modeToReturn = new DoNothing();
        }

        //modeToReturn.waitTime = (int) waitTime.getNumber(0) * 1000;
        modeToReturn.startOffset = startOffset.getDouble(0);
        
        return modeToReturn;
    }




}
