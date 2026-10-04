package org.team340.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team340.robot.Constants.RobotMap;
import org.team340.robot.Robot;
import org.team340.robot.util.ShootParams;

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
                ShootMotor.set(TalonSRXControlMode.PercentOutput, ShootParams.shooterVelocityMap.get(Robot.getDistance()));
                break;
            default:
                ShootMotor.neutralOutput();
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
        ShootMotor.neutralOutput();
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
    }
}
