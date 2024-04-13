// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class Drivetrain extends SubsystemBase {
  private static Drivetrain instance = null;
  
  private final Talon left_motor;
  private final Talon right_motor;

  private double left_motor_velo = 0;
  private double right_motor_velo = 0;

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

    left_motor.setInverted(false);
    right_motor.setInverted(true); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (Math.abs(RobotContainer.controller1.getLeftX()) > deadzone && Math.abs(RobotContainer.controller1.getRightY()) > deadzone) {
      left_motor_velo = 0; 
      right_motor_velo = 0;
    }
  }

  public void drive(double percent_x, double percent_y) {
    if (Math.abs(percent_y) > deadzone) {
      left_motor_velo = percent_y * maxSpeed;
      right_motor_velo = percent_y * maxSpeed;
    }

    if (Math.abs(percent_x) > deadzone) {
      left_motor_velo = Math.signum(percent_x) * percent_x * maxSpeed;
      right_motor_velo = -Math.signum(percent_x) * percent_x * maxSpeed;
    }

    left_motor.set(left_motor_velo);
    right_motor.set(right_motor_velo);
  }
  /*
   * public void drive(double percent_x, double percent_y) {
      left_motor.set(Math.abs(percent_y) > deadzone ? (percent_y * maxSpeed + (Math.abs(percent_x) > deadzone ? Math.signum(percent_x) * percent_x * maxSpeed : percent_y * maxSpeed)) / 2 : (Math.abs(percent_x) > deadzone ? Math.signum(percent_x) * percent_x * maxSpeed : 0));
      right_motor.set(Math.abs(percent_y) > deadzone ? (percent_y * maxSpeed + (Math.abs(percent_x) > deadzone ? -Math.signum(percent_x) * percent_x * maxSpeed : percent_y * maxSpeed)) / 2 : (Math.abs(percent_x) > deadzone ? -Math.signum(percent_x) * percent_x * maxSpeed : 0));
    }
   */

  public double getGyroAngle() {
    return navX.getAngle();
  }
}
