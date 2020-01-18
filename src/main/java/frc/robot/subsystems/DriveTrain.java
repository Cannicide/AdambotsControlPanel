/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrainNew.
   */
  private WPI_TalonSRX BackLeftMotor;
  private WPI_TalonSRX FrontRightMotor;
  private WPI_TalonSRX FrontLeftMotor;
  private WPI_TalonSRX BackRightMotor;

  DifferentialDrive drive;

  public DriveTrain() {
    super();

    FrontRightMotor = new WPI_TalonSRX(Constants.FR_TALON);    
    FrontLeftMotor = new WPI_TalonSRX(Constants.FL_TALON);    
    BackLeftMotor = new WPI_TalonSRX(Constants.BL_TALON);  
    BackRightMotor = new WPI_TalonSRX(Constants.BR_TALON);  
    BackLeftMotor.follow(FrontLeftMotor);
    BackRightMotor.follow(FrontRightMotor);

    BackLeftMotor.setInverted(false);
    FrontLeftMotor.setInverted(false);
    BackRightMotor.setInverted(true);
    FrontRightMotor.setInverted(true);

    drive = new DifferentialDrive(FrontLeftMotor, FrontRightMotor);
  }

  public void arcadeDrive(double speed, double turnSpeed){
    drive.arcadeDrive(speed, turnSpeed);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
