package org.team340.robot.subsystems.Pivot;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class PivotConfig {

    public static double STOW = 0;
    public static double EXTEND = 0;

            /* configuration constants */
            private static final double mmCruiseVelocity = 0.1;  // 12 rpm cruise
            private static final double mmAcceleration   = 0.2;  // ~0.5 seconds to max vel.
            private static final double mmJerk           = 0;  // ~0.2 seconds to max accel.

            protected static final int     climberContinuousCurrentLimit = 30; //TODO: find real number
            protected static final int     climberPeakCurrentLimit       = 30; //TODO: find real number
            protected static final int     climberPeakCurrentDuration    = 100;//TODO: find real number
            protected static final boolean climberEnableCurrentLimit     = true;//TODO: find real number1

            private static final boolean enableCurrentLimitting = false;
            private static final double  suppCurrent = 40;      // Max Amps allowed in Supply
            private static final NeutralModeValue neutralMode = NeutralModeValue.Brake;

            private static final double nonload_kP = 20;   // (P)roportional value
            private static final double nonload_kI = 1;   // (I)ntegral Value
            private static final double nonload_kD = 0.2;   // (D)erivative Value
            private static final double nonload_kV = 0.12;  // Volts/100 (?)
            private static final double nonload_kS = 0;  // (S)tiction Value:
            private static final double nonload_kG = 0;


    protected static TalonFXConfiguration getConfig(CANcoder caNcoder) {
        var talonFX_Config = new TalonFXConfiguration();
        talonFX_Config.Feedback.FeedbackRemoteSensorID = caNcoder.getDeviceID();
        talonFX_Config.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;

                // Configure Motion Magic Values
        var mm = talonFX_Config.MotionMagic;
        mm.MotionMagicCruiseVelocity = mmCruiseVelocity;
        mm.MotionMagicAcceleration = mmAcceleration;
        mm.MotionMagicJerk = mmJerk;
        

        CurrentLimitsConfigs currentLimits = talonFX_Config.CurrentLimits;
        currentLimits.SupplyCurrentLimitEnable = enableCurrentLimitting;
        currentLimits.SupplyCurrentLimit = suppCurrent;
        
        // Configure Soft Limits
        talonFX_Config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        talonFX_Config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 2;

        // Configure neutral mode
        talonFX_Config.MotorOutput.NeutralMode = neutralMode;

        var slot0Configs = talonFX_Config.Slot0;

        slot0Configs.kP = nonload_kP;
        slot0Configs.kI = nonload_kI;
        slot0Configs.kD = nonload_kD;
        slot0Configs.kV = nonload_kV;
        slot0Configs.kS = nonload_kS;
        slot0Configs.kG = nonload_kG;

        //motor.getConfigurator().apply(talonFX_Config);
        return talonFX_Config;
    }
}
