// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Robot;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;


public class Arm extends SubsystemBase {
  /** Creates a new Arm. */
  private final WPI_TalonSRX armMotorL;
  private final WPI_TalonSRX armMotorR;
  private final DutyCycleEncoder armEncoder = new DutyCycleEncoder(0);

  private final PIDController angleController = new PIDController(.2, 0, 0);
  private double setPoint;
  private double minAng = 0.6; 
  private double maxAng = 0.91;
  public double deadzone = 0.1;

  public Arm(int armIdL, int armIdR) {
    armMotorL = new WPI_TalonSRX(armIdL);
    armMotorR = new WPI_TalonSRX(armIdR);
    setPoint = armEncoder.getAbsolutePosition();
    armMotorL.setInverted(false);
    armMotorR.setInverted(true);
  }

  @Override
  public void periodic() {
    if(!Robot.teleop) return;
    // This method will be called once per scheduler run
    // arm will continuously move to setPoint
    if(Math.abs(RobotContainer.controller1.getRightY()) > deadzone) {
      this.changeArmAngle(RobotContainer.controller1.getRightY() / 1000);
    }
    if (setPoint > minAng && setPoint < maxAng) {
      angleController.setSetpoint(setPoint * 360);

      double update = angleController.calculate(armEncoder.getAbsolutePosition() * 360);


      armMotorL.set(update);
      armMotorR.set(update);
    }
    System.out.println("Setpoint: " + this.setPoint + " | position: " + armEncoder.getAbsolutePosition());
  }

  public void changeArmAngle(double delta) {
    this.setPoint += delta;
    this.setPoint = Math.min(Math.max(this.setPoint, minAng), maxAng);
    System.out.print("Setpoint set to: ");
    System.out.println(this.setPoint);
  }
}
