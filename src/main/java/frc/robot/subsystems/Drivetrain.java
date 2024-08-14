// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class Drivetrain extends SubsystemBase {
  private static Drivetrain instance = null;
  private static DifferentialDrive differentialDrive;
  private final WPI_TalonSRX left_motor;
  private final WPI_TalonSRX left_follow_motor;
  private final WPI_TalonSRX right_motor;
  private final WPI_TalonSRX right_follow_motor;

  private double left_motor_velo = 0;
  private double right_motor_velo = 0;

  private final double deadzone;

  private final AHRS navX = new AHRS(SPI.Port.kMXP);

  // Final values
  private final double maxSpeed = 1;

  public static Drivetrain getInstance() {
    if (instance == null) {
      instance = new Drivetrain(1, 2, 3, 4, 0.25); 
    }
      return instance; 
  }

  private Drivetrain(int left_motor_id, int secondary_left_id, int right_motor_id, int secondary_right_id, double deadzone) {
    left_motor = new WPI_TalonSRX(left_motor_id);
    right_motor = new WPI_TalonSRX(right_motor_id);
    left_follow_motor = new WPI_TalonSRX(secondary_left_id);
    right_follow_motor = new WPI_TalonSRX(secondary_right_id);

    left_follow_motor.follow(left_motor);
    right_follow_motor.follow(right_motor);


    differentialDrive = new DifferentialDrive(left_motor, right_motor);

    this.deadzone = deadzone; 

    left_motor.setInverted(true);
    left_follow_motor.setInverted(true);
  }

  @Override
  public void periodic() {

    this.drive(RobotContainer.controller1.getLeftX(), RobotContainer.controller1.getLeftY());

  }

  public void drive(double percent_x, double percent_y) {
    differentialDrive.arcadeDrive(percent_x, percent_y);
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
