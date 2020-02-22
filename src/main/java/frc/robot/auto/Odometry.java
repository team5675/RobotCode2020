package frc.robot.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;

import frc.robot.subsystems.NavX;
import frc.libs.swerve.WheelDrive;

public class Odometry {

    Translation2d backRightLocation;
    Translation2d backLeftLocation;
    Translation2d frontRightLocation;
    Translation2d frontLeftLocation;

    SwerveModuleState backRight;
    SwerveModuleState backLeft;
    SwerveModuleState frontRight;
    SwerveModuleState frontLeft;

    SwerveDriveKinematics kinematics;

    SwerveDriveOdometry odometry;

    NavX navX;

    Rotation2d angleState;

    Pose2d robotState;


    public Odometry() {

        navX = NavX.getInstance();

        angleState = Rotation2d.fromDegrees(navX.getAngle());

        robotState = new Pose2d();

        backRightLocation = new Translation2d(-0.2694, -0.2694);
        backLeftLocation = new Translation2d(-0.2694, 0.2694);
        frontRightLocation = new Translation2d(0.2694, -0.2694);
        frontLeftLocation = new Translation2d(0.2694, 0.2694);

        kinematics = new SwerveDriveKinematics(backRightLocation, backLeftLocation, frontRightLocation, frontLeftLocation);

        odometry = new SwerveDriveOdometry(kinematics, angleState);

        backRight = new SwerveModuleState();
        backLeft = new SwerveModuleState();
        frontRight = new SwerveModuleState();
        frontLeft = new SwerveModuleState();
    }

    public Pose2d updatePose() {

        robotState = odometry.update(angleState, backRight, backLeft, frontRight, frontLeft);

        return robotState;
    }

    public void resetOdometry(Pose2d pose) {

        

    }
}