// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Made AmpShoot the Y keybind on the xbox controller so we can test it

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.IntakeShooter;
//// IntakeShooter
public class AmpShoot extends Command implements Constants{
    public AmpShoot() { }

    @Override
    public void initialize() { }

    @Override
    public boolean isFinished() {
        return true;
    }
    
    @Override
    public void end(boolean interrupted) {
        new DefaultShoot(10, Constants.ArmAngles.ampAngle).schedule();
    }

}