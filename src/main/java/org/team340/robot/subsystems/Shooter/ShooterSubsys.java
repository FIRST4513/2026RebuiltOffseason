package org.team340.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team340.robot.Constants.RobotMap;

public class ShooterSubsys extends SubsystemBase {


    public TalonFX shooter1 = new TalonFX(RobotMap.SHOOTER1);
    public TalonFX shooter2 = new TalonFX(RobotMap.SHOOTER2);
    public TalonFX shooter3 = new TalonFX(RobotMap.SHOOTER3);
    public TalonFX shooter4 = new TalonFX(RobotMap.SHOOTER4);

    public enum ShooterState {
        STOPPED,
        MAN,
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
                
                break;
            case AUTO:
                
                break;
            case MAN:
                
                break;
            default:
                //ShootMotor.set(TalonSRXControlMode.PercentOutput, 0);
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



    //2.45 = 75%
}
