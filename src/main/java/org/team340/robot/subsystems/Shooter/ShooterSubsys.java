package org.team340.robot.subsystems.Shooter;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team340.robot.Constants.RobotMap;

public class ShooterSubsys extends SubsystemBase {

    public TalonFX shooter1 = new TalonFX(RobotMap.SHOOTER1, RobotMap.LOWER_CAN);
    public TalonFX shooter2 = new TalonFX(RobotMap.SHOOTER2, RobotMap.LOWER_CAN);
    public TalonFX shooter3 = new TalonFX(RobotMap.SHOOTER3, RobotMap.LOWER_CAN);
    public TalonFX shooter4 = new TalonFX(RobotMap.SHOOTER4, RobotMap.LOWER_CAN);

    public enum ShooterState {
        STOPPED,
        MAN,
        AUTO,
        SNOWBLOW
    }

    private static ShooterState state = ShooterState.STOPPED;

    public ShooterSubsys() {
        stopMotors();
        marryMotors();
    }

    @Override
    public void periodic() {
        switch (state) {
            // case HUB:
            //     ShootMotor.set(ShooterConfig.HUB);
            //     break;
            case SNOWBLOW:
                shooter1.set(ShooterConfig.SNOWBLOW);
                break;
            case MAN:
                shooter1.set(ShooterConfig.MAN);
                break;
            case AUTO:
                shooter1.set(ShooterConfig.AUTO);
                break;
            default:
                shooter1.stopMotor();
                break;
        }
    }

    // get set stuff

    public void setNewState(ShooterState newState) {
        state = newState;
    }

    public static ShooterState getState() {
        return state;
    }

    public void stopMotors() {
        //ShootMotor.set(TalonSRXControlMode.PercentOutput, 0);
        state = ShooterState.STOPPED;
    }

    public void marryMotors() {
        shooter2.setControl(new Follower(RobotMap.SHOOTER1, MotorAlignmentValue.Aligned));
        shooter3.setControl(new Follower(RobotMap.SHOOTER1, MotorAlignmentValue.Opposed));
        shooter4.setControl(new Follower(RobotMap.SHOOTER1, MotorAlignmentValue.Opposed));
    }
}
