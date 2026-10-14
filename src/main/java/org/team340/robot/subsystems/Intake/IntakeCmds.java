package org.team340.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.team340.robot.Robot;
import org.team340.robot.subsystems.Intake.IntakeSubsys.IntakeState;

public class IntakeCmds {

    /* ----- Intake Stop Command ----- */
    public static Command intakeStopCmd() {
        return new InstantCommand(() -> Robot.intake.stopMotors());
    }

    /* ----- Intake Set State Commands ----- */
    public static Command intakeSetState(IntakeState newState) {
        return new InstantCommand(() -> Robot.intake.setNewState(newState));
    }

    public static Command intakeSetStoppedCmd() {
        return new SequentialCommandGroup(new WaitCommand(0.25), intakeSetState(IntakeState.STOPPED));
    }
}
