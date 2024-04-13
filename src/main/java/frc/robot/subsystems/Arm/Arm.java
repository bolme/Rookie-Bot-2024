// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Arm;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  /** Creates a new Arm. */
  private final Talon armMotorL;
  private final Talon armMotorR;
  private final DutyCycleEncoder armEncoder = new DutyCycleEncoder(1);

  private final PIDController angleController = new PIDController(1, 0, 0);
  private double setPoint;
  private double minAng = 0;
  private double maxAng = 180;

  public Arm(int armIdL, int armIdR) {
    armMotorL = new Talon(armIdL);
    armMotorR = new Talon(armIdR);

    armMotorL.setInverted(false);
    armMotorR.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // arm will continuously move to setPoint
    if (setPoint > minAng && setPoint < maxAng) {
      angleController.setSetpoint(setPoint);

      double update = angleController.calculate(armEncoder.getAbsolutePosition());

      armMotorL.set(update);
      armMotorR.set(update);
    }
  }

  public void setArmAngle(double setPoint) {
    this.setPoint = setPoint;
  }
}
