/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

public final class Constants {
    public static final int FL_TALON = 0;
    public static final int BL_TALON = 1;
    public static final int FR_TALON = 2;
    public static final int BR_TALON = 3;
    public static final int INTAKE_MOTOR_PORT = 6;

    //TODO: Add accurate PANEL_MOTOR_PORTs (ports of motors that spin control panel)
    public static final int PANEL_MOTOR1_PORT = 10000;
    public static final int PANEL_MOTOR2_PORT = 10001;
    
	public static final int INTAKE_SPEED = 1;
    public static final int OUTTAKE_SPEED = -1;

    //TODO: Add accurate panel motor speeds (motors that spin control panel)
    public static final double PANEL_MOTOR1_SPEED = 0.5;
    public static final double PANEL_MOTOR2_SPEED = -1 * PANEL_MOTOR1_SPEED;
    
    public final static int MIN_ROTATIONS = 3;
    public final static int MAX_ROTATIONS = 5;

    public final static I2C.Port I2C_PORT = I2C.Port.kOnboard;

    public final static ColorSensorV3 M_COLOR_SENSOR = new ColorSensorV3(I2C_PORT);

    public final static ColorMatch M_COLOR_MATCHER = new ColorMatch();

    public final static Color BLUE_TARGET = ColorMatch.makeColor(0.125, 0.424, 0.450);
    public final static Color GREEN_TARGET = ColorMatch.makeColor(0.167, 0.580, 0.252);
    public final static Color RED_TARGET = ColorMatch.makeColor(0.518, 0.347, 0.134);
    public final static Color YELLOW_TARGET = ColorMatch.makeColor(0.311, 0.566, 0.121);


    //The following color order is defined for the sensor moving in a clockwise direction
    //If the control panel itself turns clockwise, the sensor will move in a counterclockwise direction
    public final static String[] COLOR_ORDER = {"Blue", "Green", "Red", "Yellow"};

    public final static String DIRECTION = "Clockwise";

    //The distance between our color sensor and the game's color sensor in number of color slices away
    public final static int DIFFERENTIAL = 2;
}

