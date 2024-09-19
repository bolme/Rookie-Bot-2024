package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.DefaultShoot;
import frc.robot.commands.MoveForSeconds;
import frc.robot.commands.SetArmToAngle;
import frc.robot.commands.SpeakerShoot;
import frc.robot.commands.TurnDegrees;
import frc.robot.commands.ResetDriveDirection;
public class OneNoteAndMobility extends SequentialCommandGroup{
    public OneNoteAndMobility(){
        super(new DefaultShoot(10, Constants.ArmAngles.speakerAngle), new ResetDriveDirection(), new MoveForSeconds(0, 0.75,  0.5));
    }
    
}
