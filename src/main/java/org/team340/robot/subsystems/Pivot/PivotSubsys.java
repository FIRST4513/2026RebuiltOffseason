package org.team340.robot.subsystems.Pivot;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import org.team340.lib.util.command.GRRSubsystem;
import org.team340.robot.Constants.RobotMap;

public class PivotSubsys extends GRRSubsystem {

    public SparkMax pivot = new SparkMax(RobotMap.PIVOT, MotorType.kBrushless);
    public SparkClosedLoopController configController = pivot.getClosedLoopController();
    public RelativeEncoder encoder = pivot.getEncoder();

    public enum PivotState {
        STOW,
        EXTEND,
        MANOUT,
        MANIN,
        SHOOTING,
        STOPPED
    }

    private static PivotState state = PivotState.STOPPED;

    //final MotionMagicVoltage mr = new MotionMagicVoltage(0).withSlot(0);

    public PivotSubsys() {
        configureMotors();
    }

    @Override
    public void periodic() {
        switch (state) {
            case STOW:
                configController.setSetpoint(0.25, ControlType.kPosition);
                break;
            case EXTEND:
                configController.setSetpoint(24.5, ControlType.kPosition);
                break;
            case MANOUT:
                pivot.setVoltage(2);
                break;
            case MANIN:
                pivot.setVoltage(-5);
                break;
            case SHOOTING:
                configController.setSetpoint(12.25, ControlType.kPosition);
                break;
            case STOPPED:
                pivot.set(0);
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
        SparkMaxConfig config = new SparkMaxConfig();
        config.closedLoop
            .p(0.1)
            .i(0)
            .d(0)
            .maxOutput(0.3)
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .maxMotion.cruiseVelocity(1440);

        config.idleMode(IdleMode.kBrake);
        pivot.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
}
