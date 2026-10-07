package org.team340.robot;

import static edu.wpi.first.wpilibj2.command.Commands.*;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.NotLogged;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.team340.lib.logging.LoggedRobot;
import org.team340.lib.logging.Profiler;
import org.team340.lib.util.DisableWatchdog;
import org.team340.robot.commands.Autos;
import org.team340.robot.commands.Routines;
import org.team340.robot.subsystems.Conveyor.ConveyorCmds;
import org.team340.robot.subsystems.Conveyor.ConveyorSubSys;
import org.team340.robot.subsystems.Conveyor.ConveyorSubSys.ConveyorState;
//import org.team340.robot.subsystems.Pivot.PivotSubsys;
import org.team340.robot.subsystems.Shooter.ShooterCmds;
import org.team340.robot.subsystems.Shooter.ShooterSubsys;
import org.team340.robot.subsystems.Shooter.ShooterSubsys.ShooterState;
import org.team340.robot.subsystems.ShooterFeeder.ShooterFeederCmds;
import org.team340.robot.subsystems.ShooterFeeder.ShooterFeederSubSys;
import org.team340.robot.subsystems.ShooterFeeder.ShooterFeederSubSys.ShooterFeederState;
import org.team340.robot.subsystems.Swerve;

@Logged
public final class Robot extends LoggedRobot {

    /* TODO:🐖🐖🐖🐖🐖🐖🐖🐖🐖🐖🐖🐖🐖
     * Set shooter auto speed bands
     * Set pivot rotations
     * Vision
     * More autos (if corbin thinks intake will be done?)
     *
     * 🗑️🪠 DE---CIM---ATE
     */

    private final CommandScheduler scheduler = CommandScheduler.getInstance();

    public final Swerve swerve;

    public final Routines routines;
    public final Autos autos;
    public static ShooterSubsys shooter;
    //public static IntakeSubsys intake;
    public static ShooterFeederSubSys shooterFeeder;
    public static ConveyorSubSys conveyor;
    //public static PivotSubsys pivot;

    private final CommandXboxController driver;
    private final CommandXboxController coDriver;

    public static double Distance;

    public Robot() {
        // Initialize subsystems
        swerve = new Swerve();

        // Initialize compositions
        routines = new Routines(this);
        autos = new Autos(this);
        shooter = new ShooterSubsys();
        //intake = new IntakeSubsys();
        shooterFeeder = new ShooterFeederSubSys();
        conveyor = new ConveyorSubSys();
        //pivot = new PivotSubsys();

        // Initialize controllers
        driver = new CommandXboxController(Constants.DRIVER);
        coDriver = new CommandXboxController(Constants.CO_DRIVER);

        // Set default commands
        swerve.setDefaultCommand(swerve.drive(this::driverX, this::driverY, this::driverAngular));

        // Driver bindings
        driver.a().onTrue(none());
        driver.button(7).onTrue(swerve.tareRotation());

        driver.button(6).onTrue(new InstantCommand(() -> scheduler.cancel(swerve.drive(null, null, null))));
        driver
            .button(6)
            .onTrue(
                new InstantCommand(() ->
                    scheduler.schedule(swerve.driveAtHub(this::driverX, this::driverY, this::driverAngular))
                )
            );

        driver.button(5).onTrue(new InstantCommand(() -> scheduler.cancel(swerve.driveAtHub(null, null, null))));
        driver
            .button(5)
            .onTrue(
                new InstantCommand(() ->
                    scheduler.schedule(swerve.drive(this::driverX, this::driverY, this::driverAngular))
                )
            );

        // Co-driver bindings

        coDriver.y().onTrue(ShooterCmds.shooterSetState(ShooterState.AUTO));

        coDriver
            .y()
            .onTrue(
                new SequentialCommandGroup(
                    new WaitCommand(1),
                    new ParallelCommandGroup(
                        ShooterFeederCmds.shooterFeederSetState(ShooterFeederState.ON),
                        ConveyorCmds.ConveyorSetState(ConveyorState.FEEDING)
                    )
                )
            );
        //coDriver.y().onTrue();
        //coDriver.y().onTrue();

        coDriver.povDown().onTrue(ConveyorCmds.ConveyorSetState(ConveyorState.BACKWARDS));

        coDriver.x().onTrue(ShooterCmds.shooterStopCmd());
        coDriver.x().onTrue(ShooterFeederCmds.shooterFeederStopCmd());
        coDriver.x().onTrue(ConveyorCmds.ConveyorStopCmd());

        // coDriver.b().onTrue(IntakeCmds.intakeSetState(IntakeState.INTAKE));
        // coDriver.a().onTrue(IntakeCmds.intakeSetState(IntakeState.STOPPED));

        // Disable loop overrun warnings from the command
        // scheduler, since we already log loop timings
        DisableWatchdog.in(scheduler, "m_watchdog");

        // Configure the brownout threshold to match RIO 1
        RobotController.setBrownoutVoltage(6.3);
    }

    @NotLogged
    public double driverX() {
        return driver.getLeftX();
    }

    @NotLogged
    public double driverY() {
        return driver.getLeftY();
    }

    @NotLogged
    public double driverAngular() {
        return driver.getLeftTriggerAxis() - driver.getRightTriggerAxis();
    }

    @Override
    public void robotPeriodic() {
        Profiler.run("scheduler", scheduler::run);
    }

    public double distanceToHub() {
        return swerve.distanceToHub();
    }

    public static double getDistance() {
        return Distance;
    }
}
