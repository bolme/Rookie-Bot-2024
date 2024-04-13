// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private final Talon intakeMotor;
  private final DigitalInput intakeSensor = new DigitalInput(0);

  public Intake(int motorID){
    intakeMotor = new Talon(motorID);
    intakeMotor.setSafetyEnabled(true);
  } 

  public void toggleIntake(boolean enable) {
    intakeMotor.setVoltage(enable ? Constants.intakeVoltage : 0);
  }

  public class IntakeUntilNoteDetected extends Command {
    @Override
    public void initialize() {
      toggleIntake(true);
    }

    @Override
    public void end(boolean interrupted) {
      toggleIntake(false);
    }

    @Override
    public boolean isFinished() {
      return !intakeSensor.get();
    }
  }
}
