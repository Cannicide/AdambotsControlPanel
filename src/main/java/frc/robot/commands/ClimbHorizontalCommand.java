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

/**
 * An example command that uses an example subsystem.
 */

 /**
  * 
  */
public class ClimbHorizontalCommand extends CommandBase {

  private final Climb elevator;
  private final DoubleSupplier glideSpeed;

  public ClimbHorizontalCommand(Climb elevator, DoubleSupplier glideSpeed) {
    this.elevator = elevator;
    this.glideSpeed = glideSpeed;
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
      elevator.glide(glideSpeed.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  //Runs until interrupted
  @Override
  public boolean isFinished() {
    return false;
  }

}