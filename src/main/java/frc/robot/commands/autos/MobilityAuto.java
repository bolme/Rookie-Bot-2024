package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.MoveForSeconds;
import frc.robot.commands.TurnDegrees;

public class MobilityAuto extends SequentialCommandGroup{
    // TODO: needs to be properly configured to go the correct distance (currently guesses)
    public MobilityAuto(){
        super(new MoveForSeconds(0, 1, 1.5));
    }
}
