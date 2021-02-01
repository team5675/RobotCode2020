/***
 *     /$$$$$$$   /$$$$$$  /$$$$$$$$ /$$$$$$$ 
 *    | $$____/  /$$__  $$|_____ $$/| $$____/ 
 *    | $$      | $$  \__/     /$$/ | $$      
 *    | $$$$$$$ | $$$$$$$     /$$/  | $$$$$$$ 
 *    |_____  $$| $$__  $$   /$$/   |_____  $$
 *     /$$  \ $$| $$  \ $$  /$$/     /$$  \ $$
 *    |  $$$$$$/|  $$$$$$/ /$$/     |  $$$$$$/
 *     \______/  \______/ |__/       \______/ 
 *                                            
 *                                            
 *                                            
 */

package frc.robot.auto;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pathfinder {

    //CONSTANTS
    private double RESOLUTION;
    private double AZIMUTH_ROTATE_P;
    private double SLOWDOWN_P;
    private double SLOW_DOWN_START_POINT;
    private double MAX_MOTOR_SPEED = 0.25;

    double CurrentSplineLength = 0;
    double CurrentSplineDataAngle[];
    double CurrentSplineDataLength[];
    double CurrentSplineDataStatus[];
    int TotalNodes = 0;
    boolean RunningPath = false;

    int[] LastNode = new int[4];


    SplineInterpolator inter = new SplineInterpolator();



    public Pathfinder(double resolution, double azmiuth_rotate_p, double slowdown_p, double slowDownStartPoint)
    {

        RESOLUTION = resolution;
        AZIMUTH_ROTATE_P = azmiuth_rotate_p;
        SLOWDOWN_P = slowdown_p;
        SLOW_DOWN_START_POINT = slowDownStartPoint;
    }


    public void translate(double offsetX, double offsetY, Waypoint[] points)
    {
        double[] _xPoints = new double[points.length];
        double[] _yPoints = new double[points.length];
        double[] _anglePoints = new double[points.length];

        for (int i = 0; i < points.length; i++)
        {

            _xPoints[i] = points[i].getX() - offsetX;
            _yPoints[i] = points[i].getY() - offsetY;
            _anglePoints[i] = points[i].getAngle();
        }

        PolynomialSplineFunction _splineResult = inter.interpolate(_xPoints, _yPoints);

        int _arraySize = (int) Math.ceil(_xPoints[points.length - 1] / RESOLUTION);
        double _lastYResult = 0;
        double _splineLength = 0;

        System.out.println("[PATHFINDER] " + "Array Size: " + _arraySize);

        CurrentSplineDataAngle = new double[_arraySize];
        CurrentSplineDataLength = new double[_arraySize];

        for (int i = 0; i < _arraySize; i++)
        {

            double __yResult = _splineResult.value(i * RESOLUTION);
            double __hyp = Math.sqrt(Math.pow(RESOLUTION, 2) + Math.pow(__yResult - _lastYResult, 2));
            double __angle = Math.toDegrees(Math.atan((__yResult - _lastYResult) / RESOLUTION));

            _splineLength = _splineLength + __hyp;
            _lastYResult = __yResult;

            CurrentSplineLength = _splineLength;
            CurrentSplineDataAngle[i] = __angle;
            CurrentSplineDataLength[i] = _splineLength;

            System.out.println("[PATHFINDER] Node: " + i + " X: " + i * RESOLUTION + " Y: " + __yResult + " Angle: " + __angle + " Length: " + _splineLength);
            
        }

        LastNode = new int[4];
        TotalNodes = _arraySize;

        RunningPath = true;

        //while (RunningPath == true){} //wait for path execution to be done
    }


    //give current rotation and distance values for all modules, return motor speed for rotate and motor speed for drive
    public SwerveReturnData[] loop(
        double eFL_Angle,
        double eFR_Angle,
        double eBL_Angle,
        double eBR_Angle,

        double eFL_Distance,
        double eFR_Distance,
        double eBL_Distance,
        double eBR_Distance
    )
    {

        SwerveReturnData[] _returnData = new SwerveReturnData[4];
        _returnData[0] = new SwerveReturnData(0, 0);
        _returnData[1] = new SwerveReturnData(0, 0);
        _returnData[2] = new SwerveReturnData(0, 0);
        _returnData[3] = new SwerveReturnData(0, 0);

        if (RunningPath)
        {
            
            double[][] _swerveData = new double[4][2]; //4 modules data sets (azimuth angle, current distance traveled, module offset)
            _swerveData[0][0] = eFL_Angle;
            _swerveData[1][0] = eFR_Angle;
            _swerveData[2][0] = eBL_Angle;
            _swerveData[3][0] = eBR_Angle;
            _swerveData[0][1] = eFL_Distance;
            _swerveData[1][1] = eFR_Distance;
            _swerveData[2][1] = eBL_Distance;
            _swerveData[3][1] = eBR_Distance;
            int modulesDone = 0;

            for (int moduleID = 0; moduleID < 4; moduleID++)
            {
                
                for (int nodeID = LastNode[moduleID]; nodeID < TotalNodes; nodeID++)
                {

                    //this gives us our last node we proccssed, and makes sure we didn't go past it
                    if (CurrentSplineDataLength[nodeID] > _swerveData[moduleID][1]) //if this node's spline length is greater than the module encoder value
                    {

                        if (CurrentSplineLength > _swerveData[moduleID][1]) //check to see if we have completed the path
                        {

                            LastNode[moduleID] = nodeID;

                            double _azimuthAngle = _swerveData[moduleID][0] * 72;

                            if (_azimuthAngle > 180)
                            {

                                _azimuthAngle = _azimuthAngle - 180;
                            }

                            double _angleError = CurrentSplineDataAngle[nodeID] - _azimuthAngle;
                            double _azimuthSpeed = _angleError * AZIMUTH_ROTATE_P;

                            SmartDashboard.putNumber("Current Azimuth Angle " + moduleID, _azimuthAngle);
                            SmartDashboard.putNumber("Target Angle " + moduleID, CurrentSplineDataAngle[nodeID]);
                            SmartDashboard.putNumber("Angle Error " + moduleID, _angleError);

                            _returnData[moduleID].AzimuthSpeed = _azimuthSpeed;

                            if (CurrentSplineLength - _swerveData[moduleID][1] < SLOW_DOWN_START_POINT) //if we are past the start to slow down point, else go full power
                            {

                                double __slowDownError = CurrentSplineLength - _swerveData[moduleID][1];
                                _returnData[moduleID].DriveSpeed = __slowDownError * SLOWDOWN_P * MAX_MOTOR_SPEED;
                            }
                            else
                            {

                                _returnData[moduleID].DriveSpeed = MAX_MOTOR_SPEED;
                            }
                        }
                        else
                        {

                            _returnData[moduleID].AzimuthSpeed = 0;
                            _returnData[moduleID].DriveSpeed = 0;
                            System.out.println(moduleID + " completed it's path");
                            modulesDone++;
                        }

                        SmartDashboard.putNumber("Azimuth Speed " + moduleID, _returnData[moduleID].AzimuthSpeed);
                        SmartDashboard.putNumber("Drive Speed " + moduleID, _returnData[moduleID].DriveSpeed);
                        SmartDashboard.putNumber("Current Distance Traveled " + moduleID, _swerveData[moduleID][1]);

                        break;
                    }
                }
            }

            if (modulesDone == 4) //if all 4 modules completed the path, stop running the system
            {

                RunningPath = false;
            }
        }

        return _returnData;
    }
}