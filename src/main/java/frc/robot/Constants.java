package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;

public class Constants {
    public final static int MIN_ROTATIONS = 3;
    public final static int MAX_ROTATIONS = 5;

    public final static I2C.Port I2C_PORT = I2C.Port.kOnboard;

    public final static ColorSensorV3 M_COLOR_SENSOR = new ColorSensorV3(I2C_PORT);

    public final static ColorMatch M_COLOR_MATCHER = new ColorMatch();

    public final static Color BLUE_TARGET = ColorMatch.makeColor(0.125, 0.424, 0.450);
    public final static Color GREEN_TARGET = ColorMatch.makeColor(0.167, 0.580, 0.252);
    public final static Color RED_TARGET = ColorMatch.makeColor(0.518, 0.347, 0.134);
    public final static Color YELLOW_TARGET = ColorMatch.makeColor(0.311, 0.566, 0.121);

}