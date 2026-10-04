package org.team340.robot.subsystems.ShooterFeeder;

import org.team340.robot.Robot;
import org.team340.robot.subsystems.ShooterFeeder.ShooterFeederSubSys.ShooterFeederState;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ShooterFeederCmds {
    
    public static Command shooterFeederStopCmd() {
        return new InstantCommand(() -> Robot.shooterFeeder.stopMotors());
    }

    public static Command shooterFeederSetState(ShooterFeederState newState) {
        return new InstantCommand(() -> Robot.shooterFeeder.setNewState(newState));
    }
}
