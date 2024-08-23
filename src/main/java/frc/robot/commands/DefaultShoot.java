// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Made DefaultShoot the B keybind on the Xbox controller so we can test it
// This command is meant so that we can input the speeds

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeShooter;
//// IntakeShooter
public class DefaultShoot extends Command implements Constants {

    private final IntakeShooter intakeShooter;
    private final double voltage;
    private final double angle;
    private boolean endCommand = false;
    public DefaultShoot(double voltage, double angle) {
        this.intakeShooter = IntakeShooter.getInstance();
        this.voltage = voltage;
        this.angle = angle;
      // Adjust the desiredVoltage variable to the voltage value you want to use. 
      // You can then use this instance of DefaultShoot in your robot's command scheduler or bind it to a button as needed for your specific control setup.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
 
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
            new WaitCommand(2.0d),
            new ParallelCommandGroup(
                new InstantCommand(()->{ 
                    intakeShooter.setShooterVoltage(0); 
                }),
                new InstantCommand(()->{ 
                    intakeShooter.setIntakeVoltage(0); 
                })
            ),
            new InstantCommand(()->{
                endCommand = true;
            })
        ).schedule();
        
    }
    @Override
    public boolean isFinished() {
        // The command is finished when the arm is at the target angle
        return endCommand; 
    }
}