//pathweezer.java

package frc.robot.auto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import frc.robot.subsystems.Drive;
    


public class PathWeezer {

    public static Scanner in;
    File pathWeaver;
    
    final static double P = 0.1; //change dis
    final static double I = 0.1;
    final static double D = 0.1;
    
    final static int SEGMENTS = setSegments(); // obv not the real number
    final static int VARIABLES = 7;

    File path = new File(setFile());
    
    static double[][] trajectory = new double[SEGMENTS][VARIABLES];

    modeSelector = new SendableChooser<Modes>();
    
    public PathWeezer() {

            try {
    
           Scanner in = new Scanner(path);
    
         } catch (FileNotFoundException e) {
    
            e.printStackTrace();
        }
    }



    public String setFile(){
        int a = 1;
        String phile = "bluea.wpilib.json";
        if ( a == 1){
         phile =  "blueb.wpilib.json";
        }
            else if( a==2){
          phile = "reda.wpilib.json";   
            }
            else if(a == 3){
          phile = "redb.wpilib.json";   

            }
            else if ( a == 4) {
            
            }
            else if ( a == 5) {

            }
            else if ( a == 6) {

            }
            else if ( a == 7) {

            }
            return phile;
        
    }



    public static void setTrajectory(){


        for(int a = 0; a < SEGMENTS; a++){

            for (int x = 0; x < VARIABLES; x++){
                  trajectory[a][x] = in.nextDouble(); 

            } 
        }
    }
    
    public static double[][] getTrajectory(){
        return trajectory;
    }

    public static int setSegments() {
        int segs = 0;
        while (in.hasNext()) {
            int doubles = 0;
                
            String path = in.next();

            try{
                   // it double
                double d = Double.parseDouble(path);
                doubles += 1;
                    
            }
            catch (NumberFormatException e) {
                //not double
                //doubles = doubles;
            }
            segs = doubles/VARIABLES;
                
        }
}
        return segs;
    }
}
