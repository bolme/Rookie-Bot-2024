// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class Drivetrain extends SubsystemBase {
  private NetworkTableInstance inst = NetworkTableInstance.getDefault();
  public static boolean autonomous = false;
  private static Drivetrain instance = null;
  private static DifferentialDrive differentialDrive;
  private final WPI_TalonSRX left_motor;
  private final WPI_TalonSRX left_follow_motor;
  private final WPI_TalonSRX right_motor;
  private final WPI_TalonSRX right_follow_motor;

  private double left_motor_velo = 0;
  private double right_motor_velo = 0;

  private final AHRS navX = new AHRS(SPI.Port.kMXP);

  private final double P = 0.1;
  private final double I = 0;
  private final double D = 0.01;

  private PIDController pid;
  private double pidOutput;

  public static double pidError = 0;

  public static double targetAngle;

  private final double maxSpeed = 1;

  public static Drivetrain getInstance() {
    instance = (instance != null) ? instance : new Drivetrain(); 
    
    return instance; 
  }

  private Drivetrain() {
    pid = new PIDController(P, I, D);
    targetAngle = getAngle();
    NetworkTableEntry nAngle = inst.getTable("Drivetrain").getEntry("Angle");
    NetworkTableEntry nSetpoint = inst.getTable("Drivetrain").getEntry("Setpoint");
    nAngle.setPersistent();
    nSetpoint.setPersistent();


    left_motor = new WPI_TalonSRX(Constants.MotorIds.leftDrivetrainLeader);
    right_motor = new WPI_TalonSRX(Constants.MotorIds.rightDrivetrainLeader);
    left_follow_motor = new WPI_TalonSRX(Constants.MotorIds.leftDrivetrainFollower);                                  // |
    right_follow_motor = new WPI_TalonSRX(Constants.MotorIds.rightDrivetrainFollower);                                // v
    // why did i do this, this is why a hole is in the wall:  right_follow_motor = new WPI_TalonSRX(Constants.MotorIds.leftDrivetrainLeader); 

    left_follow_motor.follow(left_motor);
    right_follow_motor.follow(right_motor);

    left_motor.setNeutralMode(NeutralMode.Brake);
    right_motor.setNeutralMode(NeutralMode.Brake);
    left_follow_motor.setNeutralMode(NeutralMode.Brake);
    right_follow_motor.setNeutralMode(NeutralMode.Brake);


    differentialDrive = new DifferentialDrive(left_motor, right_motor);


    left_motor.setInverted(true);
  }

  @Override
  public void periodic() {
    double currentAngle = getAngle();
    inst.getTable("Drivetrain").getEntry("Angle").setDouble(currentAngle);
    inst.getTable("Drivetrain").getEntry("Setpoint").setDouble(targetAngle);
    if(autonomous) {
      pid.setSetpoint(targetAngle);
      pidError = Math.abs(currentAngle - targetAngle);
      pidOutput = pid.calculate(currentAngle);// * (pidError > 180 ? 0 : -1);
      
      return;
    } else {
      pidOutput = 0;
    }
    
    this.drive(Robot.xbox.getRightX(), Robot.xbox.getLeftY());
  }

  public void drive(double percent_x, double percent_y) {
    differentialDrive.arcadeDrive(percent_x + pidOutput, percent_y);
  }

  public double getAngle() {
    return navX.getAngle() % 360;
  }
  public double getAbsoluteAngle() {
    return navX.getAngle();
  }
  
  public void resetGyro() {
    navX.reset();
    targetAngle = getAngle();
  }
}
