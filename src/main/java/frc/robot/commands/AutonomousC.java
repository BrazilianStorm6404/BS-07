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

public class AutonomousC extends CommandBase {

  Drivetrain sb_drive;
  Elevator   sb_elev;
  Claw       sb_claw;

  boolean finished;
  Timer t;
  final int claw = 1, elevator = 2, drive = 3, clawTimer = 4;

  ArrayList<Pair<Double, Integer>> actions = new ArrayList<>(Arrays.asList(
    
    new Pair<>(-0.5, clawTimer)
    ,new Pair<>(2.0, 0)
    ,new Pair<>(0.0, claw)
    ,new Pair<>(-142.0, drive)


  ));

  public AutonomousC(Drivetrain dr, Elevator el, Claw cl) {

    addRequirements(dr);
    addRequirements(el);
    addRequirements(cl);

    sb_drive    = dr;
    sb_elev     = el;
    sb_claw     = cl;

    t = new Timer();

  }

  @Override
  public void initialize() {

    //sb_elev.resetEnc_elev();
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

        /*case 2:
          sb_elev.move_elev(actions.get(0).firstValue(), .35);
          if (!sb_elev.isMove()) {
            actions.remove(0);
          }
          break;*/
        
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
        }

      } 
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return finished;
  }
}
