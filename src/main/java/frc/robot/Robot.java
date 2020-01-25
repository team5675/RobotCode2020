/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auto.actions.Action;
import frc.robot.auto.actions.LineUpWithTarget;

import frc.robot.auto.pathfinders.PathfinderCore;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Sucker;


public class Robot extends TimedRobot {

  DriverController driverController;
  Dashboard dashboard;

  Vision vision;
  Sucker sucker;
 // Shooter shooter;
  Drive drive;
  NavX navX;

  PathfinderCore pathfinder;
  Action action;

  Action lineUpWithTarget;

  @Override
  public void robotInit() {

    driverController = new DriverController();

    dashboard = new Dashboard();

    navX = new NavX();

    vision = new Vision();
    sucker = new Sucker();
    //shooter = new Shooter();
    drive = new Drive();

    action = new Action();

    //Pathfinder needs Drive, so put it after
    pathfinder = new PathfinderCore(drive);

    navX.init();
    driverController.init();
    vision.init();
    sucker.init();
   // shooter.init();
    dashboard.init();
    drive.init();
  }

  @Override
  public void autonomousInit() {

    pathfinder.config();
  }

  @Override
  public void autonomousPeriodic() {

    pathfinder.runpathfinder();
  }

  @Override
  public void teleopInit() {
    lineUpWithTarget = new LineUpWithTarget();
  }

  @Override
  public void teleopPeriodic() {

    drive.move(driverController.getForward(), driverController.getStrafe(), driverController.getRotation(), navX.getAngle(), false);
    
    if(driverController.getA()) {

      navX.resetYaw();
    }

    if (driverController.getLineUp()) {
      lineUpWithTarget.run();
    } else {
      drive.move(driverController.getForward(), driverController.getStrafe(), driverController.getRotation(), navX.getAngle() - 45, driverController.isFieldOriented());
    }

    vision.loop();
    sucker.run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
