// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private static Drivetrain instance = null;
  
  private final Talon left_motor;
  private final Talon right_motor;

  private final double deadzone;

  private final AHRS navX = new AHRS(SPI.Port.kMXP);

  // Final values
  private final double maxSpeed = 1;

  public static Drivetrain getInstance() {
    if (instance == null) {
      instance = new Drivetrain(1, 2, 0.1); 
    }
      return instance; 
  }

  private Drivetrain(int left_motor_id, int right_motor_id, double deadzone) {
    left_motor = new Talon(left_motor_id);
    right_motor = new Talon(right_motor_id);
    this.deadzone = deadzone;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double joystick_x, double joystick_y) {
    if (Math.abs(joystick_x) > deadzone) {
      left_motor.set(joystick_x * maxSpeed);
      right_motor.set(-(joystick_x * maxSpeed));
    }

    if (Math.abs(joystick_y) > deadzone) {
      if (Math.signum(joystick_y) < 0) {
        left_motor.set(-(joystick_y * maxSpeed));
        right_motor.set(joystick_y * maxSpeed);
      } else {
        left_motor.set(joystick_y * maxSpeed);
        right_motor.set(-(joystick_y * maxSpeed));
      }
    }
  }

  public double getGyroAngle() {
    return navX.getAngle();
  }
}
