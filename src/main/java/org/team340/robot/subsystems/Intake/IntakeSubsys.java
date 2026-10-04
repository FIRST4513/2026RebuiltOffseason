package org.team340.robot.subsystems.Intake;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import org.team340.lib.util.command.GRRSubsystem;
import org.team340.robot.Constants.RobotMap;

public class IntakeSubsys extends GRRSubsystem {

    public SparkMax intakeMotor = new SparkMax(RobotMap.INTAKE, MotorType.kBrushless);

    public enum IntakeState {
        STOPPED,
        INTAKE,
    }

    private static IntakeState state = IntakeState.STOPPED;

    public IntakeSubsys() {
        stopMotors();
    }

    @Override
    public void periodic() {
        switch (state) {
            case INTAKE:
                intakeMotor.set(IntakeConfig.INTAKE);
                break;
            default:
                intakeMotor.stopMotor();
                break;
        }
    }

    // --------------------------------------------------------
    // ---------------- Intake Motor Methods ------------------
    // --------------------------------------------------------

    /* ----- Setters ----- */

    public void setNewState(IntakeState newState) {
        state = newState;
    }

    public void stopMotors() {
        intakeMotor.stopMotor();
        //intakeFeeder.stopMotor();
        state = IntakeState.STOPPED;
    }

    /* ----- Config ----- */
    public void configureNovaControllers() {
        //intakeTopMotor.setInverted(true);
    }
}
