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
import frc.robot.auto.Pathfinderold;
import frc.robot.auto.SwerveReturnData;
import frc.robot.auto.Waypoint;
import frc.robot.auto.PathWeezer;
import frc.robot.auto.actions.Action;
import frc.robot.auto.actions.LineUpTowardsTargetWithDriver;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Sucker;


public class Robot extends TimedRobot {

  DriverController driverController;
  Dashboard        dashboard;

  Vision           vision;
  Sucker           sucker;
  Shooter          shooter;
  //Drive            drive;
  NavX             navX;
  Pneumatics       pneumatics;
  Climber          climber;
  PathWeezer       pathWeezer;
  Pathfinderold    pathFinderOld;

  Pathfinder       pathfinder;
  ModeRunner       modeRunner;
  ActionRunner     actionRunner;
  Action           lineUpTowardsTargetWithDriver;
  AutoChooser      autoChooser;


  @Override
  public void robotInit() {

    driverController = DriverController.getInstance();
    dashboard        = Dashboard.getInstance();
    
    //drive            = Drive.getInstance();
    navX             = NavX.getInstance();
    vision           = Vision.getInstance();
    shooter          = Shooter.getInstance();
    sucker           = Sucker.getInstance();
    pneumatics       = Pneumatics.getInstance();
    climber          = Climber.getInstance();
    pathWeezer       = PathWeezer.getInstance();

    actionRunner     = ActionRunner.getInstance();
    pathFinderOld    = Pathfinderold.getInstance();
    pathfinder       = new Pathfinder(0.25, 0.003, 0, 1); 
    autoChooser      = AutoChooser.getInstance();

    Waypoint[] _newTrajectory = new Waypoint[] {
      new Waypoint(2.5, 7.5, 0),
      new Waypoint(5, 7.5, 0),
      new Waypoint(7.5, 12.5, 0),
      new Waypoint(9.5, 5, 0),
      new Waypoint(12.5, 2.5, 0),
      new Waypoint(14.7, 5, 0),
      new Waypoint(15, 12.5, 0),
      new Waypoint(15.25, 7.5, 0),
      new Waypoint(16.125, 2.5, 0),
      new Waypoint(19.375, 5, 0),
      new Waypoint(21.875, 7.5, 0),
      new Waypoint(22.5, 12.5, 0),
      new Waypoint(27.5, 7.5, 0)
    };

    pathfinder.translate(2.5, 7.5, _newTrajectory);
  }

  
  @Override
  public void disabledPeriodic() {
    
    //run disabled code here like leds and stuff
  }


  @Override
  public void autonomousInit() {

    navX.resetYaw();
    
    //modeRunner = new ModeRunner(autoChooser.getMode());

    //actionRunner.start();
    //modeRunner.start();
  }


  @Override
  public void autonomousPeriodic() {

    if(pathWeezer.loopUntilTrajectory()) {
      pathFinderOld.runPath();
    }

    //vision.loop();
    //actionRunner.loop();
    /*
    SwerveReturnData[] _motorSpeed = pathfinder.loop(
      drive.getFrontLeft().getAzimuth() - 2.13,
      drive.getFrontRight().getAzimuth() - 2.62,
      drive.getBackLeft().getAzimuth() - 2.47,
      drive.getBackRight().getAzimuth() - 2.58,
      drive.getFrontLeft().getDrivePosition() / 7.64,
      (drive.getFrontRight().getDrivePosition() / 7.64) * -1,
      drive.getBackLeft().getDrivePosition() / 7.64,
      drive.getBackRight().getDrivePosition() / 7.64
    );

    drive.getFrontLeft().setAzimuth(_motorSpeed[0].getAzimuthSpeed());
    drive.getFrontRight().setAzimuth(_motorSpeed[1].getAzimuthSpeed());
    drive.getBackLeft().setAzimuth(_motorSpeed[2].getAzimuthSpeed());
    drive.getBackRight().setAzimuth(_motorSpeed[3].getAzimuthSpeed());
    drive.getFrontLeft().setDrive(_motorSpeed[0].getDriveSpeed());
    drive.getFrontRight().setDrive(_motorSpeed[1].getDriveSpeed() * -1);
    drive.getBackLeft().setDrive(_motorSpeed[2].getDriveSpeed());
    drive.getBackRight().setDrive(_motorSpeed[3].getDriveSpeed());
    */
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

    shooter.run();
    //Shoot else run intake and drive
    if (driverController.getShoot()) {

      //lineUpTowardsTargetWithDriver.loop();
      shooter.shoot();
    } else {

      //drive.move(forward, strafe, rotation, angle, isFieldOriented);
      //sucker.suckOrBlow(driverController.getIntake() - driverController.getOuttake());
    }

    //System.out.println("Front Right: " + drive.getFrontRight().getAzimuth());
    //System.out.println("Front Left: " + drive.getFrontLeft().getAzimuth());
    //System.out.println("Back Right: " + drive.getBackRight().getAzimuth());
    //System.out.println("Back Left: " + drive.getBackLeft().getAzimuth());

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
      //sucker.suckOrBlow(0);
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


    
  //sucker.deploy();
  //sucker.suckOrBlow(-1);


  }


  @Override
  public void testInit() {
  }


  @Override
  public void testPeriodic() {
  }
}