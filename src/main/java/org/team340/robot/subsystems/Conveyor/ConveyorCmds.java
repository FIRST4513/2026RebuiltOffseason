package org.team340.robot.subsystems.Conveyor;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.team340.robot.Robot;
import org.team340.robot.subsystems.Conveyor.ConveyorSubSys.ConveyorState;

public class ConveyorCmds {

    public static Command ConveyorStopCmd() {
        return new InstantCommand(() -> Robot.conveyor.stopMotors());
    }

    public static Command ConveyorSetState(ConveyorState newState) {
        return new InstantCommand(() -> Robot.conveyor.setNewState(newState));
    }
}
