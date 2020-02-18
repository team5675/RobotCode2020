/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auto.ActionRunner;
import frc.robot.auto.ModeRunner;
import frc.robot.auto.Pathfinder;
import frc.robot.auto.actions.Action;
import frc.robot.auto.actions.LineUpTowardsTargetWithDriver;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
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
  //Spinner          spinner;

  Pathfinder       pathfinder;
  ModeRunner       modeRunner;
  ActionRunner     actionRunner;
  Action           action;
  Action           lineUpTowardsTargetWithDriver;
  Action           lineUpWithTarget;
  AutoChooser      autoChooser;

  // for testing
  //Spark feeder = new Spark(0);     11
  CANSparkMax left = new CANSparkMax(13, MotorType.kBrushed);
  CANSparkMax right = new CANSparkMax(12, MotorType.kBrushed);


  @Override
  public void robotInit() {

    driverController = DriverController.getInstance();
    dashboard        = Dashboard.getInstance();
    
    drive            = Drive.getInstance();
    navX             = NavX.getInstance();
    vision           = Vision.getInstance();
    shooter          = Shooter.getInstance();
    sucker           = Sucker.getInstance();
    //spinner          = Spinner.getInstance();

    actionRunner     = ActionRunner.getInstance();
    pathfinder       = Pathfinder.getInstance();
    autoChooser      = AutoChooser.getInstance();
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
    //lineUpWithTarget = new LineUpWithTarget();
  }


  @Override
  public void teleopPeriodic() {

   System.out.format("BR: %f \nBL: %f \nFR: %f \nFL: %f\n", drive.getBackRight().getAzimuth(), drive.getBackLeft().getAzimuth(), drive.getFrontRight().getAzimuth(), drive.getFrontLeft().getAzimuth());
    
    //Reset Yaw on NavX
    if(driverController.getA()) {

      navX.resetYaw();
    }

    //sucker.run();

    //Tele-op auto functions or manual drive
    if (driverController.getLineUp()) {

      lineUpTowardsTargetWithDriver.loop();
    } else {

      drive.move(driverController.getForward(), driverController.getStrafe(), driverController.getRotation(), navX.getAngle() - 90, driverController.isFieldOriented());
    }

    //Start/stop vision assist driving
    if (driverController.getOnLineUpPressed()) {

      lineUpTowardsTargetWithDriver.start();
    } else if (driverController.getOnLineUpReleased()) {

      lineUpTowardsTargetWithDriver.stop();
    }

    navX.loop();
    vision.loop();
    //sucker.run();
  }


  @Override
  public void testInit() {
  }


  @Override
  public void testPeriodic() {
    
    //feeder.set(-1);
    left.set(0.5);
    right.set(0.5);
    //left.getPIDController().setReference(-0.5, ControlType.kVelocity);
    //right.getPIDController().setReference(-0.5, ControlType.kVelocity);
  }
}