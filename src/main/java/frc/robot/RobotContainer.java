
package frc.robot;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AmpShoot;
import frc.robot.commands.DefaultShoot;
import frc.robot.commands.IntakeUntilNoteDetected;
import frc.robot.commands.SetArmToAngle;
import frc.robot.commands.SpeakerShoot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IntakeShooter;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;


/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
 // public static XboxCotroller controller = new XboxCotroller(0);
 // public static AHRS gyro = new AHRS(Port.kMXP);
 // public static SwerveDrive swerve = new SwerveDrive();
 // private final Camera camera;
 // SendableChooser<Command> autoChooser = new SendableChooser<>();

  public static XboxController xbox = Robot.xbox;
  public static IntakeShooter intakeShooter;
  public static Drivetrain drivetrain;
  public static Arm arm;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    intakeShooter = IntakeShooter.getInstance();
    drivetrain = Drivetrain.getInstance();
    arm = Arm.getInstance();

    autoChooser.addOption("Mobility", new MobilityAuto());
    SmartDashboard.putData("Auto", autoChooser);
 
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
 
    new JoystickButton(xbox, Button.kX.value).onTrue(new InstantCommand(()->{ Arm.getInstance().setAngle(Constants.ArmAngles.intakeAngle); }));
    new JoystickButton(xbox, Button.kB.value).onTrue(new InstantCommand(()->{ Arm.getInstance().setAngle(Constants.ArmAngles.stowedAngle); }));


    new JoystickButton(xbox, Button.kA.value).onTrue(new AmpShoot());
    new JoystickButton(xbox, Button.kY.value).onTrue(new SpeakerShoot());
    IntakeUntilNoteDetected intakeCommand = new IntakeUntilNoteDetected();
    new JoystickButton(xbox, Button.kLeftBumper.value)
          .onTrue(intakeCommand)
          .onFalse(new InstantCommand(()->{ 
              intakeCommand.cancel();
            }));
  } 


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   * 
   */

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }

}
