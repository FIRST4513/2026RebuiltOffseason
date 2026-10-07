package org.team340.robot.subsystems.Pivot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.team340.robot.Robot;
import org.team340.robot.subsystems.Pivot.PivotSubsys.PivotState;

public class PivotCmds {

    public static Command setupDefaultCommand() {
        return new InstantCommand(() -> Robot.pivot.setDefaultCommand(pivotSetStowCmd()));
    }

    /* ----- pivot Set State Commands ----- */
    public static Command pivotSetState(PivotState newState) {
        return new InstantCommand(() -> Robot.pivot.setNewState(newState));
    }

    public static Command pivotSetStowCmd() {
        return pivotSetState(PivotState.STOW);
    }
}
