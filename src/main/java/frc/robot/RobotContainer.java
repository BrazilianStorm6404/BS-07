package frc.robot;

import frc.robot.commands.AutonomousC;
import frc.robot.commands.AutonomousCone;
import frc.robot.commands.AutonomousRL;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pitch;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class RobotContainer {

  XboxController pilot, coPilot;

  Drivetrain sb_drive;
  Elevator sb_elev;
  Claw sb_claw;
  Pitch sb_pitch;
  AutonomousC cmd_autoC;
  AutonomousRL cmd_autoRL;
  AutonomousCone cmd_autoCone;

  Camera sb_camera;
  Limelight sb_ll;

  public RobotContainer() {

    pilot   = new XboxController(Constants.Joystick.id_pilot);
    coPilot = new XboxController(Constants.Joystick.id_copilot);

    sb_drive = new Drivetrain();
    sb_elev  = new Elevator();
    sb_claw  = new Claw();
    sb_pitch = new Pitch();
    cmd_autoC = new AutonomousC(sb_drive, sb_claw, sb_pitch);
    cmd_autoRL = new AutonomousRL(sb_drive, sb_claw, sb_pitch);
    cmd_autoCone = new AutonomousCone(sb_drive, sb_claw, sb_pitch, sb_elev);

    sb_camera = new Camera();
    sb_ll = new Limelight();

    configureBindings();
  }

  private void configureBindings() {

    //DRIVE
    sb_drive.setDefaultCommand( new RunCommand(() -> {

      sb_drive.traction(pilot.getRightX() * (pilot.getRightBumper() ? .5 : 1) , pilot.getLeftY() * (pilot.getRightBumper() ? .5 : 1));

    }, sb_drive));

 
    //ELEVADOR
    sb_elev.setDefaultCommand( new RunCommand(() -> {

      sb_elev.updateStages();
      if (coPilot.getYButtonReleased()) sb_elev.upStage(); 
      if (coPilot.getAButtonReleased()) sb_elev.downStage();

    }, sb_elev));
    
    
    //GARRA
    sb_claw.setDefaultCommand( new RunCommand(() -> {

      if      (coPilot.getLeftTriggerAxis() > 0) sb_claw.collect(coPilot.getLeftTriggerAxis());
      else if (coPilot.getLeftBumper()) sb_claw.collect(0.3);
      else if (coPilot.getRightTriggerAxis() > 0) sb_claw.collect(-coPilot.getRightTriggerAxis());
      else    sb_claw.collect(.0);
      
    }, sb_claw));

    //PITCH_GARRA
    sb_pitch.setDefaultCommand( new RunCommand(() -> {

      sb_pitch.move(coPilot.getLeftY());
  
    }, sb_pitch));
 
  }

  //AUTONOMO
  public Command getAutonomousCommand() {
    
    if(sb_ll.LimeC() == true){
      return cmd_autoC;
    } else if (sb_ll.LimeRL() == true){
      return cmd_autoRL;
    } else{
      return cmd_autoCone;
    }
  }

}