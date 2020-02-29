/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Climber {

    static Climber instance;

    DoubleSolenoid lock;
    //DoubleSolenoid masterArm;
    Solenoid masterArmRaise;
    Solenoid masterArmCollapse;

    boolean locked = true;
    

    public Climber() {
        lock = new DoubleSolenoid(Constants.LOCK_SOLENOID_ID_1, Constants.LOCK_SOLENOID_ID_2);
        //masterArm = new DoubleSolenoid(Constants.MASTER_ARM_COLLAPSE_SOLENOID_ID, Constants.MASTER_ARM_RAISE_SOLENOID_ID);
        masterArmRaise = new Solenoid(Constants.MASTER_ARM_RAISE_SOLENOID_ID);
        masterArmCollapse = new Solenoid(Constants.MASTER_ARM_COLLAPSE_SOLENOID_ID);

        lock.set(Value.kForward);
    }


    public void releaseLock() {
        lock.set(Value.kReverse);
    }


    public void raiseMasterArm() {
        //masterArm.set(Value.kForward);
        masterArmRaise.set(true);
    }


    public void collapseMasterArm() {
        //masterArm.set(Value.kOff);
        //masterArmRaise.set(true);
        masterArmCollapse.set(true);
    }

    
    public void winch() {
        
    }
    
    
    public static Climber getInstance() {

        if (instance == null) {
            
            instance = new Climber();
        }

        return instance;
    }
}
