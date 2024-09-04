package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.commands.DefaultShoot;

public class AmpShoot extends Command implements Constants{
    public AmpShoot() { }

    @Override
    public void initialize() { }

    @Override
    public boolean isFinished() {
        return true;
    }
    
    @Override
    public void end(boolean interrupted) {
        new DefaultShoot(10, Constants.ArmAngles.ampAngle).schedule();
    }

}