package org.team340.robot.util;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import org.team340.robot.subsystems.Shooter.ShooterConfig;

/**
 * This class stores our lookup tables for the {@link Hood} and {@link Shooters}
 * subsystems to utilize when converting hub distance to motor setpoints.
 */
public final class ShootParams {

    /**
     * The ball's time of flight, in seconds. All entries in
     * our lookup tables below are tuned for this constant.
     */
    public static final double TOF = 1.4;

    /**
     * Our shooter velocity lookup table.
     * Maps hub distance (meters) to shooter velocity (rotations/second).
     */
    public static final InterpolatingDoubleTreeMap shooterVelocityMap;

    static {
        // Data obtained from empirical testing.
        final DataPoint[] dataPoints = {
            new DataPoint(1.5, 0.0),
            new DataPoint(2.00, -.535 + ShooterConfig.AUTOMAN),
            new DataPoint(2.2, -.5675 + ShooterConfig.AUTOMAN),
            new DataPoint(2.4, -.595 + ShooterConfig.AUTOMAN),
            new DataPoint(2.7, -.61 + ShooterConfig.AUTOMAN),
            new DataPoint(2.9, -.6525 + ShooterConfig.AUTOMAN),
            new DataPoint(3.1, -.6625 + ShooterConfig.AUTOMAN),
            new DataPoint(3.3, -.695 + ShooterConfig.AUTOMAN),
            new DataPoint(3.5, -.7325 + ShooterConfig.AUTOMAN),
            new DataPoint(3.7, -.77 + ShooterConfig.AUTOMAN),
            new DataPoint(3.9, -.7875 + ShooterConfig.AUTOMAN),
            new DataPoint(4, -.795 + ShooterConfig.AUTOMAN),
            new DataPoint(4.2, -.8175 + ShooterConfig.AUTOMAN),
            new DataPoint(4.4, -.8325 + ShooterConfig.AUTOMAN),
            new DataPoint(4.7, -.85 + ShooterConfig.AUTOMAN),
            new DataPoint(5.3, -.9275 + ShooterConfig.AUTOMAN)
        };

        // Create our lookup tables.
        shooterVelocityMap = new InterpolatingDoubleTreeMap();

        // Populate the tables with our configured data points.
        for (final DataPoint dataPoint : dataPoints) {
            shooterVelocityMap.put(dataPoint.distance, dataPoint.shooterVelocity);
        }
    }

    /**
     * A data point for our lookup tables. Contains the hood position
     * and shooter velocity needed to make a shot from a given distance.
     * @param distance The robot's distance to the hub, in meters.
     * @param shooterVelocity The velocity of the shooter wheels, in rotations/second.
     */
    private record DataPoint(double distance, double shooterVelocity) {}

    private ShootParams() {
        throw new UnsupportedOperationException("This is a utility class!");
    }
}
