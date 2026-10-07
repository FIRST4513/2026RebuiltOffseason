package org.team340.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import org.team340.robot.util.Vision.CameraConfig;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 */
public final class Constants {

    public static final CameraConfig[] CAMERAS = {
        new CameraConfig(
            "LeftCam",
            new Translation3d(0.32512, -0.22479, 0.2413254),
            new Rotation3d(0, 0.174533, 0.174533)
        ),
        new CameraConfig(
            "RightCam",
            new Translation3d(0.32512, 0.22479, 0.2413254),
            new Rotation3d(0, 0.174533, -0.174533)
        )
    };

    public static final double VOLTAGE = 12.0;

    // Controller ports
    public static final int DRIVER = 0;
    public static final int CO_DRIVER = 1;

    /**
     * The RobotMap class defines CAN IDs, CAN bus names, DIO/PWM/PH/PCM channel
     * IDs, and other relevant identifiers for addressing robot hardware.
     */
    public static final class RobotMap {

        public static final String LOWER_CAN = "CANivore";

        public static final int FL_MOVE = 2;
        public static final int FL_TURN = 3;
        public static final int FR_MOVE = 4;
        public static final int FR_TURN = 5;
        public static final int BL_MOVE = 6;
        public static final int BL_TURN = 7;
        public static final int BR_MOVE = 8;
        public static final int BR_TURN = 9;

        public static final int FL_ENCODER = 10;
        public static final int FR_ENCODER = 11;
        public static final int BL_ENCODER = 12;
        public static final int BR_ENCODER = 13;

        public static final int CANANDGYRO = 14;

        public static final int PIVOTENCODER = 15;

        public static final int SHOOTER1 = 21;
        public static final int SHOOTER2 = 22;
        public static final int SHOOTER3 = 23;
        public static final int SHOOTER4 = 24;

        public static final int SHOOTERACCELERATOR = 25;
        public static final int SHOOTERFEEDER = 26;

        public static final int CONVEYOR = 27;

        public static final int PIVOT = 28;

        public static final int INTAKE = 29;
    }
}
