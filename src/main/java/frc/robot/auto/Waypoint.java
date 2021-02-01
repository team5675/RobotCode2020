// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

/** Add your docs here. */
public class Waypoint {

    private double X;
    private double Y;
    private double Angle;


    public Waypoint(double x, double y, double angle)
    {

        X = x;
        Y = y;
        Angle = angle;
    }

    double getX()
    {

        return X;
    }

    double getY()
    {

        return Y;
    }

    double getAngle()
    {
        
        return Angle;
    }
}