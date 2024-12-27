package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;

public class MockClimb extends Command implements Constants {
    Drivetrain drivetrain = Drivetrain.getInstance();
    Arm arm = Arm.getInstance();

    private double Climb = 90;

    public MockClimb() {
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        Climb = 90;
        Arm.getInstance().setAngle(Climb);
    }

    @Override
    public void execute() {
        Climb -= 0.5;
        System.out.printf("Climb: %f\n", Climb);
        Arm.getInstance().setAngle(Climb);

    }

    @Override
    public boolean isFinished() {
        return (Climb <= 10.0);
    }

    @Override
    public void end(boolean interrupted) {
        Arm.getInstance().setAngle(10);
    }

}