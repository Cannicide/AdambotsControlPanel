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

    public WPI_TalonSRX arm1;
    public WPI_TalonSRX arm2;
    public WPI_TalonSRX arm1_wheel;
    public WPI_TalonSRX arm2_wheel;

    public Climb() {
        super();

        arm1 = new WPI_TalonSRX(Constants.CLIMB_ARM1_PORT);
        arm2 = new WPI_TalonSRX(Constants.CLIMB_ARM2_PORT);
        arm1_wheel = new WPI_TalonSRX(Constants.ARM1_WHEEL_PORT);
        arm2_wheel = new WPI_TalonSRX(Constants.ARM2_WHEEL_PORT);
    }

    private double liftSpeed(double elevateSpeed) {
        return Math.min(elevateSpeed, Constants.MAX_LIFT_SPEED);
    }

    private double shiftSpeed(double glideSpeed) {
        return Math.min(glideSpeed, Constants.MAX_GLIDE_SPEED);
    }

    //Rise/lower the elevator arms
    public void elevate(double arm1Speed, double arm2Speed, boolean autoLevel) {
        arm1Speed = liftSpeed(arm1Speed);
        arm2Speed = liftSpeed(arm2Speed);
        arm1.set(arm1Speed);

        //When autolevel enabled, both arms should rise at an equal speed, to an equal height, synchronously
        if (autoLevel) {
            arm2.set(arm1Speed);
        }
        else {
            arm2.set(arm2Speed);
        }
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