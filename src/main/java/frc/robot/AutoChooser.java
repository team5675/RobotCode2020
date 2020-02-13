/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.modes.DoNothing;
import frc.robot.auto.modes.Mode;
import frc.robot.auto.modes.ShootEightBalls;
import frc.robot.auto.modes.ShootThreeBalls;

/**
 * Add your docs here.
 */
public class AutoChooser {

    static AutoChooser instance;

    SendableChooser<Modes> modeSelector;
    NetworkTable autoTable;
    NetworkTableEntry waitTime;
    NetworkTableEntry startOffset;

    enum Modes {
        DoNothing,
        ShootThreeBalls,
        ShootEightBalls
    }

    public AutoChooser() {

        modeSelector = new SendableChooser<Modes>();
        autoTable = NetworkTableInstance.getDefault().getTable("auto");
        waitTime = autoTable.getEntry("waitTime");
        startOffset = autoTable.getEntry("startOffset");

        modeSelector.addOption("Do nothing", Modes.DoNothing);
        modeSelector.addOption("Shoot Three Balls", Modes.ShootThreeBalls);
        modeSelector.addOption("Shoot Eight Balls", Modes.ShootEightBalls);

        SmartDashboard.putData(modeSelector);
    }


    public Mode getMode() {

        Modes result = modeSelector.getSelected();
        Mode modeToReturn = new DoNothing();
        
        switch (result) {
            case ShootThreeBalls:
                modeToReturn = new ShootThreeBalls();
            case ShootEightBalls:
                modeToReturn = new ShootEightBalls();
        }

        modeToReturn.waitTime = (int) waitTime.getNumber(0) * 1000;
        modeToReturn.startOffset = startOffset.getDouble(0);
        
        return modeToReturn;
    }
    

    public static AutoChooser getInstance() {

        if (instance == null) {

            instance = new AutoChooser();
        }

        return instance;
    }
}