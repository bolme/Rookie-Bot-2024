package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeShooter;

public class DefaultShoot extends Command implements Constants {

    private final IntakeShooter intakeShooter;
    private final double voltage;
    private final double angle;
    private boolean endCommand = false;
    public DefaultShoot(double voltage, double angle) {
        this.intakeShooter = IntakeShooter.getInstance();
        this.voltage = voltage;
        this.angle = angle;
    }

    @Override
    public void initialize() {
        /*  1. Set arm to shoot angle and power up shooters
         *  2. Wait two seconds
         *  3. Activate the intake to fire the note
         *  4. Wait two seconds
         *  5. Turn off intake and shooters.
         */
        new SequentialCommandGroup(
            new ParallelCommandGroup(
                new SetArmToAngle(this.angle),
                new InstantCommand(()->{ 
                    intakeShooter.setShooterVoltage(this.voltage); 
                })
            ),
            new InstantCommand(()->{System.out.print("Finished");})
            ,
            new WaitCommand(2.0d),
            new InstantCommand(()->{
                intakeShooter.setIntakeVoltage(10.0);
            }),
            new WaitCommand(1.0d),
            new ParallelCommandGroup(
                new InstantCommand(()->{ 
                    intakeShooter.setShooterVoltage(0); 
                }),
                new InstantCommand(()->{ 
                    intakeShooter.setIntakeVoltage(0); 
                }),
                new SetArmToAngle(Constants.ArmAngles.safeAngle)
            ),
            new InstantCommand(()->{
                endCommand = true;
            })
        ).schedule();
        
    }
    @Override
    public boolean isFinished() {
        // endCommand is set to true after the the note has been shot and the motors are powering down.
        return endCommand; 
    }
}