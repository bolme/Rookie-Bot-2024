// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// Intake and Shooter related
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.RelativeEncoder;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;

// Color sensor related
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.ColorSensorV3;
//// IntakeShooter
public class IntakeShooter extends SubsystemBase {

    public static IntakeShooter instance = null;

    /**
     * The motor that controls the intake.
     */
    public WPI_TalonSRX intakeMotor = new WPI_TalonSRX(Constants.MotorIds.intake);

    /**
     * Flywheel 1 on the shooter.
     */
    public WPI_TalonSRX shooterA = new WPI_TalonSRX(Constants.MotorIds.leftFlywheel);


    /**
     * Flywheel 2 on the shooter.
     */
    public WPI_TalonSRX shooterB = new WPI_TalonSRX(Constants.MotorIds.rightFlywheel);


    // Change the I2C port below to match the connection of the color sensor.
    /**
     * The I2C port the proximity sensor is connected to.
     */

    /** 
     * This object is constructed with an I2C port as a parameter.
     * This device will be automatically initialized with default parameters.
     */
    private final DigitalInput proximitySensor = new DigitalInput(1);

    /**
     * True if a piece is in the intake.
     */
    public static boolean holdingPiece = false;
    public static boolean pieceDetected = false;

    /**
     * Returns the instance.
     */
    public static synchronized IntakeShooter getInstance() {
        if (instance == null) {
            instance = new IntakeShooter();
        }
        return instance;
    }

    // Sets the motor to neutral on creation of the class.
    public IntakeShooter() {
    }

    /** 
     * Sets the speed of the intake motor through voltage.
     */
    public void setIntakeVoltage(double voltage) {
        intakeMotor.setVoltage(-voltage);
        if(voltage != 0) {
            System.out.println("Intaking");
        }
    }


    /** 
     * Sets the speed of the shooter's motor, make sure one is negative and one is postive.
     */
    public void setShooterVoltage(double voltage) {
        shooterA.setVoltage(voltage);
        shooterB.setVoltage(voltage);
        if(voltage != 0) {
            System.out.println("Shooting");
        }
    }
    
    /** 
     * True when a note reaches the sensor.
     */
    public static boolean proximityThresholdExeeded;
    ColorMatch matcher = new ColorMatch();
    
    final int detectThreshold = Constants.detectThreshold;
    @Override
    public void periodic() {
        
        // The method getProximity() returns a value 0 - 2047, with the closest being .
        pieceDetected = !proximitySensor.get();

        //Open Smart Dashboard to see the color detected by the sensor.
        SmartDashboard.putBoolean("NoteDetected", pieceDetected);
    }
//fuck you grace - joseph
}
