// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  private final double P = 0.075;
  private final double I = 0.075;
  private final double D = 0.01;

  private PIDController pid;
  private double pidOutput;

  public static double pidError = 0;

  public static double targetAngle;

  public static Pose2d robotPose;

  public static Optional<Alliance> alliance;

  public static enum Position {
    AMP_SIDE_SPEAKER, MIDDLE_SPEAKER, SOURCE_SIDE_SPEAKER, CUSTOM_MOBILITY, CUSTOM
  }

  public static Position position = Position.MIDDLE_SPEAKER;

  private final double maxSpeed = 1;
  private Field2d field;
  private static DifferentialDriveOdometry odometry;

  public static Drivetrain getInstance() {
    instance = (instance != null) ? instance : new Drivetrain();

    return instance;
  }

  private Drivetrain() {
    pid = new PIDController(P, I, D);
    targetAngle = getAngle();

    NetworkTableEntry nSetpoint = inst.getTable("Drivetrain").getEntry("Setpoint");
    nSetpoint.setPersistent();

    left_motor = new WPI_TalonSRX(Constants.MotorIds.leftDrivetrainLeader);
    right_motor = new WPI_TalonSRX(Constants.MotorIds.rightDrivetrainLeader);
    left_follow_motor = new WPI_TalonSRX(Constants.MotorIds.leftDrivetrainFollower); // |
    right_follow_motor = new WPI_TalonSRX(Constants.MotorIds.rightDrivetrainFollower); // v
    // why did i do this, this is why a hole is in the wall: right_follow_motor =
    // new WPI_TalonSRX(Constants.MotorIds.leftDrivetrainLeader);

    left_follow_motor.follow(left_motor);
    right_follow_motor.follow(right_motor);

    left_motor.setNeutralMode(NeutralMode.Brake);
    right_motor.setNeutralMode(NeutralMode.Brake);
    left_follow_motor.setNeutralMode(NeutralMode.Brake);
    right_follow_motor.setNeutralMode(NeutralMode.Brake);

    differentialDrive = new DifferentialDrive(left_motor, right_motor);
    Drivetrain.odometry = new DifferentialDriveOdometry(
        getRotation2d(),
        getLeftDistance(),
        getRightDistance(),
        new Pose2d(0.0, 0, new Rotation2d()));
    field = new Field2d();
    SmartDashboard.putData("Field", field);

    left_motor.setInverted(true);
  }

  @Override
  public void periodic() {
    inst.getTable("Drivetrain").getEntry("RawGyroX").setDouble(navX.getRawGyroX());
    inst.getTable("Drivetrain").getEntry("RawGyroY").setDouble(navX.getRawGyroY());
    inst.getTable("Drivetrain").getEntry("RawGyroZ").setDouble(navX.getRawGyroZ());
    inst.getTable("Drivetrain").getEntry("RawAccelX").setDouble(navX.getRawAccelX());
    inst.getTable("Drivetrain").getEntry("RawAccelY").setDouble(navX.getRawAccelY());
    inst.getTable("Drivetrain").getEntry("RawAccelZ").setDouble(navX.getRawAccelZ());
    inst.getTable("Drivetrain").getEntry("RawMagX").setDouble(navX.getRawMagX());
    inst.getTable("Drivetrain").getEntry("RawMagY").setDouble(navX.getRawMagY());
    inst.getTable("Drivetrain").getEntry("RawMagZ").setDouble(navX.getRawMagZ());
    inst.getTable("Drivetrain").getEntry("Pitch").setDouble(navX.getPitch());
    inst.getTable("Drivetrain").getEntry("Roll").setDouble(navX.getRoll());
    inst.getTable("Drivetrain").getEntry("Yaw").setDouble(navX.getYaw());
    inst.getTable("Drivetrain").getEntry("CompassHeading").setDouble(navX.getCompassHeading());


    double TiltAngle = getTiltAngle();
    inst.getTable("Drivetrain").getEntry("TiltAngle").setDouble(TiltAngle);

    double currentAngle = getAngle();
    inst.getTable("Drivetrain").getEntry("Angle").setDouble(currentAngle);
    inst.getTable("Drivetrain").getEntry("Setpoint").setDouble(targetAngle);

    inst.getTable("Drivetrain").getEntry("Left Distance")
        .setDouble((left_motor.getSelectedSensorPosition()) * Constants.encoderRotationToMeters);
    inst.getTable("Drivetrain").getEntry("Right Distance")
        .setDouble((right_follow_motor.getSelectedSensorPosition()) * Constants.encoderRotationToMeters);
    if (autonomous) {
      targetAngle = Math.max(-180, Math.min(targetAngle, 180));
      pid.setSetpoint(targetAngle);
      pidError = Math.abs(currentAngle - targetAngle);
      pidOutput = pid.calculate(currentAngle);// * (pidError > 0 ? 0 : -1);

      return;
    } else {
      pidOutput = 0;
    }
    Drivetrain.robotPose = Drivetrain.odometry.update(
        getRotation2d(),
        getLeftDistance(),
        getRightDistance());
    field.setRobotPose(Drivetrain.odometry.getPoseMeters());
    this.drive(Robot.xbox.getRightX(), Robot.xbox.getLeftY());
  }

  public void drive(double percent_x, double percent_y) {
    differentialDrive.arcadeDrive(percent_x + pidOutput, percent_y);
  }

  public double getTiltAngle() {
    return navX.getRoll(); // *180/Math.PI;
  }

  public double getAngle() {
    return navX.getAngle() % 180; // TODO: I think this is wrong
  }

  public double getAbsoluteAngle() {
    return navX.getAngle();
  }

  public Rotation2d getRotation2d() {
    return navX.getRotation2d();
  }

  public void resetGyro() {
    navX.reset();
    targetAngle = getAngle();
  }

  public void resetPositionToZero() {
    Drivetrain.setOdometryPosition(0, 0, 0);
  }

  public double getLeftDistance() {
    return (left_motor.getSelectedSensorPosition()) * Constants.encoderRotationToMeters;
  }

  public double getRightDistance() {
    return (right_follow_motor.getSelectedSensorPosition()) * Constants.encoderRotationToMeters;
  }

  public static void setOdometryPosition(double x, double y, double rotation) {
    Drivetrain drtr = Drivetrain.getInstance();
    odometry.resetPosition(drtr.getRotation2d(), drtr.getLeftDistance(), drtr.getRightDistance(),
        new Pose2d(x, y, new Rotation2d(Math.toRadians(rotation))));
  }

  public static void setOdometryBasedOnPosition() {
    alliance = DriverStation.getAlliance();
    if (alliance.get() == Alliance.Blue) {
      switch (position) {
        case AMP_SIDE_SPEAKER:
          // TODO: Update to correct pose
          Drivetrain.setOdometryPosition(0.8, 6.7, 60);
          break;
        case MIDDLE_SPEAKER:
          Drivetrain.setOdometryPosition(1.36, 5.5, 0);
          break;
        case SOURCE_SIDE_SPEAKER:
          // TODO: Update to correct pose
          Drivetrain.setOdometryPosition(0.8, 4.4, -60);
          break;
      }
    } else if (alliance.get() == Alliance.Red) {
      switch (position) {
        case AMP_SIDE_SPEAKER:
          // TODO: Update to correct pose
          Drivetrain.setOdometryPosition(0, 0, 0);
          break;
        case MIDDLE_SPEAKER:
          // TODO: Update to correct pose
          Drivetrain.setOdometryPosition(0, 0, 0);
          break;
        case SOURCE_SIDE_SPEAKER:
          // TODO: Update to correct pose
          Drivetrain.setOdometryPosition(0, 0, 0);
          break;
      }
    }
  }
}
