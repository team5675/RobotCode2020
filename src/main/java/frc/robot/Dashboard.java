/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Dashboard {

    static Dashboard instance;

    NetworkTable dashboardTable;

    
    public Dashboard() {

        dashboardTable = NetworkTableInstance.getDefault().getTable("dashboardTable");       
    }


    public static Dashboard getInstance() {

        if (instance == null) {

            instance = new Dashboard();
        }

        return instance;
    }
}