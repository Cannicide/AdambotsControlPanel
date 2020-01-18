/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /**
   * Creates a new Intake.
   */

  public WPI_TalonSRX IntakeMotor;

  public IntakeSubsystem() {
    super();

    IntakeMotor = new WPI_TalonSRX(Constants.INTAKE_MOTOR_PORT);

  }

  public void intake(){ 
    IntakeMotor.set(ControlMode.PercentOutput, Constants.INTAKE_SPEED);
  } 
  public void outtake(){
    IntakeMotor.set(ControlMode.PercentOutput, Constants.OUTTAKE_SPEED);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
