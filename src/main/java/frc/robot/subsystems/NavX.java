package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class NavX {

    static NavX instance;
    
    AHRS gyro;

    //fix this stuff later
    NetworkTableEntry gyroEntry = Shuffleboard.getTab("Main").add("Gyro", 0).withWidget(BuiltInWidgets.kGyro).getEntry();

    public NavX() {

        try {

            gyro = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex) {

			System.out.println("Error instantiating navX-MXP:  " + ex.getMessage());
		}
    }

    public double getAngle() {

        return gyro.getAngle();
    }

    public void resetYaw() {

        gyro.zeroYaw();
    }


    public void loop() {

    }


    public static NavX getInstance() {
        
        if (instance == null) {
            
            instance = new NavX();
        }

        return instance;
    }
}