package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatchResult;

public class ControlPanel {
  
    
    public static void init() {
        System.out.println("Initialized");   
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
    
        return colorString;
    }

    public static int getProximity() {
        return Constants.M_COLOR_SENSOR.getProximity();
    }


    static String rotationalStartingColor;
    static int rotationalColorCount;
    static boolean onColor = false;

    public static void startRotating() {
        rotationalStartingColor = getColorCorrector();
        rotationalColorCount = 1;

        //start rotating control wheel motor
    }

    public static void mightStopRotating () {

        if (rotationalStartingColor == getColorCorrector() && onColor) {
            rotationalColorCount++;
        }
        if (rotationalStartingColor != getColorCorrector()) {
            onColor = true;
        }

        if ((rotationalColorCount/2)-0.5 >= Constants.MIN_ROTATIONS) {
            //stop rotating
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
    

    public static void dashboard() {

        Color detectedColor = Constants.M_COLOR_SENSOR.getColor();
        ColorMatchResult match = Constants.M_COLOR_MATCHER.matchClosestColor(detectedColor);

        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", getColorCorrector());
        SmartDashboard.putNumber("Proximity", getProximity());
    
        System.out.println("Detected color: " + getColorCorrector());
    
    }
}