package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;

public class MoveForSeconds extends Command {
    private final Drivetrain drivetrain;
    private final double xPercent;
    private final double yPercent;
    private static Timer timer = new Timer();
    private double seconds;

    public MoveForSeconds(double xPercent, double yPercent, double seconds) {
        this.drivetrain = Drivetrain.getInstance();
        this.xPercent = xPercent;
        this.yPercent = yPercent;
        this.seconds = seconds;
        addRequirements(Arm.getInstance());
    }

    @Override
    public void initialize() {
        drivetrain.drive(xPercent, yPercent);
    }

    @Override
    public void execute() { }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(seconds);
    }
}