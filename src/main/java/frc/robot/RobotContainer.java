package frc.robot;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import java.util.List;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class RobotContainer {

  XboxController pilot, coPilot;

  Drivetrain sb_drive;
  Elevator sb_elev;
  Arm sb_arm;
  Claw sb_claw;

  Camera sb_camera;

  public RobotContainer() {

    pilot   = new XboxController(Constants.Joystick.id_pilot);
    coPilot = new XboxController(Constants.Joystick.id_copilot);

    sb_drive = new Drivetrain();
    sb_elev  = new Elevator();
    sb_arm   = new Arm();
    sb_claw  = new Claw();

    sb_camera = new Camera();

    configureBindings();
  }

  private void configureBindings() {

    sb_drive.setDefaultCommand( new RunCommand(() -> {

      sb_drive.traction(pilot.getRightX(), pilot.getLeftY());

    }, sb_drive));

    sb_elev.setDefaultCommand( new RunCommand(() -> {

      sb_elev.move_elev(coPilot.getLeftY());
        //pilot.setRumble(null, 0);
    }, sb_elev));

    sb_arm.setDefaultCommand( new RunCommand(() -> {

      // if((pilot.getRightX() > 0.1 || pilot.getRightX() > -0.1) && sb_arm.getSr_front())
      if(pilot.getAButton()) {

        sb_arm.moveArm(.5);
        sb_arm.position1();

      } else if (pilot.getBButton()) {

        sb_arm.moveArm(-.5);
        sb_arm.position2();

      }

      else sb_arm.moveArm(0);
      if (pilot.getAButton()) 
      sb_arm.moveRoll(1);
      else sb_arm.moveRoll(0);

      

    }, sb_arm));

    sb_claw.setDefaultCommand( new RunCommand(() -> {

    }, sb_claw));

  }

  public Command getAutonomousCommand() {

    return null;
    /* 
    var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
      new SimpleMotorFeedforward(
        Constants.Drivetrain.ksVolts,
        Constants.Drivetrain.kvVoltSecondsPerMeter,
        Constants.Drivetrain.kaVoltSecondsSquaredPerMeter),
        Constants.Drivetrain.kDriveKinematics, 6);

    TrajectoryConfig config = new TrajectoryConfig(
      Constants.Drivetrain.kMaxSpeedMetersPerSecond,
      Constants.Drivetrain.kMaxAccelerationMetersPerSecondSquared)
      .setKinematics(Constants.Drivetrain.kDriveKinematics)
      .addConstraint(autoVoltageConstraint);

    Trajectory exampleTrajectory =
    TrajectoryGenerator.generateTrajectory(
        new Pose2d(0, 0, new Rotation2d(0)),
        List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
        new Pose2d(3, 0, new Rotation2d(0)),
        config);
          
        RamseteCommand ramseteCommand =
        new RamseteCommand(
            exampleTrajectory,
            sb_drive::getPose,
            new RamseteController(Constants.Drivetrain.kRamseteB, Constants.Drivetrain.kRamseteZeta),
            new SimpleMotorFeedforward(
                Constants.Drivetrain.ksVolts,
                Constants.Drivetrain.kvVoltSecondsPerMeter,
                Constants.Drivetrain.kaVoltSecondsSquaredPerMeter),
            Constants.Drivetrain.kDriveKinematics,
            sb_drive::getWheelSpeeds,
            new PIDController(Constants.Drivetrain.kPDriveVel, 0, 0),
            new PIDController(Constants.Drivetrain.kPDriveVel, 0, 0),
            sb_drive::tankDriveVolts,
            sb_drive);

    sb_drive.resetOdometry(exampleTrajectory.getInitialPose());
    return ramseteCommand.andThen(() -> sb_drive.tankDriveVolts(0, 0));
  //*/ 
  }

}
