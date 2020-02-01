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
import frc.robot.auto.actions.Action;
import frc.robot.auto.actions.LineUpTowardsTargetWithDriver;
import frc.robot.auto.modes.ShootThreeBalls;
import frc.robot.auto.pathfinders.PathfinderCore;

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
  Spinner          spinner;

  ModeRunner       modeRunner;
  PathfinderCore   pathfinder;

  ActionRunner     actionRunner;
  Action           action;
  Action           lineUpTowardsTargetWithDriver;
  Action           lineUpWithTarget;

  Spark feeder = new Spark(0);
  CANSparkMax left = new CANSparkMax(10, MotorType.kBrushless);
  CANSparkMax right = new CANSparkMax(12, MotorType.kBrushless);


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

    actionRunner     = ActionRunner.getInstance();
    pathfinder       = PathfinderCore.getInstance();
  }


  @Override
  public void autonomousInit() {

    modeRunner = new ModeRunner(new ShootThreeBalls());
    modeRunner.start();
    System.out.println("haha poop");
  }


  @Override
  public void autonomousPeriodic() {

    actionRunner.loop();
   // pathfinder.runpathfinder();
    pathfinder.runpathfinder(pathfinder.config("DEFAULT"));
  }


  @Override
  public void teleopInit() {

    //actionRunner.forceStop();

    lineUpTowardsTargetWithDriver = new LineUpTowardsTargetWithDriver();
    //lineUpWithTarget = new LineUpWithTarget();
  }


  @Override
  public void teleopPeriodic() {
    
    //Reset Yaw on NavX
    if(driverController.getA()) {

      navX.resetYaw();
    }

    //Tele-op auto functions or manual drive
    if (driverController.getLineUp()) {

      lineUpTowardsTargetWithDriver.loop();
    } else {

      drive.move(driverController.getForward(), driverController.getStrafe(), driverController.getRotation(), navX.getAngle() - 45, driverController.isFieldOriented());
    }

    //vision.loop();
    //sucker.run();
  }


  @Override
  public void testInit() {
  }


  @Override
  public void testPeriodic() {
    feeder.set(-0.3);
    left.getPIDController().setReference(-3000, ControlType.kVelocity);
    right.getPIDController().setReference(3000, ControlType.kVelocity);
    //left.set(1);
    //right.set(1);
  }
}