// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private final CANSparkMax intakeMotor1;
  private final CANSparkMax intakeMotor2;

  public Intake(int motorOneID, int motorTwoID) {
    intakeMotor1 = new CANSparkMax(motorOneID, MotorType.kBrushless);
    intakeMotor2 = new CANSparkMax(motorTwoID, MotorType.kBrushless);
  } 

  public void toggleIntake(boolean enable) {
    intakeMotor1.setVoltage(enable ? Constants.intakeVoltage : 0);
    intakeMotor2.setVoltage(enable ? Constants.intakeVoltage : 0);
  }

  public class IntakeUntilNoteDetected extends Command {
    @Override
    public void initialize() {
      toggleIntake(true);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
      toggleIntake(false);
    }

    @Override
    public boolean isFinished() {
      return false;
    }
  }
}
