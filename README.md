![alt text](https://i0.wp.com/www.mattawanwiredcats.org/wp-content/uploads/2019/11/wiredlogo.png?zoom=1&fit=100%2C532)
  
    
# MR. MEGA MAID

![alt text](GithubLogo.jpg)


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


### Driver Controls

Main Controller

|Button or Axis | Function                                           |
|-------------------|------------------------------------------------|
|Left Joystick      | Forward / Backwards, Strafe Left / Strafe Right|
|Right Joystick     | Rotate Left / Rotate Right                     |
|Left Trigger       |                                                |
|Right Trigger      |                                                |
|Left Stick Button  |                                                |
|Right Stick Button |                                                |
|Left Bumper        |                                                |
|Right Bumper       | Robot oriented                                 |
|Select Button      |                                                |
|Start Button       |                                                |
|Y Button           | Reset Yaw                                      |
|X Button           |                                                |
|B Button           |                                                |
|A Button           | Stay Straight                                  |
|D Pad              |                                                |

Aux Controller

|Button or Axis | Function |
|---------------|----------|
|Left Joystick      | Winch                        |
|Right Joystick     | Troller                      |
|Left Trigger       | Outtake                      |
|Right Trigger      | Intake                       |
|Left Stick Button  | Winch Enable                 |
|Right Stick Button |                              |
|Left Bumper        | Deploy Intake                |
|Right Bumper       | Retract Intake               |
|Select Button      | Climb Lock Dissengage        |
|Start Button       | Climb Arm Deploy/Collapse    |
|Y Button           |                              |
|X Button           | Shoot                        |
|B Button           | Spin                         |
|A Button           | Spinner Deploy               |
|D Pad              |                              |
