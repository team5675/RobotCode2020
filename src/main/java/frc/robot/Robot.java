/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auto.actions.Action;
import frc.robot.auto.actions.LineUpTowardsTargetWithDriver;
import frc.robot.auto.actions.LineUpWithTarget;

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

  Vision  vision;
  Sucker  sucker;
  Shooter shooter;
  Drive   drive;
  NavX    navX;
  Spinner spinner;

  PathfinderCore pathfinder;
  Action         action;
  Action         lineUpTowardsTargetWithDriver;
  Action         lineUpWithTarget;

  CANSparkMax shooter1;
  CANSparkMax shooter2;
  Spark shooterIntake;

  @Override
  public void robotInit() {

    shooter1 = new CANSparkMax(14, MotorType.kBrushless);
    shooter2 = new CANSparkMax(15, MotorType.kBrushless);

    shooterIntake = new Spark(0);

    driverController = DriverController.getInstance();
    dashboard        = Dashboard.getInstance();
    
    drive            = Drive.getInstance();
    navX             = NavX.getInstance();
    vision           = Vision.getInstance();
    shooter          = Shooter.getInstance();
    sucker           = Sucker.getInstance();
    spinner          = Spinner.getInstance();
    pathfinder       = PathfinderCore.getInstance();
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

    lineUpTowardsTargetWithDriver = new LineUpTowardsTargetWithDriver();
    //lineUpWithTarget = new LineUpWithTarget();
  }

  @Override
  public void teleopPeriodic() {


    shooter1.set(-1);
    shooter2.set(1);
    shooterIntake.set(1);
    
    if(driverController.getA()) {

      navX.resetYaw();
    }

    if (driverController.getLineUp()) {
      lineUpTowardsTargetWithDriver.run();
      //lineUpWithTarget.run(0, 0);
    } else {
      drive.move(driverController.getForward(), driverController.getStrafe(), driverController.getRotation(), navX.getAngle() - 45, driverController.isFieldOriented());
    }

    vision.loop();
    //sucker.run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
