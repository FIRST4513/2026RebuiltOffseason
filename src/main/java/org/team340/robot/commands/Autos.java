package org.team340.robot.commands;

import static edu.wpi.first.wpilibj2.command.Commands.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.team340.lib.math.geometry.ExtPose;
import org.team340.lib.tunable.TunableTable;
import org.team340.lib.tunable.Tunables;
import org.team340.lib.tunable.Tunables.TunableDouble;
import org.team340.lib.util.command.AutoChooser;
import org.team340.robot.Robot;
import org.team340.robot.subsystems.Conveyor.ConveyorCmds;
import org.team340.robot.subsystems.Conveyor.ConveyorSubSys.ConveyorState;
import org.team340.robot.subsystems.Shooter.ShooterCmds;
import org.team340.robot.subsystems.Shooter.ShooterSubsys.ShooterState;
import org.team340.robot.subsystems.ShooterFeeder.ShooterFeederCmds;
import org.team340.robot.subsystems.ShooterFeeder.ShooterFeederSubSys.ShooterFeederState;
import org.team340.robot.subsystems.Swerve;

/**
 * The Autos class declares autonomous modes, and adds them
 * to the dashboard to be selected by the drive team.
 */
@SuppressWarnings("unused")
public final class Autos {

    private static final TunableTable tunables = Tunables.getNested("autos");
    private static final TunableDouble deceleration = tunables.value("deceleration", 5.0);
    private static final TunableDouble tolerance = tunables.value("tolerance", 0.05);

    private final Swerve swerve;
    private final Routines routines;

    private final AutoChooser chooser;

    public Autos(Robot robot) {
        swerve = robot.swerve;
        routines = robot.routines;

        // Create the auto chooser
        chooser = new AutoChooser();

        // Add autonomous modes to the dashboard
        chooser.add("Off line shoot", offLineShoot());
        chooser.add("Example", example());
    }

    private Command example() {
        var start = new ExtPose(1.0, 1.0, Rotation2d.kZero);
        var middle = new ExtPose(2.0, 5.0, Rotation2d.k180deg);
        var end = new ExtPose(6.0, 2.5, Rotation2d.kCW_90deg);

        return sequence(
            swerve.resetPose(start),
            swerve.apfDrive(middle, deceleration, tolerance),
            swerve.apfDrive(end, deceleration, tolerance),
            swerve.stop(false)
        );
    }

    private Command offLineShoot() {
        var start = new ExtPose(3.575, 0.6, Rotation2d.kCCW_90deg);
        var middle = new ExtPose(2.0, 1.75, Rotation2d.fromDegrees(45));
        var end = new ExtPose(2, 0.6, Rotation2d.kZero);

        return new SequentialCommandGroup(
            swerve.resetPose(start),
            //swerve.aimAtHub(() -> middle, deceleration),
            swerve.apfDrive(middle, deceleration, tolerance),
            ShooterCmds.shooterSetState(ShooterState.AUTO),
            new WaitCommand(1),
            ShooterFeederCmds.shooterFeederSetState(ShooterFeederState.ON),
            ConveyorCmds.ConveyorSetState(ConveyorState.FEEDING),
            new WaitCommand(2.5),
            ShooterFeederCmds.shooterFeederStopCmd(),
            ShooterCmds.shooterStopCmd(),
            ConveyorCmds.ConveyorStopCmd()
            //swerve.apfDrive(end, deceleration)
        );
    }
}
