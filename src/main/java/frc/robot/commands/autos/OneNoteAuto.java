package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.MoveForSeconds;
import frc.robot.commands.SpeakerShoot;
import frc.robot.commands.TurnDegrees;
public class OneNoteAuto extends SequentialCommandGroup{
    public OneNoteAuto(){
        super(new SpeakerShoot());
    }
    
}
