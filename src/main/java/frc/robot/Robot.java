// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer  m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    armRightMotor5.setInverted(false);
    armLeftMotor6.setInverted(true);
    driveBackRightMotor3.setInverted(false);
    driveFrontRightMotor4.setInverted(false);
    driveBackLeftMotor2.setInverted(true);
    //Number 4 was factory reset, may fix it, may need to put it back to true (This is motor Back Left)
    //Unsure why one is true, could be that it is hardcoded to be inverted- Jason
    driveFrontLeftMotor1.setInverted(false);
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    driveFrontRightMotor4.set(ControlMode.PercentOutput, 0);
    driveFrontLeftMotor1.set(ControlMode.PercentOutput, 0);
    driveBackRightMotor3.set(ControlMode.PercentOutput, 0);
    driveBackRightMotor3.set(ControlMode.PercentOutput, 0);
    armLeftMotor6.set(ControlMode.PercentOutput, 0);
    armRightMotor5.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
   // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}


  public static TalonSRX driveBackRightMotor3 = new TalonSRX(3);
  public static TalonSRX driveFrontRightMotor4 = new TalonSRX(4);
  public static TalonSRX driveBackLeftMotor2 = new TalonSRX(2);
  public static TalonSRX driveFrontLeftMotor1 = new TalonSRX(1);
  
  public static TalonSRX armLeftMotor6 = new TalonSRX(6);
  public static TalonSRX armRightMotor5 = new TalonSRX(5);
  XboxController controller = new XboxController(0);
  
  @Override
  public void teleopInit() {
    
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    driveFrontLeftMotor1.set(ControlMode.PercentOutput, controller.getLeftY() - controller.getRightX());
    // driveBackLeftMotor2.set(ControlMode.PercentOutput, controller.getLeftY() - controller.getRightX());

    // driveFrontRightMotor4.set(ControlMode.PercentOutput, controller.getLeftY() + controller.getRightX());
    // driveBackRightMotor3.set(ControlMode.PercentOutput, controller.getLeftY() + controller.getRightX());

    
    
  }

  @Override
  public void testInit() {
    driveBackRightMotor3.set(ControlMode.PercentOutput, .5);
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
