package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.MoveForSeconds;
import frc.robot.commands.SetRelativeTurnAngle;
import frc.robot.commands.TurnDegrees;
import frc.robot.commands.TurnToAngle;

public class TestAuto extends SequentialCommandGroup{
    // Should be ran facing mid-field, placed to the right of the stage (source side).
    // Just drives straight for 1.5 seconds at max speed (~5 m/s)
    public TestAuto(){
        super(
            new TurnDegrees(45),
            new WaitCommand(1),
            new TurnToAngle(300)
        );
    }
}
