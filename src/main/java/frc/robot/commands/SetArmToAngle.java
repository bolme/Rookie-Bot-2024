package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class SetArmToAngle extends Command {
    private final Arm arm;
    private final double targetAngle;
    private final double kDefaultError = 4.0; // degrees
    private double error = kDefaultError;

    public SetArmToAngle(double targetAngle) {
        this.arm = Arm.getInstance();
        this.targetAngle = targetAngle;
        this.error = kDefaultError;
        addRequirements(arm);
    }

    public SetArmToAngle(double targetAngle, double angleError) {
        this.arm = Arm.getInstance();
        this.targetAngle = targetAngle;
        this.error = angleError;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setAngle(targetAngle);
    }

    @Override
    public void execute() { /* PID Controller handles arm movement*/ }

    @Override
    public void end(boolean interrupted) { }

    @Override
    public boolean isFinished() {
        // finishes when the variations between the target and current angle is below the set error;
        double currentError = Math.abs(arm.getAngle() - targetAngle);
        return currentError < error; 
    }
}