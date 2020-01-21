/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.libs.swerve.SwerveDrive;
import frc.libs.swerve.WheelDrive;
import frc.robot.subsytems.Shooter;
import frc.robot.subsytems.Vision;
import frc.robot.auto.pathfinders.PathfinderCore;

public class Robot extends TimedRobot {

  DriverController driverController;

  Vision vision;
  Shooter shooter;

  SwerveDrive swerve;
  WheelDrive backRight;
  WheelDrive backLeft;
  WheelDrive frontRight;
  WheelDrive frontLeft;

  PathfinderCore pathfinder;

  @Override
  public void robotInit() {
    driverController = new DriverController();

    vision = new Vision();
    shooter = new Shooter();

    pathfinder = new PathfinderCore(backRight, backLeft, frontRight, frontLeft);

    backRight = new WheelDrive(Constants.DRIVE_BACK_RIGHT_AZIMUTH_ID, Constants.DRIVE_BACK_RIGHT_SPEED_ID, Constants.BR_AZIMUTH_ENCODER_ID, 
    Constants.BR_P, Constants.BR_I, Constants.BR_D);

    swerve = new SwerveDrive(backRight, backLeft, frontRight, frontLeft);

    driverController.init();
    vision.init();
    shooter.init();
  }

  @Override
  public void autonomousInit() {

    pathfinder.config();
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
