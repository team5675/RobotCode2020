//pathweezer.java

package frc.robot.auto;

import java.io.File;
import java.io.FileNotFoundException;
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
    
    final static int SEGMENTS = setSegments(); // obv not the real number
    final static int VARIABLES = 7;


    String path = setFile();
    String json = readFileAsString(path);
    
    static double[][] trajectory = new double[SEGMENTS][VARIABLES];

    modeSelector = new SendableChooser<Modes>();
    
    public PathWeezer() {

            try {
    
           Scanner in = new Scanner(path);
    
         } catch (FileNotFoundException e) {
    
            e.printStackTrace();
        }
    }



    public static String setFile(){
       
        
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

    public static String readFileAsString(String file)
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static int setSegments() {
        int segs = 0;
        while (in.hasNext()) {
            int doubles = 0;
            
            String fileX = setFile();
            long count = fileX.chars().filter(ch -> ch == 'e').count();
           
           
            segs = doubles/VARIABLES;
                
        }
    

        return segs;
    }
}
