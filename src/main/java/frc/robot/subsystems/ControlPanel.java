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
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.DriverStation;

public class ControlPanel extends SubsystemBase {
  /**
   * Creates a new DriveTrainNew.
   */
 

  private String direction;
  private String lastColor;

  public ControlPanel() {
    super();

    //Init control panel code
    Constants.M_COLOR_MATCHER.addColorMatch(Constants.BLUE_TARGET);
    Constants.M_COLOR_MATCHER.addColorMatch(Constants.GREEN_TARGET);
    Constants.M_COLOR_MATCHER.addColorMatch(Constants.RED_TARGET);
    Constants.M_COLOR_MATCHER.addColorMatch(Constants.YELLOW_TARGET);

    direction = "Clockwise";
    lastColor = "Unknown";
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
//This method gets the direction of where the robot is facing
public String getDirection() {
    return direction;
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
public int getProximity() {
    return Constants.M_COLOR_SENSOR.getProximity();
}
//This method starts the motor
public void startMotor() {
    //Will have the code to start spinning the wheel
}
//This method stops the motor
public void stopMotor() {
    //Will have the code to stop spinning the wheel
}


private String rotationalStartingColor;
private int rotationalColorCount;
private boolean offStartingColor = false;
//TODO: For debug purposes:
private boolean stopRotating = false;
//This method starts the rotations of the wheel
public void startRotations() {
    //We can use the color our sensor is detecting as opposed to the game's sensor, it will still work:
    rotationalStartingColor = getColor();
    rotationalColorCount = 0;
    stopRotating = false;
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
        stopRotating = true; //TODO: for testing
        stopMotor();
    }
}
//This method gets the FMS color
public String getFmsColor() {
    /*String gameData;
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
    }*/
    //For testing purposes, assign manual color:
    return "Blue";
}


private String targetColor;

/* code for the color offset corrector*/
public void startAligner() {
    targetColor = getFmsColor();


    //Start rotating
    startMotor();
}

public void monitorAligner() {
    boolean isTarget = false;
    if (getColor().equals("Yellow") && targetColor.equals("Green")) {
        isTarget = true;
    }
    if (getColor().equals("Green") && targetColor.equals("Yellow")) {
        isTarget = true;
    }
    if (getColor().equals("Blue") && targetColor.equals("Red")) {
        isTarget = true;
    }
    if (getColor().equals("Red") && targetColor.equals("Blue")) {
        isTarget = true;
    }
    else {
        isTarget = false;
    }

    if (isTarget) {
        stopRotating = true;
        stopMotor();
    }
    else {
        stopRotating = false;
    }
    
}

//This method gets confidence for the robot
public double getConfidence() {
    Color detectedColor = Constants.M_COLOR_SENSOR.getColor();
    ColorMatchResult match = Constants.M_COLOR_MATCHER.matchClosestColor(detectedColor);
    return match.confidence;
}

private boolean startedTracker = false;

//This method puts stuff on the dashboard
public void putDashboard() {

    //TODO: Following if statement for testing out rotation-tracker
    if (!startedTracker) {
        //startRotations();
        startAligner();
        startedTracker = true;
        //Begins testing rotation-tracker when on color red.
    }

    if (startedTracker) {
        //monitorRotations();
        monitorAligner();
    }

    SmartDashboard.putNumber("Confidence", getConfidence());
    SmartDashboard.putString("Detected Color", getColor());

    SmartDashboard.putString("Direction", getDirection());
    SmartDashboard.putString("Predicted Next Color", mapNextColor(getColor()));
    SmartDashboard.putBoolean("Target Color on Game Sensor", stopRotating);
    SmartDashboard.putNumber("Rotations", getRotations());

}

  


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
