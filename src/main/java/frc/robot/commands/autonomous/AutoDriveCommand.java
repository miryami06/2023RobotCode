// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveCommand extends CommandBase {
  private DoubleSupplier distanceSupplier;
  private DriveSubsystem driveSubsystem;

  /** Creates a new AutoDriveCommand. */
  public AutoDriveCommand(DoubleSupplier distance, DriveSubsystem driveSubsystem) {
    this.distanceSupplier = distance;

    driveSubsystem.resetEncoders();

    this.driveSubsystem = driveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
  }

  public AutoDriveCommand(double distance, DriveSubsystem driveSubsystem) {
    this(() -> distance, driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveSubsystem.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.autoDrive(distanceSupplier.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return driveSubsystem.distanceReached(distanceSupplier.getAsDouble());
  }
}
