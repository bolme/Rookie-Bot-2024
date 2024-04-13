// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class Taxi extends Command {
  /** Creates a new Taxi. */
  // This is a simple example auto that will drive forward for 2 seconds
  private double delayInSeconds = 2; 
  
  Drivetrain driveTrain; 

  Timer globalTimer = new Timer();

  public Taxi(Drivetrain drive) {
    this.driveTrain = drive; 

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    globalTimer.reset();
    globalTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveTrain.drive(0.5, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.drive(0, 0);
    globalTimer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return globalTimer.hasElapsed(delayInSeconds);
  }
}
