/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import frc.robot.auto.actions.Action;

/**
 * Add your docs here.
 */
public class ActionRunner {

    static ActionRunner instance;

    static Action currentAction;


    public void run(Action action) {

        currentAction = action;

        action.start();

        while (currentAction != null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        action.stop();
    }


    public void loop() {

        if (currentAction != null) {
            
            if (currentAction.loop() == false) {

                currentAction = null;
            };
        }
    }
    

    public static ActionRunner getInstance() {

        if (instance == null) {

            instance = new ActionRunner();
        }

        return instance;
    }
}