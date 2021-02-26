//pathweezer.java

package frc.robot.auto;

import frc.robot.subsystems.*;
import java.util.Scanner;
import frc.libs.swerve.*;

import edu.wpi.first.wpilibj2.command.Subsystem;

import java.io.File;
import java.io.FileNotFoundException;

public class PathWeezer {

    // get
    // send

    private Subsystem drive = WheelDrive.getInstance();
    
    public static Scanner in;
    File pathWeaver;
    
    final static double P = 0.1; //change dis
    final static double I = 0.1;
    final static double D = 0.1;
    
    final static int SEGMENTS; //obv not the real number
    final static int VARIABLES = 6;
    File path = new File("bluea.wpilib.json");
    private static double[] currentSegment; // [x, y, angle, and whatever else longman send mee]
    static double[][] trajectory = new double[SEGMENTS][VARIABLES];
    
    public PathWeezer() {
           

        try {
    
            in = new Scanner(path);
    
        } catch (FileNotFoundException e) {
    
            e.printStackTrace();
        }
    }

        
    
    


       /* public void loop() { //change this name
    
            for(double[] segment : trajectory) {
    
            }
    */
        
        
        
    public static void setTrajectoryArray() {
    
        for (int i = 0; i < SEGMENTS; i++) {
    
            String[] data = in.nextLine().split(",");
    
            for (int k = 0; k < VARIABLES; k++) {
    
                trajectory[i][k] = Double.parseDouble(data[k]);
            }
        }
    }
}
    
    








