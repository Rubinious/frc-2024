package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.FeedforwardCharacterization;
import frc.robot.commands.FeedforwardCharacterization.Config;

/**
 * A subsystem that controls the drivey bit of the robot.
 */
public class Drivetrain extends SubsystemBase {
    // The kind of drivetrain we are controlling: either simulation or real.
    private final DrivetrainIO io;
    // The current sensor readings for our drivetrain. This is actually a `DrivetrainIO.Inputs`, but the annotation on the class generated this lovely version for us that logs itself nicely.
    private final InputsAutoLogged inputs = new InputsAutoLogged();

    /**
     * Construct a new drivetrain.
     * @param io the kind of drivetrain we are controlling: either simulation or real
     */
    public Drivetrain(DrivetrainIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        // This tells our drivetrain-like-thing (either real or simulated) to update our class with all the sensor data.
        io.updateInputs(inputs);
        // Log all the sensor data.
        Logger.processInputs("Drivetrain", inputs);
    }

    /**
     * Make the robot drive with the given translational power and rotational power.
     * @param translation the forward-backwards power in the range [-1, 1]
     * @param rotation the left-right turning power in the range [-1, 1]
     */
    public void arcadeDrive(double translation, double rotation) {
        io.setVoltages(12 * (translation + rotation), 12 * (translation - rotation));
    }

    /**
     * Get the average position of the two encoders. If the robot has only driven straight, this is the distance its travelled.
     */
    public double getDistance() {
        return (inputs.leftPosition + inputs.rightPosition) / 2;
    }

    public double getYaw() {
        return inputs.yaw;
    }

    // The drivetrain needs 4m of clearance in front of and behind it when running this command.
    public Command characterize() {
        return new FeedforwardCharacterization(new Config(
            voltage -> io.setVoltages(voltage, voltage),
            this::getDistance,
            () -> (inputs.leftVelocity + inputs.rightVelocity) / 2, 
            6, 
            12, 
            2, 
            4, 
            "Drivetrain"
        ));
    }
}