package frc.robot.commands;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;

public class SetRelativeTurnAngle extends Command {

    private double targetAngle;
    private Drivetrain drivetrain;

    public SetRelativeTurnAngle(double angle) { 
        this.targetAngle = angle;
        drivetrain = Drivetrain.getInstance();
    }

    @Override
    public void initialize() { 
        Drivetrain.targetAngle = targetAngle + drivetrain.getAngle();
    }

    @Override
    public void execute() { }

    @Override
    public boolean isFinished() {
        return true;
    }
    
    @Override
    public void end(boolean interrupted) { }

}