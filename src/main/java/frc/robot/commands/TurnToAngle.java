package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;

public class TurnToAngle extends Command {
    



    public TurnToAngle(double angle) { 
        this.targetAngle = angle;
        
    }

    @Override
    public void initialize() { 
        Drivetrain.targetAngle = angle;
    }

    @Override
    public void execute() { 

    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
    @Override
    public void end(boolean interrupted) {
        
    }

}