package org.team340.robot.subsystems.Conveyor;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team340.robot.Constants.RobotMap;

public class ConveyorSubSys extends SubsystemBase {

    public TalonFX ConveyorMotor = new TalonFX(RobotMap.CONVEYOR, "CANivore");

    public enum ConveyorState {
        STOPPED,
        FEEDING,
        BACKWARDS
    }

    private static ConveyorState state = ConveyorState.STOPPED;

    public ConveyorSubSys() {
        stopMotors();
    }

    @Override
    public void periodic() {
        switch (state) {
            case FEEDING:
                ConveyorMotor.set(0.25);
                break;
            case BACKWARDS:
                ConveyorMotor.set(-0.05);
                break;
            default:
                ConveyorMotor.stopMotor();
                break;
        }
    }

    //get set stuff
    public void setNewState(ConveyorState newState) {
        state = newState;
    }

    public void stopMotors() {
        ConveyorMotor.stopMotor();
        state = ConveyorState.STOPPED;
    }
}
