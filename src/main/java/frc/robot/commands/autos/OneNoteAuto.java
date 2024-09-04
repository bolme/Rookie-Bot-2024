package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.SwerveDrive;

public class OneNoteAuto extends SequentialCommandGroup{
    // TODO: needs to be properly configured to go the correct distance (currently guesses)
    public OneNoteAuto(){
        super(new SpeakerShoot(), new MoveForSeconds(0.1, 0.5, 1), new MoveForSeconds(0, 0.5, 2));
    }
    
}
