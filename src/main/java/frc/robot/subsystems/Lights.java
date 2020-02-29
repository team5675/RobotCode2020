/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

/**
 * Add your docs here.
 */
public class Lights {

    static Lights instance;


    public Lights() {

    }


    //public set


    public static Lights getInstance() {

        if (instance == null) {
            instance = new Lights();
        }

        return instance;
    }
}