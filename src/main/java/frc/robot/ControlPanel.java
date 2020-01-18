package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.DriverStation;

public class ControlPanel {
  
    private static String direction;
    private static String lastColor;
    
    public static void init() {   
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.BLUE_TARGET);
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.GREEN_TARGET);
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.RED_TARGET);
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.YELLOW_TARGET);

        direction = "Clockwise";
        lastColor = "Unknown";
    }

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

    public static String getDirection() {
        return direction;
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

    public static int getProximity() {
        return Constants.M_COLOR_SENSOR.getProximity();
    }

    public static void startMotor() {
        //Will have the code to start spinning the wheel
    }

    public static void stopMotor() {
        //Will have the code to stop spinning the wheel
    }


    private static String rotationalStartingColor;
    private static int rotationalColorCount;
    private static boolean offStartingColor = false;
    //TODO: For debug purposes:
    private static boolean stopRotating = false;

    public static void startRotations() {
        //We can use the color our sensor is detecting as opposed to the game's sensor, it will still work:
        rotationalStartingColor = getColor();
        rotationalColorCount = 0;
        stopRotating = false;
        offStartingColor = false;

        //start rotating control wheel motor
        startMotor();
    }

    public static int getRotations() {
        return rotationalColorCount / 2;
    }

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
            stopRotating = true; //TODO: for testing
            stopMotor();
        }
    }

    public static String getFmsColor() {
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

    private static String alignerStartingColor;
    private static String targetColor;

    /* code for the color offset corrector*/
    public static void startAligner() {
        alignerStartingColor = getColor();
        targetColor = getFmsColor();

        //Start rotating
        startMotor();
    }

    public static void monitorAligner() {
        
        

    }

    public static double getConfidence() {
        Color detectedColor = Constants.M_COLOR_SENSOR.getColor();
        ColorMatchResult match = Constants.M_COLOR_MATCHER.matchClosestColor(detectedColor);
        return match.confidence;
    }

    /*public static double getColorChannel(String color) {
        Color detectedColor = Constants.M_COLOR_SENSOR.getColor();

        if (color.equals("Red")) {
            return detectedColor.red;
        }
        else if (color.equals("Green")) {
            return detectedColor.green;
        }
        else if (color.equals("Blue")) {
            return detectedColor.blue;
        }
        else {
            return 0.0;
        }
    }*/

    //TODO: For testing rotation-tracker
    private static boolean startedTracker = false;

    public static void dashboard() {

        //TODO: Following if statement for testing out rotation-tracker
        if (!startedTracker) {
            startRotations();
            startedTracker = true;
            //Begins testing rotation-tracker when on color red.
        }

        if (startedTracker) {
            monitorRotations();
        }

        //SmartDashboard.putNumber("Confidence", getConfidence());
        SmartDashboard.putString("Detected Color", getColor());
    
        //TODO: Below are SmartDashboard values for debugging/testing purposes only
        /*SmartDashboard.putNumber("Red", getColorChannel("Red"));
        SmartDashboard.putNumber("Green", getColorChannel("Green"));
        SmartDashboard.putNumber("Blue", getColorChannel("Blue"));*/

        //TODO: Below are to-be-tested/work-in-progress values
        SmartDashboard.putString("Direction", getDirection());
        SmartDashboard.putString("Predicted Next Color", mapNextColor(getColor()));
        SmartDashboard.putBoolean("3 Rotations Complete", stopRotating);
        SmartDashboard.putNumber("Rotations", getRotations());

    }
}