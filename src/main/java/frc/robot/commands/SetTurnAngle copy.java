package frc.robot.commands;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;

public class SetTurnAngle extends Command {

    private double targetAngle;

    public SetTurnAngle(double angle) { 
        this.targetAngle = angle;
    }

    @Override
    public void initialize() { 
        Drivetrain.targetAngle = angle;
    }

    @Overridea
    public void execute() { }

    @Override
    public boolean isFinished() {
        return true;
    }
    
    @Override
    public void end(boolean interrupted) { }

}