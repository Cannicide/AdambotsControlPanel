package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatchResult;

public class ControlPanel {
  
    private static String direction = "Clockwise";
    private static String lastColor = "Unknown";
    
    public static void init() {   
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.BLUE_TARGET);
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.GREEN_TARGET);
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.RED_TARGET);
        Constants.M_COLOR_MATCHER.addColorMatch(Constants.YELLOW_TARGET);
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

        if (getProximity() < 100) {
            colorString = "Unknown";
        }

        int index = Arrays.asList(Constants.COLOR_ORDER).indexOf(colorString);
        int lastIndex = Arrays.asList(Constants.COLOR_ORDER).indexOf(lastColor);

        int nextColor = index - 1;

        if (nextColor < 0) nextColor = Constants.COLOR_ORDER.length - 1; 

        if (nextColor == lastIndex) {
            direction = "Clockwise";
        }
        else if (colorString != "Unknown" && lastColor != "Unknown") {
            direction = "Counterclockwise";
        }
        else {
            direction = "Unknown";
        }

        lastColor = colorString;
    
        return colorString;
    }

    public static String getDirection() {
        return direction;
    }

    public static String mapNextColor() {
        String currentColor = getColor();
        String currentDirection = getDirection();
        String nextColor;

        int index = Arrays.asList(Constants.COLOR_ORDER).indexOf(currentColor);
        int newIndex;

        if (currentDirection == "Clockwise") {
            if (index + 1 > Constants.COLOR_ORDER.length - 1) {
                newIndex = 0;
            }
            else {
                newIndex = index + 1;
            }

            nextColor = Constants.COLOR_ORDER[newIndex];
        }
        else if (currentDirection == "Counterclockwise") {
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


    private static String rotationalStartingColor;
    private static int rotationalColorCount;
    private static boolean offStartingColor = false;
    //TODO: For debug purposes:
    private static boolean stopRotating = false;

    public static void startRotating() {
        //We can use the color our sensor is detecting as opposed to the game's sensor, it will still work:
        rotationalStartingColor = getColor();
        rotationalColorCount = 0;
        stopRotating = false;
        offStartingColor = false;

        //start rotating control wheel motor
    }

    public static void mightStopRotating () {

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

        if (rotationalColorCount/2 >= Constants.MIN_ROTATIONS) {
            //stop rotating
            stopRotating = true; //TODO: for testing
        }
    }

    /* pseudo-code for the color offset corrector*/
    public static String getColorCorrector() {
        String colorApprox = getColor();

        if (colorApprox.equals("Blue")) {
            //colorApprox = what ever the thing is supposed to be
        }
        else if (colorApprox.equals("Red")) {
            //colorApprox = what ever the thing is supposed to be
        }
        else if (colorApprox.equals("Green")) {
            //colorApprox = what ever the thing is supposed to be
        }
        else if (colorApprox.equals("Yellow")) {
            //colorApprox = what ever the thing is supposed to be
        }

        return colorApprox;
    }    


    //TODO: For testing rotation-tracker
    private static boolean startedTracker = false;

    public static void dashboard() {

        Color detectedColor = Constants.M_COLOR_SENSOR.getColor();
        ColorMatchResult match = Constants.M_COLOR_MATCHER.matchClosestColor(detectedColor);

        //TODO: Following if statement for testing out rotation-tracker
        if (getColor() == "Red" && !startedTracker) {
            startRotating();
            startedTracker = true;
            //Begins testing rotation-tracker when on color red.
        }

        if (startedTracker) {
            mightStopRotating();
        }

        //SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", getColorCorrector());
        //SmartDashboard.putNumber("Proximity", getProximity());
    
        //TODO: Below are SmartDashboard values for debugging/testing purposes only
        /*SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);*/

        //TODO: Below are to-be-tested/work-in-progress values
        //SmartDashboard.putString("Direction", getDirection());
        //SmartDashboard.putString("Predicted Next Color", mapNextColor());
        SmartDashboard.putBoolean("3 Rotations Complete", stopRotating);
        SmartDashboard.putNumber("Rotations", rotationalColorCount / 2);

    }
}