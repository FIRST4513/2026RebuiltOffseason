package org.team340.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.team340.robot.Robot;
import org.team340.robot.subsystems.Shooter.ShooterSubsys.ShooterState;

public class ShooterCmds {

    public static Command shooterStopCmd() {
        return new InstantCommand(() -> Robot.shooter.stopMotors());
    }

    public static Command shooterSetState(ShooterState newState) {
        return new InstantCommand(() -> Robot.shooter.setNewState(newState));
    }

    // public static Command shooterSpeedUp() {
    //     return new InstantCommand(() -> ShooterConfig.speedUp());
    // }

    // public static Command shooterSpeedDown() {
    //     return new InstantCommand(() -> ShooterConfig.speedDown());
    // }

    public static Command autoShooterSpeedUp() {
        return new InstantCommand(() -> ShooterConfig.autoSpeedUp());
    }

    public static Command autoShooterSpeedDown() {
        return new InstantCommand(() -> ShooterConfig.autoSpeedDown());
    }

    public void shooterAuto() {
        //dth = Robot.distanceToHub();
    }
}
