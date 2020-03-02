/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Climber {

    static Climber instance;

    DoubleSolenoid lock;
    Solenoid masterArmRaise;
    Solenoid masterArmCollapse;
    Spark winch;
    Spark troller;

    boolean locked = true;
    

    public Climber() {

        lock = new DoubleSolenoid(Constants.LOCK_SOLENOID_ID_1, Constants.LOCK_SOLENOID_ID_2);
        masterArmRaise = new Solenoid(Constants.MASTER_ARM_RAISE_SOLENOID_ID);
        masterArmCollapse = new Solenoid(Constants.MASTER_ARM_COLLAPSE_SOLENOID_ID);
        winch = new Spark(Constants.WINCH_MOTOR_ID);
        troller = new Spark(Constants.TROLLER_MOTOR_ID);

        lock.set(Value.kForward);
        masterArmRaise.set(false);
        masterArmCollapse.set(false);
    }


    public void releaseLock() {

        lock.set(Value.kReverse);
    }


    public void raiseMasterArm() {

        masterArmRaise.set(true);
        //masterArmCollapse.set(true);
    }


    public void collapseMasterArm() {

        masterArmRaise.set(false);
        masterArmCollapse.set(true);
    }

    
    public void winch() {

        winch.set(1);
    }


    public void stopWinch() {

        winch.set(0);
    }

    
    public void setTroller(double speed) {
        
        troller.set(speed);
    }
    
    
    public static Climber getInstance() {

        if (instance == null) {
            
            instance = new Climber();
        }

        return instance;
    }
}
