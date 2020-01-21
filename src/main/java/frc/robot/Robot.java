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
import frc.robot.subsytems.Drive;
import frc.robot.subsytems.Shooter;
import frc.robot.subsytems.Vision;
import frc.robot.auto.pathfinders.PathfinderCore;

public class Robot extends TimedRobot {

  DriverController driverController;
  Dashboard dashboard;

  Vision vision;
  Shooter shooter;
<<<<<<< HEAD
=======
  Dashboard dashboard;

  Spark shooterFeeder = new Spark(0);
  CANSparkMax shooterFlywheel = new CANSparkMax(1, MotorType.kBrushless);

  PathfinderCore pathfinder;

  Drive driveBase;
>>>>>>> c99f055fcecccb45dfb1d9f92423e5117458335d

  @Override
  public void robotInit() {
    driverController = new DriverController();
    dashboard = new Dashboard();

    vision = new Vision();
    shooter = new Shooter();
<<<<<<< HEAD
=======
    dashboard = new Dashboard();

    driveBase = new Drive();
>>>>>>> c99f055fcecccb45dfb1d9f92423e5117458335d

    //Pathfinder needs Drive, so put it after
    pathfinder = new PathfinderCore(driveBase);

    driverController.init();
    vision.init();
    shooter.init();
    dashboard.init();
  }

  @Override
  public void autonomousInit() {

    pathfinder.config();
  }

  @Override
  public void autonomousPeriodic() {
<<<<<<< HEAD
=======
    /*
    shooterFeeder.set(-1);
    shooterFlywheel.getPIDController().setReference(-6000, ControlType.kVelocity);*/

    pathfinder.runpathfinder();
>>>>>>> c99f055fcecccb45dfb1d9f92423e5117458335d
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    
    driveBase.teleopDrive();
    
    vision.loop();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
