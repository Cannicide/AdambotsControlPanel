/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Climb extends SubsystemBase {
    /**
     * Creates a new Climb/Elevator Subsystem
     */

    public WPI_TalonSRX elevator;


    public Climb() {
        super();

        elevator = new WPI_TalonSRX(Constants.CLIMB_ARM1_PORT);
    }

    private double liftSpeed(double elevateSpeed) {
        return Math.min(elevateSpeed, Constants.MAX_LIFT_SPEED);
    }

    private double shiftSpeed(double glideSpeed) {
        return Math.min(glideSpeed, Constants.MAX_GLIDE_SPEED);
    }

    //Rise/lower the elevator arms
    public void elevate(double elevatorSpeed, double arm2Speed, boolean autoLevel) {
        elevatorSpeed = liftSpeed(elevatorSpeed);
        elevator.set(elevatorSpeed);
    }

   public boolean atMaxHeight() {
       ControlMode height = ControlMode.Position;
       return false;
   }

    //Move horizontally along the Shield Generator bar
    public void glide(double glideSpeed) {
        double shiftSpeed = shiftSpeed(glideSpeed);

    }


}