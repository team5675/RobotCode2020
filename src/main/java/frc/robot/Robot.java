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
import frc.robot.subsystems.Pneumatics;
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
  Pneumatics       pneumatics;

  Pathfinder       pathfinder;
  ModeRunner       modeRunner;
  ActionRunner     actionRunner;
  Action           lineUpTowardsTargetWithDriver;
  AutoChooser      autoChooser;

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
    pneumatics       = Pneumatics.getInstance();

    actionRunner     = ActionRunner.getInstance();
    pathfinder       = Pathfinder.getInstance();
    autoChooser      = AutoChooser.getInstance();
  }

  
  @Override
  public void disabledPeriodic() {
    //run disabled code here like leds and stuff

    if (driverController.getRunCompressor()) {
      
      pneumatics.runCompressor();
    }
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
  }


  @Override
  public void teleopPeriodic() {

   //System.out.format("BR: %f \nBL: %f \nFR: %f \nFL: %f\n", drive.getBackRight().getAzimuth(), drive.getBackLeft().getAzimuth(), drive.getFrontRight().getAzimuth(), drive.getFrontLeft().getAzimuth());
    
    //Reset Yaw on NavX
    if(driverController.getResetYaw()) {

      navX.resetYaw();
    }

    //sucker.run();

    //Tele-op auto functions or manual drive
    if (driverController.getLineUp()) {

      lineUpTowardsTargetWithDriver.loop();
    } else if (driverController.getStayStraight()) {

      drive.move(driverController.getForward(), driverController.getStrafe(), navX.getAngle() * -0.01, navX.getAngle(), driverController.isFieldOriented());
    } else {

      drive.move(driverController.getForward(), driverController.getStrafe(), driverController.getRotation(), navX.getAngle(), driverController.isFieldOriented());
    }

    //Start/stop vision assist driving
    if (driverController.getOnLineUpPressed()) {

      lineUpTowardsTargetWithDriver.start();
    } else if (driverController.getOnLineUpReleased()) {

      lineUpTowardsTargetWithDriver.stop();
    }
    
    //Sucker
    sucker.suckOrBlow(driverController.getIntake() - driverController.getOuttake());
    
    /** 
    if(driverController.getSpinnerDeploy()) {
      spinner.deploySpinner();      
    }

    if(driverController.getSpin()) {
      spinner.runRotation();
    }
    else {
      spinner.changes = 0;
    }

    if(driverController.getColor()){
      spinner.runColor();
    }*/

    navX.loop();
    vision.loop();
  }


  @Override
  public void testInit() {
  }


  @Override
  public void testPeriodic() {
    //feeder.set(1);
    //left.getPIDController().setReference(-3000, ControlType.kVelocity);
    //right.getPIDController().setReference(-3000, ControlType.kVelocity);
    //left.set(-0.75);
    //right.set(-0.75);
  }
}