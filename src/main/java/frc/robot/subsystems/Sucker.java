/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


/**
 * Sucks
 */

 
public class Sucker {

    static Sucker instance;


    public void expand() {

    }


    public void collapse() {

    }


    public void suck() {

    }

    
    public void blow() {

    }

    public static Sucker getInstance() {

        if (instance == null) {
            
            instance = new Sucker();
        }

        return instance;
    }
}
