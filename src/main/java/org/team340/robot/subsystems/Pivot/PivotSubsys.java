package org.team340.robot.subsystems.Pivot;


import org.team340.lib.util.command.GRRSubsystem;
import org.team340.robot.Constants.RobotMap;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

public class PivotSubsys extends GRRSubsystem {
    public TalonFX pivot = new TalonFX(RobotMap.PIVOT, "Canivore");
    public CANcoder pivotCaNcoder = new CANcoder(RobotMap.PIVOTENCODER, "Canivore");

    public enum PivotState{
        STOW,
        EXTEND,
        SPEAK
    }
    private static PivotState state = PivotState.STOW;
    final MotionMagicVoltage mr = new MotionMagicVoltage(0).withSlot(0);

    public PivotSubsys() {
        pivot.setPosition(pivotCaNcoder.getAbsolutePosition().getValueAsDouble());

        configureMotors();
        //pivot.setInverted(true);
    }

    @Override
    public void periodic() {
        switch (state) {
            case STOW:
                pivot.setControl(mr.withPosition(PivotConfig.STOW));
                break;
            case EXTEND:
                pivot.setControl(mr.withPosition(PivotConfig.EXTEND));
                break;
            case SPEAK:
                System.out.println(pivotCaNcoder.getAbsolutePosition());
                break;
            default:
            pivot.set(0);
                break;
        }
    }

    /* ----- Setters ----- */

    public void setNewState(PivotState newState) {
       state = newState;
    }

    /* ----- Config ----- */
    public void configureMotors() {
        pivot.getConfigurator().apply(PivotConfig.getConfig(pivotCaNcoder));
        
    }
}
