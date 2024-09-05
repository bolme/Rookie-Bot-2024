package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

import frc.robot.Constants;

public class TurnDegrees extends Command {
    private final double errorLimitDefault = 3;
    private double errorLimit;
    private double targetAngle;
    private Drivetrain drivetrain;

    public TurnDegrees(double angle) { 
        this.targetAngle = angle;
        errorLimit = errorLimitDefault;
        drivetrain = Drivetrain.getInstance();
    }
    public TurnDegrees(double angle, double errorLimit) { 
        this.targetAngle = angle;
        this.errorLimit = errorLimit;
    }

    @Override
    public void initialize() { 
        Drivetrain.targetAngle = targetAngle + Drivetrain.getAngle();
    }

    @Override
    public void execute() { 
        drivetrain.drive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Drivetrain.pidError < errorLimit;
    }
    
    @Override
    public void end(boolean interrupted) { }

}