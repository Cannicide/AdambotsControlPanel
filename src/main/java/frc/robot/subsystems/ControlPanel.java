/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Color Sensor dependencies:
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorMatchResult;


//FMS dependencies:
import edu.wpi.first.wpilibj.DriverStation;


//Motor dependencies:
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ControlPanel extends SubsystemBase {
  /**
   * Creates a new ControlPanel Subsystem.
   */
 

  private String lastColor;
  public WPI_TalonSRX panelMotor1;
  public WPI_TalonSRX panelMotor2;
  private boolean rotationsFinished;
  private boolean alignerFinished;

  public ControlPanel() {
    super();

    //Init control panel code
    Constants.M_COLOR_MATCHER.addColorMatch(Constants.BLUE_TARGET);
    Constants.M_COLOR_MATCHER.addColorMatch(Constants.GREEN_TARGET);
    Constants.M_COLOR_MATCHER.addColorMatch(Constants.RED_TARGET);
    Constants.M_COLOR_MATCHER.addColorMatch(Constants.YELLOW_TARGET);

    lastColor = "Unknown";
    rotationsFinished = false;
    alignerFinished = false;

    panelMotor1 = new WPI_TalonSRX(Constants.PANEL_MOTOR1_PORT);
    panelMotor2 = new WPI_TalonSRX(Constants.PANEL_MOTOR2_PORT);
  }

  public String getColor() {

        

    Color detectedColor = Constants.M_COLOR_SENSOR.getColor();

    /**
     * Run the color match algorithm on our detected color
     */
    String colorString;
    ColorMatchResult match = Constants.M_COLOR_MATCHER.matchClosestColor(detectedColor);

    if (match.color == Constants.BLUE_TARGET) {
      colorString = "Blue";
    } else if (match.color == Constants.RED_TARGET) {
      colorString = "Red";
    } else if (match.color == Constants.GREEN_TARGET) {
      colorString = "Green";
    } else if (match.color == Constants.YELLOW_TARGET) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }

    //If beyond 3 inches, detected color is inaccurate
    if (getProximity() < 100) {
        colorString = "Unknown";
    }

    //Account for inaccurate colors detected in transition between two colors
    if (!mapNextColor(lastColor).equals(colorString) && !lastColor.equals("Unknown")) {
        //If the detected color does not appropriately match the predicted color to come after the last color,
        //And both the last color and direction clockwise/counter-clockwise are known,
        //Then stick to the value of the lastColor.
        colorString = lastColor;
    }

    lastColor = colorString;

    return colorString;
}

public String getDirection() {
    return Constants.DIRECTION;
}

public String mapNextColor(String color) {
    String currentColor = color;
    String currentDirection = getDirection();
    String nextColor;

    int index = Arrays.asList(Constants.COLOR_ORDER).indexOf(currentColor);
    int newIndex;

    if (currentDirection.equals("Clockwise")) {
        if (index + 1 > Constants.COLOR_ORDER.length - 1) {
            newIndex = 0;
        }
        else {
            newIndex = index + 1;
        }

        nextColor = Constants.COLOR_ORDER[newIndex];
    }
    else if (currentDirection.equals("Counterclockwise")) {
        if (index - 1 < 0) {
            newIndex = Constants.COLOR_ORDER.length - 1;
        }
        else {
            newIndex = index - 1;
        }

        nextColor = Constants.COLOR_ORDER[newIndex];
    }
    else {
        nextColor = "Unknown";
    }

    return nextColor;
}

//This method gets the proximity of the color sensor to the wheel
public double getProximity() {
    return Constants.M_COLOR_SENSOR.getProximity();
}


public void startMotor() {
    //Will have the code to start spinning the wheel
    panelMotor1.set(ControlMode.PercentOutput, Constants.PANEL_MOTOR1_SPEED);
    panelMotor2.set(ControlMode.PercentOutput, Constants.PANEL_MOTOR2_SPEED);
}


public void stopMotor() {
    //Will have the code to stop spinning the wheel
    panelMotor1.set(ControlMode.PercentOutput, 0.0);
    panelMotor2.set(ControlMode.PercentOutput, 0.0);
}


private String rotationalStartingColor;
private int rotationalColorCount;
private boolean offStartingColor = false;


public void startRotations() {
    //We can use the color our sensor is detecting as opposed to the game's sensor, it will still work:
    rotationalStartingColor = getColor();
    rotationalColorCount = 0;
    offStartingColor = false;

    //start rotating control wheel motor
    startMotor();
}

//This method counts the rotations of the wheel
public int getRotations() {
    return rotationalColorCount / 2;
}

//This method monitors the rotations of the wheel
public void monitorRotations() {

    if (rotationalStartingColor.equals(getColor()) && offStartingColor) {
        rotationalColorCount++;
        offStartingColor = false;
    }
    if (!rotationalStartingColor.equals(getColor())) {
        offStartingColor = true;
    }

    if (getRotations() >= Constants.MIN_ROTATIONS) {
        //stop rotating
        rotationsFinished = true;
        stopMotor();
    }
}

public boolean isFinished(String event) {
    if (event.equals("rotations")) {
        return rotationsFinished;
    }
    else if(event.equals("aligner")) {
        return alignerFinished;
    }
    else {
        return false;
    }
}

//This method gets the FMS color
public String getFmsColor() {
    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
        switch (gameData.charAt(0)) {
        case 'B':
            return "Blue";
        case 'G':
            return "Green";
        case 'R':
            return "Red";
        case 'Y':
            return "Yellow";
        default:
            // This is corrupt data
            return "Corrupt Data";
        }
    } else {
        // Code for no data received yet
        return "Unknown";
    }
}


private String targetColor;

/* code for the color offset corrector*/
public void startAligner() {
    targetColor = getFmsColor();


    //Start rotating
    startMotor();
}

public int getDifferential() {
    return Constants.DIFFERENTIAL;
}

//Gets the color that is two color slices away from our sensor's position
public String colorCorrector(String currentColor) {
    if (getDifferential() == 2) {
        return mapNextColor(mapNextColor(currentColor));
    }
    else if (getDifferential() == 3) {
        return mapNextColor(mapNextColor(mapNextColor(currentColor)));
    }
    else {
        return mapNextColor(currentColor);
    }
}

public void monitorAligner() {
    boolean isTarget = false;
    
    if (targetColor.equals(colorCorrector(getColor()))) {
        isTarget = true;
    }
    else {
        isTarget = false;
    }

    if (isTarget) {
        stopMotor();
    }
    
}



//These methods put stuff on the dashboard (only use if necessary)
public void putDashRotations() {

    SmartDashboard.putString("Detected Color", getColor());
    SmartDashboard.putString("Predicted Next Color", mapNextColor(getColor()));
    SmartDashboard.putNumber("Rotations", getRotations());

}

public void putDashAligner() {

    SmartDashboard.putString("Detected Color", getColor());
    SmartDashboard.putString("Predicted Gamesensor Color", colorCorrector(getColor()));
    SmartDashboard.putString("Target Gamesensor Color", targetColor);

}



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
