//pathweezer.java

package frc.robot.auto;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.modes.DoNothing;
import frc.robot.auto.modes.Mode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
import frc.robot.auto.modes.blueA;
import frc.robot.auto.modes.blueB;
import frc.robot.auto.modes.redA;
import frc.robot.auto.modes.slalom;
import frc.robot.auto.modes.bounce;
import frc.robot.auto.modes.barrel;
*/



//import frc.robot.subsystems.Drive;
    


public class PathWeezer {

    public static Scanner in;
    File pathWeaver;
    
    final static double P = 0.1; //change dis
    final static double I = 0.1;
    final static double D = 0.1;
    
    int SEGMENTS = getSegments(); // obv not the real number
    final static int VARIABLES = 7;

    long count;
    String path;
    String json;

    SendableChooser<Modes> modeSelector;
    NetworkTable pathTable;
    NetworkTableEntry waitTime;
    NetworkTableEntry startOffset;

    //static pathChooser instance;


    double[][] trajectory = new double[SEGMENTS][VARIABLES];
    // modeSelector = new SendableChooser<Modes>();

    public PathWeezer() throws IOException {
        path = getFile();
        json = readFileAsString(path);
        Scanner in = new Scanner(path);
    }

    enum Modes {
        DoNothing,
        blueA,
        blueB,
        redA,
        redB,
        slalom,
        bounce,
        barrel,
    }

    public static String getFile() {

        return path;

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
        int segs = 0;
        while (in.hasNext()) {
            final int doubles = 0;

            final String fileX = getFile();
            count = fileX.chars().filter(ch -> ch == 'x').count();
            
           
            segs = (int)count/VARIABLES;
                
        }
    

        return segs;
    }


    public void fileChooser() {

        modeSelector = new SendableChooser<Modes>();
        pathTable = NetworkTableInstance.getDefault().getTable("path");
       // waitTime = pathTable.getEntry("waitTime");
        //startOffset = autoTable.getEntry("startOffset");

        modeSelector.addOption("Do nothing", Modes.DoNothing);
        modeSelector.addOption("blueA", Modes.blueA);
        modeSelector.addOption("blueB", Modes.blueB);
        modeSelector.addOption("redA", Modes.redA);
        modeSelector.addOption("redB", Modes.redB);
        modeSelector.addOption("slalom", Modes.slalom);
        modeSelector.addOption("bounce", Modes.bounce);
        modeSelector.addOption("barrel", Modes.bounce);

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
            case redB:
                modeToReturn = new redB();
                break;
                case slalom:
                modeToReturn = new slalom();
                break;
            case bounce:
                modeToReturn = new bounce();
                break;
            case barrel:
                modeToReturn = new barrel();
                break;
            default:
                modeToReturn = new DoNothing();
            
        }

        //modeToReturn.waitTime = (int) waitTime.getNumber(0) * 1000;
       // modeToReturn.startOffset = startOffset.getDouble(0);
        
        return modeToReturn;
    }




}
