// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class Drivetrain extends SubsystemBase {
  
  public static boolean autonomous = true;
  private static Drivetrain instance = null;
  private static DifferentialDrive differentialDrive;
  private final WPI_TalonSRX left_motor;
  private final WPI_TalonSRX left_follow_motor;
  private final WPI_TalonSRX right_motor;
  private final WPI_TalonSRX right_follow_motor;

  private double left_motor_velo = 0;
  private double right_motor_velo = 0;

  private final AHRS navX = new AHRS(SPI.Port.kMXP);

  // Final values
  private final double maxSpeed = 1;

  public static Drivetrain getInstance() {
    instance = (instance != null) ? instance : new Drivetrain(); 
    
    return instance; 
  }

  private Drivetrain() {
    left_motor = new WPI_TalonSRX(Constants.MotorIds.leftDrivetrainLeader);
    right_motor = new WPI_TalonSRX(Constants.MotorIds.rightDrivetrainLeader);
    left_follow_motor = new WPI_TalonSRX(Constants.MotorIds.leftDrivetrainFollower);
    right_follow_motor = new WPI_TalonSRX(Constants.MotorIds.leftDrivetrainLeader);

    left_follow_motor.follow(left_motor);
    right_follow_motor.follow(right_motor);


    differentialDrive = new DifferentialDrive(left_motor, right_motor);


    left_motor.setInverted(true);
  }

  @Override
  public void periodic() {
    if(autonomous) return;
    
    this.drive(Robot.xbox.getRightX(), Robot.xbox.getLeftY());
  }

  public void drive(double percent_x, double percent_y) {
    differentialDrive.arcadeDrive(percent_x, percent_y);
  }

  public double getGyroAngle() {
    return navX.getAngle();
  }
}
