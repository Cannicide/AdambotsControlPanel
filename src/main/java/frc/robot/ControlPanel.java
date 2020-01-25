package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatchResult;

//import edu.wpi.first.wpilibj.DriverStation;

//Motor dependencies:
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

//control panel program
public class ControlPanel {
  
    private static String lastColor;
    
    public static void init() {   
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.BLUE_TARGET);
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.GREEN_TARGET);
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.RED_TARGET);
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.YELLOW_TARGET);

        lastColor = "Unknown";
    }
    //This method gets the color on the spinning wheel
    public static String getColor() {

        

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
    public static String getDirection() {
        return Constants.DIRECTION;
    }

    public static String mapNextColor(String color) {
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
    public static int getProximity() {
        return Constants.M_COLOR_SENSOR.getProximity();
    }
    //This method starts the motor
    public static void startMotor() {
        //Will have the code to start spinning the wheel
    }
    //This method stops the motor
    public static void stopMotor() {
        //Will have the code to stop spinning the wheel
    }


    private static String rotationalStartingColor;
    private static int rotationalColorCount;
    private static boolean offStartingColor = false;

    private static boolean stopRotating = false;

    //This method starts the rotations of the wheel
    public static void startRotations() {
        //We can use the color our sensor is detecting as opposed to the game's sensor, it will still work:
        rotationalStartingColor = getColor();
        rotationalColorCount = 0;
        stopRotating = false;
        offStartingColor = false;

        //start rotating control wheel motor
        startMotor();
    }
    //This method counts the rotations of the wheel
    public static int getRotations() {
        return rotationalColorCount / 2;
    }
    //This method monitors the rotations of the wheel
    public static void monitorRotations() {

        //Thought process for rotations (rCC is rotationalColorCount):
        /*
            1/2 rotation -> rCC = 1
            1 rotation -> rCC = 2
            1.5 rotation -> rCC = 3
            ...
            3 rotations -> rCC = 6
            */

        if (rotationalStartingColor.equals(getColor()) && offStartingColor) {
            rotationalColorCount++;
            offStartingColor = false;
        }
        if (!rotationalStartingColor.equals(getColor())) {
            offStartingColor = true;
        }

        if (getRotations() >= Constants.MIN_ROTATIONS) {
            //stop rotating
            stopRotating = true;
            stopMotor();
        }
    }
    //This method gets the FMS color
    public static String getFmsColor() {
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

    
    private static String targetColor;

    /* code for the color offset corrector*/
    public static void startAligner() {
        targetColor = getFmsColor();


        //Start rotating
        startMotor();
    }

    public static String colorCorrector(String currentColor) {
        return mapNextColor(mapNextColor(currentColor));
    }
    
    public static void monitorAligner() {
        boolean isTarget = false;
        
        if (targetColor.equals(colorCorrector(getColor()))) {
            isTarget = true;
        }
        else {
            isTarget = false;
        }
    
        if (isTarget) {
            stopRotating = true;
            stopMotor();
        }
        
    }

    private static boolean startedTracker = false;

    //This method puts stuff on the dashboard
    public static void dashboard() {

        //Following if statement for testing out rotation-tracker
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

        //Immediately below are tested and working values
        SmartDashboard.putString("Detected Color", getColor());
        //SmartDashboard.putString("Predicted Next Color", mapNextColor(getColor()));
        //SmartDashboard.putNumber("Rotations", getRotations());
    

        //Below are to-be-tested/work-in-progress values
        SmartDashboard.putString("Predicted GSC", colorCorrector(getColor()));
        SmartDashboard.putString("Target GSC", targetColor);
        //GSC = Game Sensor Color (color that the game's built-in sensor detects)

    }
}