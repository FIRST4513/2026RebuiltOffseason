package org.team340.robot.commands;

import static edu.wpi.first.wpilibj2.command.Commands.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
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
import org.team340.robot.subsystems.Intake.IntakeCmds;
import org.team340.robot.subsystems.Intake.IntakeSubsys.IntakeState;
import org.team340.robot.subsystems.Pivot.PivotCmds;
import org.team340.robot.subsystems.Pivot.PivotSubsys.PivotState;
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
        //chooser.add("Example", example());
        chooser.add("neutral sweep right", neutralSweepRight());
        chooser.add("Back up Shoot", backUpShoot());
        chooser.add("neutral sweep left", neutralSweepLeft());
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

    private Command neutralSweepRight() {
        var start = new ExtPose(4, 0.68, Rotation2d.k180deg);
        var move1 = new ExtPose(5.5, 0.68, Rotation2d.k180deg);
        var move2 = new ExtPose(6.48, 0.68, Rotation2d.kCW_90deg);
        var intake1 = new ExtPose(7.75, 1.11, Rotation2d.kCW_90deg);
        var intake2 = new ExtPose(7.75, 3.25, Rotation2d.kCW_90deg);
        var intakeSecond = new ExtPose(7.6, 2.5, Rotation2d.kCW_90deg);

        var return1 = new ExtPose(6.84, 0.68, Rotation2d.k180deg);
        var return2 = new ExtPose(3.76, 0.68, Rotation2d.k180deg);
        //var return3 = new ExtPose(3, 1.11, Rotation2d.k180deg);
        var return4 = new ExtPose(2.6, 0.85, Rotation2d.k180deg);
        var shoot = new ExtPose(2.2, 2.3, Rotation2d.fromDegrees(42.5));

        if (DriverStation.getAlliance().equals(Alliance.Red)) {
            start = new ExtPose(16.54 - 4, 8.00 - 0.68, Rotation2d.kZero);
            move1 = new ExtPose(16.54 - 5.5, 8.00 - 0.68, Rotation2d.k180deg);
            move2 = new ExtPose(16.54 - 6.48, 8.00 - 0.68, Rotation2d.kCCW_90deg);
            intake1 = new ExtPose(16.54 - 7.75, 8.00 - 1.11, Rotation2d.kCCW_90deg);
            intake2 = new ExtPose(16.54 - 7.75, 8.00 - 3.25, Rotation2d.kCCW_90deg);
            intakeSecond = new ExtPose(16.54 - 7.6, 8.00 - 2.5, Rotation2d.kCCW_90deg);

            return1 = new ExtPose(16.54 - 6.84, 8.00 - 0.68, Rotation2d.kZero);
            return2 = new ExtPose(16.54 - 3.76, 8.00 - 0.68, Rotation2d.kZero);
            //return3 = new ExtPose(16.54 - 3, 8.00 - 1.11, Rotation2d.k180deg);
            return4 = new ExtPose(16.54 - 2.6, 8.00 - 0.85, Rotation2d.kZero);
            shoot = new ExtPose(16.54 - 2.2, 8.00 - 2.3, Rotation2d.fromDegrees(222.5));
        }

        return sequence(
            swerve.resetPose(start),
            PivotCmds.pivotSetState(PivotState.EXTEND),
            new WaitCommand(0.5),
            swerve.apfDrive(move1, () -> 16, tolerance),
            swerve.apfDrive(move2, () -> 16, tolerance),
            swerve.apfDrive(intake1, deceleration, tolerance),
            IntakeCmds.intakeSetState(IntakeState.INTAKE),
            swerve.apfSlow(intake2, deceleration, tolerance),
            IntakeCmds.intakeStopCmd(),
            swerve.apfDrive(return1, deceleration, tolerance),
            ShooterCmds.shooterSetState(ShooterState.MAN),
            swerve.apfDrive(return2, deceleration, tolerance),
            //swerve.apfDrive(return3, deceleration, tolerance),
            swerve.apfDrive(return4, deceleration, tolerance),
            swerve.apfSlow(shoot, deceleration, tolerance),
            ConveyorCmds.ConveyorSetState(ConveyorState.FEEDING),
            ShooterFeederCmds.shooterFeederSetState(ShooterFeederState.ON),
            new WaitCommand(4),
            PivotCmds.pivotSetState(PivotState.MANIN),
            IntakeCmds.intakeSetState(IntakeState.INTAKE),
            new WaitCommand(1.25),
            PivotCmds.pivotSetState(PivotState.EXTEND),
            new WaitCommand(1.75),
            ShooterCmds.shooterStopCmd(),
            ConveyorCmds.ConveyorStopCmd(),
            ShooterFeederCmds.shooterFeederStopCmd(),
            IntakeCmds.intakeSetStoppedCmd()
        );
    }

    private Command neutralSweepLeft() {
        var start = new ExtPose(4, 0.32, Rotation2d.k180deg);
        var move1 = new ExtPose(5.5, 0.32, Rotation2d.k180deg);
        var move2 = new ExtPose(6.48, 0.32, Rotation2d.kCCW_90deg);
        var intake1 = new ExtPose(7.75, 6.89, Rotation2d.kCCW_90deg);
        var intake2 = new ExtPose(7.75, 4.75, Rotation2d.kCCW_90deg);
        var intakeSecond = new ExtPose(7.6, 5.5, Rotation2d.kCCW_90deg);

        var return1 = new ExtPose(6.84, 0.32, Rotation2d.kZero);
        var return2 = new ExtPose(3.76, 0.32, Rotation2d.kZero);
        var return4 = new ExtPose(2.6, 7.15, Rotation2d.kZero);
        var shoot = new ExtPose(2.2, 5.7, Rotation2d.fromDegrees(-42.5));

        if (DriverStation.getAlliance().equals(Alliance.Red)) {
            start = new ExtPose(16.54 - 4, 8.00 - 0.32, Rotation2d.kZero);
            move1 = new ExtPose(16.54 - 5.5, 8.00 - 0.32, Rotation2d.kZero);
            move2 = new ExtPose(16.54 - 6.48, 8.00 - 0.32, Rotation2d.kCW_90deg);
            intake1 = new ExtPose(16.54 - 7.75, 8.00 - 6.89, Rotation2d.kCW_90deg);
            intake2 = new ExtPose(16.54 - 7.75, 8.00 - 4.75, Rotation2d.kCW_90deg);
            intakeSecond = new ExtPose(16.54 - 8.00 - 7.6, 5.5, Rotation2d.kCW_90deg);

            return1 = new ExtPose(16.54 - 6.84, 0.32, Rotation2d.k180deg);
            return2 = new ExtPose(16.54 - 3.76, 0.32, Rotation2d.k180deg);
            return4 = new ExtPose(16.54 - 2.6, 7.15, Rotation2d.k180deg);
            shoot = new ExtPose(16.54 - 2.2, 5.7, Rotation2d.fromDegrees(42.5));
        }

        return sequence(
            swerve.resetPose(start),
            PivotCmds.pivotSetState(PivotState.EXTEND),
            new WaitCommand(0.5),
            swerve.apfDrive(move1, () -> 16, tolerance),
            swerve.apfDrive(move2, () -> 16, tolerance),
            swerve.apfDrive(intake1, deceleration, tolerance),
            IntakeCmds.intakeSetState(IntakeState.INTAKE),
            swerve.apfSlow(intake2, deceleration, tolerance),
            IntakeCmds.intakeStopCmd(),
            swerve.apfDrive(return1, deceleration, tolerance),
            ShooterCmds.shooterSetState(ShooterState.MAN),
            swerve.apfDrive(return2, deceleration, tolerance),
            //swerve.apfDrive(return3, deceleration, tolerance),
            swerve.apfDrive(return4, deceleration, tolerance),
            swerve.apfSlow(shoot, deceleration, tolerance),
            ConveyorCmds.ConveyorSetState(ConveyorState.FEEDING),
            ShooterFeederCmds.shooterFeederSetState(ShooterFeederState.ON),
            new WaitCommand(4),
            PivotCmds.pivotSetState(PivotState.MANIN),
            IntakeCmds.intakeSetState(IntakeState.INTAKE),
            new WaitCommand(1.25),
            PivotCmds.pivotSetState(PivotState.EXTEND),
            new WaitCommand(1.75),
            ShooterCmds.shooterStopCmd(),
            ConveyorCmds.ConveyorStopCmd(),
            ShooterFeederCmds.shooterFeederStopCmd(),
            IntakeCmds.intakeSetStoppedCmd()
        );
    }

    private Command backUpShoot() {
        var start = new ExtPose(3.5, 4, Rotation2d.kZero);
        var move1 = new ExtPose(2.0, 4, Rotation2d.kZero);

        if (DriverStation.getAlliance().equals(Alliance.Red)) {
            start = new ExtPose(16.54 - 3.5, 8.00 - 4, Rotation2d.k180deg);
            move1 = new ExtPose(16.54 - 2.0, 8.00 - 4, Rotation2d.k180deg);
        }

        return sequence(
            swerve.resetPose(start),
            ShooterCmds.shooterSetState(ShooterState.MAN),
            swerve.apfDrive(move1, deceleration, tolerance),
            ShooterFeederCmds.shooterFeederSetState(ShooterFeederState.ON),
            ConveyorCmds.ConveyorSetState(ConveyorState.FEEDING),
            new WaitCommand(6),
            ShooterCmds.shooterStopCmd(),
            ShooterFeederCmds.shooterFeederStopCmd(),
            ConveyorCmds.ConveyorStopCmd()
        );
    }
}
