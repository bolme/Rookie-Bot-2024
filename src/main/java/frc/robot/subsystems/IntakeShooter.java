package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeShooter extends SubsystemBase {

    public static IntakeShooter instance = null;
    
    public WPI_TalonSRX intakeMotor = new WPI_TalonSRX(Constants.MotorIds.intake);
    public WPI_TalonSRX shooterA = new WPI_TalonSRX(Constants.MotorIds.leftFlywheel);
    public WPI_TalonSRX shooterB = new WPI_TalonSRX(Constants.MotorIds.rightFlywheel);

    private final DigitalInput proximitySensor = new DigitalInput(1);

    public static boolean holdingPiece = false;
    public static boolean pieceDetected = false; 

    public static synchronized IntakeShooter getInstance() {
        instance = (instance != null) ? instance : new IntakeShooter(); 
        return instance;
    }

    public IntakeShooter() { }

    public void setIntakeVoltage(double voltage) {
        // Voltage is inverted because it doesn't work when the voltage is positive for some reason ¯\_(ツ)_/¯
        intakeMotor.setVoltage(-voltage);
    }

    public void setShooterVoltage(double voltage) {
        shooterA.setVoltage(voltage);
        shooterB.setVoltage(voltage);
    }
    

    @Override
    public void periodic() {
        pieceDetected = !proximitySensor.get();
        SmartDashboard.putBoolean("NoteDetected", pieceDetected);
    }
//fuck you grace - joseph
//STAY AWAY FROM MY PRINTERSSSSSSS - kay
}