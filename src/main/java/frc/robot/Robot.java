/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auto.ActionRunner;
import frc.robot.auto.ModeRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.Action;
import frc.robot.auto.actions.LineUpTowardsTargetWithDriver;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Sucker;
import frc.robot.subsystems.Spinner;


public class Robot extends TimedRobot {

  DriverController driverController;
  Dashboard        dashboard;

  Vision           vision;
  Sucker           sucker;
  Shooter          shooter;
  Drive            drive;
  NavX             navX;
  Spinner          spinner;
  Pneumatics       pneumatics;
  Climber          climber;

  Pathfinder       pathfinder;
  ModeRunner       modeRunner;
  ActionRunner     actionRunner;
  Action           lineUpTowardsTargetWithDriver;
  AutoChooser      autoChooser;


  @Override
  public void robotInit() {

    driverController = DriverController.getInstance();
    dashboard        = Dashboard.getInstance();
    
    drive            = Drive.getInstance();
    navX             = NavX.getInstance();
    vision           = Vision.getInstance();
    shooter          = Shooter.getInstance();
    sucker           = Sucker.getInstance();
    spinner          = Spinner.getInstance();
    pneumatics       = Pneumatics.getInstance();
    climber          = Climber.getInstance();

    actionRunner     = ActionRunner.getInstance();
    pathfinder       = Pathfinder.getInstance();
    autoChooser      = AutoChooser.getInstance();
  }

  
  @Override
  public void disabledPeriodic() {
    
    //run disabled code here like leds and stuff
  }


  @Override
  public void autonomousInit() {

    navX.resetYaw();
    
    modeRunner = new ModeRunner(autoChooser.getMode());

    actionRunner.start();
    modeRunner.start();
  }


  @Override
  public void autonomousPeriodic() {

    vision.loop();
    actionRunner.loop();
    pathfinder.loop();
  }


  @Override
  public void teleopInit() {

    actionRunner.forceStop();

    lineUpTowardsTargetWithDriver = new LineUpTowardsTargetWithDriver();
  }


  @Override
  public void teleopPeriodic() {

   //System.out.format("BR: %f \nBL: %f \nFR: %f \nFL: %f\n", drive.getBackRight().getAzimuth(), drive.getBackLeft().getAzimuth(), drive.getFrontRight().getAzimuth(), drive.getFrontLeft().getAzimuth());
   //System.out.println(drive.getBackRight().getAzimuth()); 

    //Reset Yaw on NavX
    if(driverController.getResetYaw()) {

      navX.resetYaw();
    }

    //Tele-op auto functions or manual drive
    double forward = driverController.getForward();
    double strafe = driverController.getStrafe();
    double rotation = driverController.getRotation();
    double angle = navX.getAngle();
    boolean isFieldOriented = driverController.isFieldOriented();

    //Shoot else run intake and drive
    if (driverController.getShoot()) {

      lineUpTowardsTargetWithDriver.loop();
      shooter.shoot();
    } else {

      drive.move(forward, strafe, rotation, angle, isFieldOriented);
      sucker.suckOrBlow(driverController.getIntake() - driverController.getOuttake());
    }

    //Sucker Release Deploy
    if (driverController.getIntakeDeploy()) {

      sucker.deploy();
    } else if (driverController.getIntakeRetract()) {

      sucker.retract();
    }
    
    //Start/stop shoot
    if (driverController.getShootPressed()) {

      sucker.suckOrBlow(-0.5);
      lineUpTowardsTargetWithDriver.start();
    } else if (driverController.getShootReleased()) {

      shooter.stop();
      sucker.suckOrBlow(0);
      lineUpTowardsTargetWithDriver.stop();
    }

    //Climber
    if (driverController.getUnlockClimb()) {

      climber.releaseLock();
    } else if (driverController.getRaiseMasterArm()) {

      climber.raiseMasterArm();
    } else if (driverController.getCollapseMasterArm()) {

      climber.collapseMasterArm();
      //pneumatics.stopCompressor();
    }

    //Winch for climber
    if (driverController.getWinchSafety()) {

      climber.setWinchSpeed(driverController.getWinchSpeed());
    } else {

      climber.setWinchSpeed(0);
    }

    climber.setTroller(driverController.getTroller());

    navX.loop();
    vision.loop();

    //Pizza Wheel 3-5 spins
    /**if(driverController.getSpinnerDeploy()) {
      spinner.deploySpinner();
    }
    if(driverController.getSpin()) {
      spinner.runRotation();
    }
    else {
      spinner.changes = 0;
    }

    //Pizza Wheel Color
    if(driverController.getColor()){
      spinner.runColor();
    }*/
  }


  @Override
  public void testInit() {
  }


  @Override
  public void testPeriodic() {
  }
}