package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.MoveForSeconds;
import frc.robot.commands.SetArmToAngle;
import frc.robot.commands.TurnDegrees;
import frc.robot.commands.ResetDriveDirection;
public class MobilityAuto extends ParallelCommandGroup{
    // Should be ran facing mid-field, placed to the right of the stage (source side).
    // Just drives straight for 1.5 seconds at max speed (~5 m/s)
    public MobilityAuto(){
        super(new ResetDriveDirection(), new MoveForSeconds(0, 0.75,  0.5), new SetArmToAngle(Constants.ArmAngles.safeAngle));
    }
}
