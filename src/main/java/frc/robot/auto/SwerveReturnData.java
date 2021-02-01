package frc.robot.auto;

public class SwerveReturnData
{

    public double AzimuthSpeed = 0;
    public double DriveSpeed = 0;


    SwerveReturnData(double azimuthSpeed, double driveSpeed)
    {

        AzimuthSpeed = azimuthSpeed;
        DriveSpeed = driveSpeed;
    }

    public double getAzimuthSpeed()
    {

        return AzimuthSpeed;
    }

    public double getDriveSpeed()
    {

        return DriveSpeed;
    }
}