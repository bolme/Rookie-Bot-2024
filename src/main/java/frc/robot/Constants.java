package frc.robot;

import edu.wpi.first.math.util.Units;

public interface Constants {

  public static final double intakeVoltage = 10;
  public static final double gearRatio = 6.12;
  public static final int detectThreshold = 1000;
  public static final double botMass = 24.4;
  public static final double wheelDiameter = .1016;
  public static final double botLength = Units.inchesToMeters(29);
  public static final double maxSpeed = 5.05968; // In meters per second, determined from the free speed of the bot via SwerveDriveSpecialties
  public static final double maxTurnSpeed = Double.MAX_VALUE; //These are basically infinite for our purposes 
  public static final double maxAcceleration = 4000;
  public static final double botRadius = Math.hypot(botLength, botLength);
  public static final double maxChassisTurnSpeed = maxSpeed/botRadius; // Max Speed divided by the circumference a circle determined by the distance of the module from the center, divided by 2 pi to convert to radians
  public double encoderRotationToMeters = 2*Math.PI*((wheelDiameter/2)/gearRatio)/42;

  public static class MotorIds {
    public static final int leftDrivetrainLeader = 1;
    public static final int leftDrivetrainFollower = 2;  
    public static final int rightDrivetrainLeader = 4;
    public static final int rightDrivetrainFollower = 3;

    public static final int leftArm = 6;
    public static final int rightArm = 5;

    public static final int intake = 7;

    public static final int leftFlywheel = 8;
    public static final int rightFlywheel = 9;
  }

  public static final int ArmEncoderID = 0;
  
  public static class ArmAngles {
    public static final double ampAngle = 90.0d;
    public static final double speakerAngle = 8.0d;
    public static final double intakeAngle = 0.0d;
    public static final double stowedAngle = 68.0d;
  }


}