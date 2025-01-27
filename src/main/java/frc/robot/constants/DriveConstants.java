package frc.robot.constants;

import edu.wpi.first.math.util.Units;

/**
 * Constants related to the dimensioning and control of the drivetrain. Units are in meters, kilograms, seconds.
 */
public class DriveConstants {
    public static double PRECISION_FWD_REV = 0.6;
    public static double PRECISION_TURN = 0.7;

    public static double GEARBOX_RATIO = 1 / 8.45;
    public static double WHEEL_DIAMETER = Units.inchesToMeters(6);
    public static double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; 
    public static double ENCODER_CONVERSION_FACTOR = WHEEL_CIRCUMFERENCE * GEARBOX_RATIO;
    public static double CONTROLLER_DEADZONE = 0.15;
}