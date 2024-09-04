package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.IntakeShooter;
import frc.robot.RobotContainer;


public class Robot extends TimedRobot implements Constants{

  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  private IntakeShooter intakeShooter = IntakeShooter.getInstance();
  public static XboxController xbox = new XboxController(0);

  
  @Override
  public void teleopPeriodic() { }

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }


  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    Arm.getInstance().disable();
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    Arm.getInstance().enable();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    } 
  }

  @Override
  public void autonomousPeriodic() { }

  @Override
  public void teleopInit() {
    Arm.getInstance().enable();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();

    Arm.getInstance().enable();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}

}