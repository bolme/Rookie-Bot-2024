package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.commands.DefaultShoot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;

public class Wheelie extends Command implements Constants{
    Drivetrain drivetrain = Drivetrain.getInstance();
    Arm arm = Arm.getInstance();
    public Wheelie() { }

    @Override
    public void initialize() {
        drivetrain.resetGyro();
        Arm.getInstance().setAngle(130);
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