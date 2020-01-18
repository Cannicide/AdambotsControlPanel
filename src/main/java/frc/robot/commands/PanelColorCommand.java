/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class PanelColorCommand extends CommandBase {
  private final ControlPanel controlPanel;

  public PanelColorCommand(ControlPanel controlPanel) {
    this.controlPanel = controlPanel;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(controlPanel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
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
