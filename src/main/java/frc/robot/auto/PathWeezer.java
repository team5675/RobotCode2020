//pathweezer.java

package frc.robot.auto;

import java.io.File;
import java.util.Scanner;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class PathWeezer {

    // get
    // send

  //private Subsystem drive = drive.getInstance();
    
    public static Scanner in;
    File pathWeaver;
    
    final static double P = 0.1; //change dis
    final static double I = 0.1;
    final static double D = 0.1;
    
    final static int SEGMENTS = setSegments(); // obv not the real number
    final static int VARIABLES = 6;
    File path = new File("bluea.wpilib.json");
   // private static double[] currentSegment; // [x, y, angle]
    static double[][] trajectory = new double[SEGMENTS][VARIABLES];
    
    public PathWeezer() {

        try {
    
           Scanner in = new Scanner(path);
    
        } catch (FileNotFoundException e) {
    
            e.printStackTrace();
        }
            
    }

     public static void setTrajectory(){


        for(int a = 0; a < SEGMENTS; a++){

            for (int x = 0; x < VARIABLES; x++){
                  trajectory[a][x] = in.nextDouble(); 

               } 
           }
        }
        public static void setSegments() {
            while (in.hasNext()) {
                int doubles = 0;
                String var = in.next();
                try{
                   // it double
                    double d = Double.parseDouble(var);
                     doubles += 1;
                    
                 }
                 catch (NumberFormatException e) {
                    //not double
                    doubles = doubles;
                }
            
                return doubles;
         }
        }
}