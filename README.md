![alt text](https://i0.wp.com/www.mattawanwiredcats.org/wp-content/uploads/2019/11/wiredlogo.png?zoom=1&fit=1080%2C532)
  
    
# -NAME OF ROBOT HERE-

![alt text](https://i0.wp.com/www.mattawanwiredcats.org/wp-content/uploads/2018/05/18Apr_Robotics-011-min.jpg)


### Electrical Control Structure


Drivetrain Subsystem


|CAN ID     |Motor Type |Function      |
|-----------|:----------|-------------:|
|1          |Neo        |Swerve Drive  |
|2          |Neo        |Swerve Azimuth|
|3          |Neo        |Swerve Drive  |
|4          |Neo        |Swerve Azimuth|
|5          |Neo        |Swerve Drive  |
|6          |Neo        |Swerve Azimuth|
|7          |Neo        |Swerve Drive  |
|8          |Neo        |Swerve Azimuth|


Launcher Subsystem


|CAN ID     |Motor Type |Function      |
|-----------|:----------|-------------:|
|9          |Neo        |Launcher 1    |
|10         |Neo        |Launcher 2    |


|PWM ID     |Motor Type |Function      |
|-----------|:----------|-------------:|
|0          |775        |Feeder        |


Indexer / Intake Subsystem

|PWM ID     |Motor Type |Function      |
|-----------|:----------|-------------:|
|1          |BAG        |Indexer Rotate|
|2          |BAG        |Intake        |


Climber Subsystem


|PWM ID     |Motor Type |Function      |
|-----------|:----------|-------------:|
|3          |775        |Winch         |
|4          |Snowblower |Balancer      |


Spinner Subsystem


|PWM ID     |Motor Type |Function      |
|-----------|:----------|-------------:|
|5          |JE         |Wheel spinner |


### Pneumatic Control Structure


Intake Subsystem


|SOLENOID ID|Function   |
|-----------|:----------|
|0-1        |Intake arms|


Climber Subsystem


|SOLENOID ID|Function   |
|-----------|:----------|
|2-3        |Deploy high|
|4-5        |deploy low |


Spinner Subsystem


|SOLENOID ID|Function   |
|-----------|:----------|
|6-7        |deploy spin|


Main Controller

|Button or Axis|Function|
|---|---|
|Left Joystick|Forward/Backwards, Strafe Left/Strafe Right|

