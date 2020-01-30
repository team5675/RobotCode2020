package frc.robot.auto.pathfinders;


import frc.robot.auto.pathfinders.PathfinderCore;


public class Pathfinder {

    Pathfinder instance;

    PathfinderCore pathfinderCore;
    String autoMode;

    public Pathfinder() {

        pathfinderCore = PathfinderCore.getInstance();

        pathfinderCore.config(autoMode);
    }

    public void setPath(){


    }



    public void getInstance() {

        if(instance == null) {

            instance = new Pathfinder();
        }
    }
}