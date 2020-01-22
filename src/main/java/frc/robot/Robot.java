/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

//import frc.robot.auto.pathfinders.PathfinderCore;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;


public class Robot extends TimedRobot {

  DriverController driverController;
  Dashboard dashboard;

  Vision vision;
 // Shooter shooter;
  Drive drive;
  NavX navX;

  //PathfinderCore pathfinder;


  @Override
  public void robotInit() {
    driverController = new DriverController();
    dashboard = new Dashboard();

    vision = new Vision();
    //shooter = new Shooter();
    drive = new Drive();

    //Pathfinder needs Drive, so put it after
    //pathfinder = new PathfinderCore(driveBase);

    driverController.init();
    vision.init();
   // shooter.init();
    dashboard.init();
  }

  @Override
  public void autonomousInit() {

    //pathfinder.config();
  }

  @Override
  public void autonomousPeriodic() {

    //pathfinder.runpathfinder();
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {

    System.out.println(navX.getAngle());
    drive.move(driverController.getForward(), driverController.getStrafe(), driverController.getRotation(), navX.getAngle(), false);
    
   // vision.loop();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
