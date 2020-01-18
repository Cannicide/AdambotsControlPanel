/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Gamepad;

/**
 * Add your docs here.
 */
public class GamepadConstants {
    //// CONSTANTS -------------------------------------------------------------
	/**
	 * Primary Driver Controller Port Number.
	 */
	public static final int PRIMARY_DRIVER = 1;
	/**
	 * Secondary Driver Controller Port Number.
	 */
	public static final int SECONDARY_DRIVER = 2;
	/**
	 * XBOX 360 South Face Button
	 */
	public static final int BUTTON_A = 1;
	/**
	 * XBOX 360 East Face Button
	 */
	public static final int BUTTON_B = 2;
	/**
	 * XBOX 360 West Face Button
	 */
	public static final int BUTTON_X = 3;
	/**
	 * XBOX 360 North Face Button
	 */
	public static final int BUTTON_Y = 4;
	/**
	 * XBOX 360 Left Bumper (Top)
	 */
	public static final int BUTTON_LB = 5;
	/**
	 * XBOX 360 Right Bumper (Top)
	 */
	public static final int BUTTON_RB = 6;
	/**
	 * XBOX 360 Back Button
	 */
	public static final int BUTTON_BACK = 7;
	/**
	 * XBOX 360 Start Button
	 */
	public static final int BUTTON_START = 8;
	/**
	 * XBOX 360 Left Stick Click Button
	 */
	public static final int BUTTON_LEFT_STICK = 9;
	/**
	 * XBOX 360 Right Stick Click Button
	 */
	public static final int BUTTON_RIGHT_STICK = 10;

	/**
	 * XBOX 360 Left Horizontal Axis (Left=-1, Right=1)
	 */
	public static final int AXIS_LEFT_X = 0;
	/**
	 * XBOX 360 Left Vertical Axis (Up=1, Down=-1)
	 */
	public static final int AXIS_LEFT_Y = 1;
	/**
	 * XBOX 360 Trigger Axis (LEFT)
	 */
	public static final int LEFT_AXIS_TRIGGERS = 2;
	/**
	 * XBOX 360 Trigger Axis (RIGHT)
	 */
	public static final int RIGHT_AXIS_TRIGGERS = 3;
	/**
	 * XBOX 360 Right Horizontal Axis (Left=-1, Right=1)
	 */
	public static final int AXIS_RIGHT_X = 4;
	/**
	 * XBOX 360 Right Vertical Axis (Up=1, Down=-1)
	 */
	public static final int AXIS_RIGHT_Y = 5;

	// the ID/port for the whole DPad
	// POV returns an angle in degrees 0-315 at 45 intervals
	public static final int AXIS_DPAD_POV = 0;

}
