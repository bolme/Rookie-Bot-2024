// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final WPI_TalonSRX flyWheelMotor1;
  private final WPI_TalonSRX flyWheelMotor2;
  /** Creates a new Shooter. */
  public Shooter(int motorOneID, int motorTwoID) {
    flyWheelMotor1 = new WPI_TalonSRX(motorOneID);
    flyWheelMotor2 = new WPI_TalonSRX(motorTwoID);
  }

  public void toggleShooter(boolean enable) {
    flyWheelMotor1.setVoltage(enable ? Constants.shooterVoltage : 0);
    flyWheelMotor2.setVoltage(enable ? Constants.shooterVoltage : 0);
  }

  public class SpinUpMotors extends Command {
    double spinupTime = 1;
    Timer spinUpTimer = new Timer();
    public SpinUpMotors(double spinupTime) {
      this.spinupTime = spinupTime;
    }
    @Override
    public void initialize() {
      toggleShooter(true);
      spinUpTimer.stop();
      spinUpTimer.reset();
      spinUpTimer.start();
    }

    @Override
    public void end(boolean interrupted) {
      spinUpTimer.stop();
      toggleShooter(false);
    }

    @Override
    public boolean isFinished() {
      return spinUpTimer.hasElapsed(spinupTime);
    }
  }
}
