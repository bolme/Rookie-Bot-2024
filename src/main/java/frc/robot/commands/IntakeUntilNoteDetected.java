package frc.robot.commands;

//import edu.wpi.first.wpilibj.PowerDistribution;
//import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.IntakeShooter;

public class IntakeUntilNoteDetected extends Command {

  IntakeShooter intakeShooter = IntakeShooter.getInstance();

  //PowerDistribution pdp = new PowerDistribution(1, ModuleType.kRev);
  //long startTime;

  public IntakeUntilNoteDetected() { }

  @Override
  public void initialize() {
    //startTime = System.currentTimeMillis();

    Arm.getInstance().setAngle(Constants.ArmAngles.intakeAngle);
    intakeShooter.setIntakeVoltage(Constants.intakeVoltage);
  }

  public void execute(){
    // TODO: Test if this is needed, it seems to be working without it
    // //Starts running the intake at a slower speed when there is a current spike; The color sensor takes some time to recognize it. 
    // if(pdp.getCurrent(17) > 4 && System.currentTimeMillis() - startTime > 1000){
    //   intakeShooter.setIntakeVoltage(.5);
      
    // }
  }

  @Override
  public void end(boolean interrupted) {
    IntakeShooter.holdingPiece = !interrupted;
    intakeShooter.setIntakeVoltage(0);
  }

  @Override
  public boolean isFinished() {
    return IntakeShooter.pieceDetected;
  }

}