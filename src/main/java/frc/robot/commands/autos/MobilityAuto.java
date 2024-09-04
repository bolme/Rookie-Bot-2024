package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.SwerveDrive;

public class MobilityAuto extends SequentialCommandGroup{
    static driveCommand = new MoveForSeconds(0, 0.5, 2);
    public CameraMiddleTwoNote(){
        super(AutoBuilder.buildAuto("Mobility"), driveCommand);
    }
    
}
