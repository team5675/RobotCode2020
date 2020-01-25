package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;


public class NavX {

    AHRS gyro;

    public void init() {

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
}