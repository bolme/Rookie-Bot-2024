
package frc.robot;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AmpShoot;
import frc.robot.commands.DefaultShoot;
import frc.robot.commands.IntakeUntilNoteDetected;
import frc.robot.commands.LobShoot;
import frc.robot.commands.SetArmToAngle;
import frc.robot.commands.SpeakerShoot;
import frc.robot.commands.autos.MobilityAuto;
import frc.robot.commands.autos.OneNoteAndMobility;
import frc.robot.commands.autos.OneNoteAuto;
import frc.robot.commands.autos.TestAuto;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IntakeShooter;

import javax.xml.namespace.QName;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
  SendableChooser<Command> autoChooser = new SendableChooser<>();
  SendableChooser<Command> positionChooser = new SendableChooser<>();
  SendableChooser<Drivetrain.Alliance> allianceChooser = new SendableChooser<>();

  public static XboxController xbox = Robot.xbox;
  public static IntakeShooter intakeShooter;
  public static Drivetrain drivetrain;
  public static Arm arm;

  public RobotContainer() {
    intakeShooter = IntakeShooter.getInstance();
    drivetrain = Drivetrain.getInstance();
    arm = Arm.getInstance();

    autoChooser.addOption("Mobility", new MobilityAuto());
    autoChooser.addOption("OneNote", new OneNoteAuto());
    autoChooser.addOption("Middle Speaker and Mobily", new OneNoteAndMobility());
    autoChooser.addOption("Test", new TestAuto());

    positionChooser.addOption("Amp-Side Speaker", new InstantCommand(()=>{ 
        if(allianceChooser.getSelected() == Drivetrain.Alliance.BLUE) {
          Drivetrain.setOdometryPosition(0, 0, 0);
        } else {
          Drivetrain.setOdometryPosition(0, 0, 0);
        }
      }));
    positionChooser.addOption("Middle Speaker", new InstantCommand(()=>{ 
        if(allianceChooser.getSelected() == Drivetrain.Alliance.BLUE) {
          Drivetrain.setOdometryPosition(0, 0, 0);
        } else {
          Drivetrain.setOdometryPosition(0, 0, 0);
        }
      }));
    positionChooser.addOption("Source-Side Speaker", new InstantCommand(()=>{ 
        if(allianceChooser.getSelected() == Drivetrain.Alliance.BLUE) {
          Drivetrain.setOdometryPosition(0, 0, 0);
        } else {
          Drivetrain.setOdometryPosition(0, 0, 0);
        }
      }));

    allianceChooser.addOption("Red", Drivetrain.Alliance.RED);
    allianceChooser.addOption("Blue", Drivetrain.Alliance.BLUE);
    
    SmartDashboard.putData("Auto", autoChooser);
    SmartDashboard.putData("Position", positionChooser);
    SmartDashboard.putData("Alliance", allianceChooser);
 
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
 
    new JoystickButton(xbox, Button.kB.value)
      .onTrue(new InstantCommand(()->{ Arm.getInstance().setAngle(Constants.ArmAngles.safeAngle); }))
      .whileTrue(new SequentialCommandGroup(new WaitCommand(1), new SetArmToAngle(Constants.ArmAngles.stowedAngle)));
    
      new JoystickButton(xbox, Button.kX.value).onTrue(new LobShoot());
    new JoystickButton(xbox, Button.kY.value).whileTrue(new AmpShoot());
    new JoystickButton(xbox, Button.kA.value).whileTrue(new SpeakerShoot());
    IntakeUntilNoteDetected intakeCommand = new IntakeUntilNoteDetected();
    new JoystickButton(xbox, Button.kLeftBumper.value)
          .onTrue(intakeCommand)
          .onFalse(new InstantCommand(()->{ 
              intakeCommand.cancel();
            }));
    new JoystickButton(xbox, Button.kStart.value).onTrue(new InstantCommand(()->{ drivetrain.resetOdometry(); }));


    /** 
     * BINDINGS
     * Left joystick - fowards and backwards
     * Right joystick - turn left and right 
     * A (hold) - Shoot note into the speaker
     * B - Set arm to safe angle
     * B (hold)- Set Arm to stowed angle
     * Y - Shoot note into the amp
     * X (hold) - Set arm to intake angle
     * Left Bumper (Hold) - intake note
     * Start Button - reset gyro
     */
  } 


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   * 
   */

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
  public static Command getOdometryPositionCommand() {
    return positionChooser.getSelected()
  }
}
