/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Pneumatics {


    static Pneumatics instance;

    Compressor compressor;


    public Pneumatics() {

        compressor = new Compressor(Constants.COMPRESSOR_ID);
    }


    //Returns true if the pressure is low
    public boolean getPressureSwitch() {

        return compressor.getPressureSwitchValue();
    }


    public void runCompressor() {

        compressor.start();
    }


    public void stopCompressor() {

        compressor.stop();
    }

    
    static public Pneumatics getInstance() {

        if (instance == null) {

            instance = new Pneumatics();
        }

        return instance;
    }
}