package frc.robot.commands;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Pair;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Pitch;

public class AutonomousRL extends CommandBase {

  Drivetrain sb_drive;
  Elevator   sb_elev;
  Claw       sb_claw;
  Pitch      sb_pitch;

  boolean finished;
  Timer t;
  final int claw = 1, elevator = 2, drive = 3, clawTimer = 4, pitch = 5;

  ArrayList<Pair<Double, Integer>> actions = new ArrayList<>(Arrays.asList(
    
    new Pair<>(-50.0, drive)
    ,new Pair<>(1000.0, elevator)
    ,new Pair<>(0.2, clawTimer)
    ,new Pair<>(1.0, 0)

    //new Pair<>(-0.5, clawTimer)
    //,new Pair<>(2.0, 0)
    //,new Pair<>(0.0, claw)
    //,new Pair<>(-50.0, drive)
    //,new Pair<>(3800.0, elevator)
    //,new Pair<>(-0.5, clawTimer)
    //,new Pair<>(2.0, 0)
    //,new Pair<>(0.0, clawTimer)
    //,new Pair<>(0.0, elevator)

  ));

  public AutonomousRL(Drivetrain dr, Elevator el, Claw cl, Pitch pt) {

    addRequirements(dr);
    addRequirements(el);
    addRequirements(cl);
    addRequirements(pt);

    sb_drive = dr;
    sb_elev  = el;
    sb_claw  = cl;
    sb_pitch = pt;

    t = new Timer();

  }

  @Override
  public void initialize() {

    sb_elev.resetEnc_elev();
    sb_drive.resetEncoders();

  }

  @Override
  public void execute() {

    if (actions.size() != 0) {

      switch(actions.get(0).secundValue()){
        case 1:
          sb_claw.collect(actions.get(0).firstValue());
          actions.remove(0);
          break;

        case 2:
          sb_elev.controlAuto(actions.get(0).firstValue(), .2);
          if (!sb_elev.isMove()) {

            sb_claw.collect(actions.get(1).firstValue());
            sb_claw.setTime(actions.get(2).firstValue());
            if (!sb_claw.isActive()) {
              actions.remove(2);
              actions.remove(1);

            }

          }
          break;
        
        case 3:
          sb_drive.route(.7, actions.get(0).firstValue());
          if (!sb_drive.isMove()) {
            actions.remove(0);
          }
          break;
 
        case 4:
          sb_claw.collect(actions.get(0).firstValue());
          sb_claw.setTime(actions.get(1).firstValue());
          if (!sb_claw.isActive()) {
            actions.remove(1);
            actions.remove(0);
          }
          break;

          case 5:
          sb_pitch.move_pitch(actions.get(0).firstValue(), 0.3);
          
        }

      } else finished = true;
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return finished;
  }
}
