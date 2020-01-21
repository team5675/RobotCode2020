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
import frc.libs.swerve.SwerveDrive;
import frc.libs.swerve.WheelDrive;
import frc.robot.subsytems.Shooter;
import frc.robot.subsytems.Vision;

public class Robot extends TimedRobot {

  DriverController driverController;
  Dashboard dashboard;

  Vision vision;
  Shooter shooter;

  @Override
  public void robotInit() {
    driverController = new DriverController();
    dashboard = new Dashboard();

    vision = new Vision();
    shooter = new Shooter();

    swerve = new SwerveDrive(backRight, backLeft, frontRight, frontLeft);

    driverController.init();
    vision.init();
    shooter.init();
    dashboard.init();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    vision.loop();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
