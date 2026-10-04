package org.team340.robot.subsystems.ShooterFeeder;

import org.team340.robot.Constants.RobotMap;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterFeederSubSys extends SubsystemBase{


    public SparkFlex ShooterAccel = new SparkFlex(RobotMap.SHOOTERACCELERATOR, MotorType.kBrushless);
    public SparkMax ShooterFeeder = new SparkMax(RobotMap.SHOOTERFEEDER, MotorType.kBrushless);

    public enum ShooterFeederState {
        ON,
        STOPPED
    }

    private static ShooterFeederState state = ShooterFeederState.STOPPED;

    public ShooterFeederSubSys() {
        stopMotors();
        configureMotors();
    }

    @Override
    public void periodic() {
        switch (state) {
            // case HUB:
            case ON:
                ShooterAccel.set(1);
                ShooterFeeder.set(0.5);
            default:
                ShooterAccel.stopMotor();
                ShooterFeeder.stopMotor();
                break;
        }
    }

    // get set stuff

    public void setNewState(ShooterFeederState newState) {
        state = newState;
    }

    public static ShooterFeederState getState() {
        return state;
    }

    public void stopMotors() {
        ShooterAccel.stopMotor();
        ShooterFeeder.stopMotor();
        state = ShooterFeederState.STOPPED;
    }


    public void configureMotors() {
    }
}
