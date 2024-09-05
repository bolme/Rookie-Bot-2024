package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.MoveForSeconds;
import frc.robot.commands.TurnDegrees;

public class MobilityAuto extends SequentialCommandGroup{
    // Should be ran facing mid-field, placed to the right of the stage (source side).
    // Just drives straight for 1.5 seconds at max speed (~5 m/s)
    public MobilityAuto(){
        super(new MoveForSeconds(0, 1, 1.5));
    }
}
