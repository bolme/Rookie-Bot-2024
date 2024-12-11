package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.commands.DefaultShoot;
import frc.robot.subsystems.Arm;

public class Wheelie extends Command implements Constants{
    public Wheelie() { }

    @Override
    public void initialize() {
        Arm.getInstance().setAngle(140);
     }

    public void periodic  () {}

    @Override
    public boolean isFinished() {
        return true;
    }
    
    @Override
    public void end(boolean interrupted) {
    }

}