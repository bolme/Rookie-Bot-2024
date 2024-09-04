package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.MoveForSeconds;

public class MobilityAuto extends SequentialCommandGroup{
    // TODO: needs to be properly configured to go the correct distance (currently guesses)
    public MobilityAuto(){
        super(new MoveForSeconds(0, 0.5, 3));
    }
}
