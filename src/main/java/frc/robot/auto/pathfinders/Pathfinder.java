package frc.robot.auto.pathfinders;


import frc.robot.auto.pathfinders.PathfinderCore;
import frc.robot.Constants;


public class Pathfinder {

    Pathfinder instance;

    PathfinderCore pathfinderCore;
    String autoMode;

    public Pathfinder() {

        pathfinderCore = PathfinderCore.getInstance();

        pathfinderCore.config(autoMode);
    }





    public void getInstance() {

        if(instance == null) {

            instance = new Pathfinder();
        }
    }
}