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

  Vision vision;
  Shooter shooter;
  Dashboard dashboard;

  Spark shooterFeeder = new Spark(0);
  CANSparkMax shooterFlywheel = new CANSparkMax(1, MotorType.kBrushless);

  SwerveDrive swerve;
  WheelDrive backRight;
  WheelDrive backLeft;
  WheelDrive frontRight;
  WheelDrive frontLeft;

  @Override
  public void robotInit() {
    driverController = new DriverController();

    vision = new Vision();
    shooter = new Shooter();
    dashboard = new Dashboard();

    backRight = new WheelDrive(Constants.DRIVE_BACK_RIGHT_AZIMUTH_ID, Constants.DRIVE_BACK_RIGHT_SPEED_ID, Constants.BR_AZIMUTH_ENCODER_ID, 
    Constants.BR_P, Constants.BR_I, Constants.BR_D);

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
    shooterFeeder.set(-1);
    shooterFlywheel.getPIDController().setReference(-6000, ControlType.kVelocity);
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
