/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;
import java.util.function.BooleanSupplier;

/**
 * An example command that uses an example subsystem.
 */

public class ClimbVerticalCommand extends CommandBase {

  private final Climb elevator;
  private final DoubleSupplier armSpeed;
  private final BooleanSupplier autoLevel;

  public ClimbVerticalCommand(Climb elevator, DoubleSupplier armSpeed, BooleanSupplier autoLevel) {
    this.elevator = elevator;
    this.armSpeed = armSpeed;
    this.autoLevel = autoLevel;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      elevator.elevate(armSpeed.getAsDouble(), autoLevel.getAsBoolean());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  //Runs until interrupted
  @Override
  public boolean isFinished() {
    return elevator.atMaxHeight();
  }

}