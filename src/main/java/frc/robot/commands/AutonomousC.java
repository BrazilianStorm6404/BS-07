package frc.robot.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Pair;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Pitch;

public class AutonomousC extends CommandBase {

  Drivetrain sb_drive;
  Claw       sb_claw;
  Pitch      sb_pitch;

  boolean finished;
  Timer t;
  final int claw = 1, drive = 2, clawTimer = 3, pitch = 4;

  ArrayList<Pair<Double, Integer>> actions = new ArrayList<>(Arrays.asList(
    
   new Pair<>(600.0, pitch)
  ,new Pair<>(-.9, clawTimer)
  ,new Pair<>(2.0, 0)
  ,new Pair<>(0.0, claw)
  ,new Pair<>(190.0, drive)

  ));

  public AutonomousC(Drivetrain dr, Claw cl, Pitch pt) {

    addRequirements(dr);
    addRequirements(cl);
    addRequirements(pt);

    sb_drive = dr;
    sb_claw  = cl;
    sb_pitch = pt;

    t = new Timer();

  }

  @Override
  public void initialize() {

    sb_drive.resetEncoders();
    sb_pitch.resetEnc();

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
          sb_drive.route(.8, actions.get(0).firstValue());
          if (sb_drive.isMove()) {
            actions.remove(0);
          }
          break;
 
        case 3:
          sb_claw.collect(actions.get(0).firstValue());
          sb_claw.setTime(actions.get(1).firstValue());
          if (!sb_claw.isActive()) {
            actions.remove(1);
            actions.remove(0);
          }
          break;

        case 4:
          sb_pitch.move_pitch(.8, actions.get(0).firstValue());
          sb_claw.collect(0.3);
          if (sb_pitch.isMove()) {
            actions.remove(0);
          }
          break;

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
