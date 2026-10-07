package org.team340.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team340.robot.Constants.RobotMap;

public class ShooterSubsys extends SubsystemBase {

    public TalonSRX ShootMotor = new TalonSRX(RobotMap.SHOOTER1);
    public TalonSRX ShootMotor2 = new TalonSRX(RobotMap.SHOOTER2);
    public TalonSRX ShootMotor3 = new TalonSRX(RobotMap.SHOOTER3);
    public TalonSRX ShootMotor4 = new TalonSRX(RobotMap.SHOOTER4);

    public enum ShooterState {
        STOPPED,
        //HUB,
        AUTO,
        SNOWBLOW
    }

    private static ShooterState state = ShooterState.STOPPED;

    public ShooterSubsys() {
        stopMotors();
        configureMotors();
        marryMotors();
    }

    @Override
    public void periodic() {
        switch (state) {
            // case HUB:
            //     ShootMotor.set(ShooterConfig.HUB);
            //     break;
            case SNOWBLOW:
                ShootMotor.set(TalonSRXControlMode.PercentOutput, ShooterConfig.SNOWBLOW);
                break;
            case AUTO:
                ShootMotor.set(
                    TalonSRXControlMode.PercentOutput,
                    0.75 //ShootParams.shooterVelocityMap.get(Robot.getDistance())
                );
                break;
            default:
                ShootMotor.set(TalonSRXControlMode.PercentOutput, 0);
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
        ShootMotor.set(TalonSRXControlMode.PercentOutput, 0);
        state = ShooterState.STOPPED;
    }

    public void marryMotors() {
        ShootMotor2.follow(ShootMotor);
        ShootMotor3.follow(ShootMotor);
        ShootMotor4.follow(ShootMotor);
    }

    public void configureMotors() {
        ShootMotor3.setInverted(true);
        ShootMotor4.setInverted(true);
        ShootMotor.setNeutralMode(NeutralMode.Coast);
        ShootMotor2.setNeutralMode(NeutralMode.Coast);
        ShootMotor3.setNeutralMode(NeutralMode.Coast);
        ShootMotor4.setNeutralMode(NeutralMode.Coast);
    }
}
