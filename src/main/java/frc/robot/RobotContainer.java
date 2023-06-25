package frc.robot;

import frc.robot.commands.AutonomousC;
import frc.robot.commands.AutonomousRL;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.NeoTeste;
import frc.robot.subsystems.Pitch;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class RobotContainer {

  XboxController pilot, coPilot;

  boolean setCol1 = true,
          estado1 = false, 
          stagesActv = false;

  Drivetrain sb_drive;
  Elevator sb_elev;
  Claw sb_claw;
  Pitch sb_pitch;
  NeoTeste neo;
  AutonomousC cmd_autoC;
  AutonomousRL cmd_autoRL;

  Camera sb_camera;
  Limelight sb_ll;

  public RobotContainer() {

    pilot   = new XboxController(Constants.Joystick.id_pilot);
    coPilot = new XboxController(Constants.Joystick.id_copilot);

    //sb_drive = new Drivetrain();
    //sb_elev  = new Elevator();
    //sb_claw  = new Claw();
    //sb_pitch = new Pitch(sb_elev);
    //cmd_autoC = new AutonomousC(sb_drive, sb_elev, sb_claw);
    //cmd_autoRL = new AutonomousRL(sb_drive, sb_elev, sb_claw, sb_pitch);

    neo = new NeoTeste();

    sb_camera = new Camera();
    sb_ll = new Limelight();

    configureBindings();
  }

  private void configureBindings() {

    neo.setDefaultCommand( new RunCommand(() -> {

      //neo.control(coPilot.getLeftY());

    }, neo));

/* 
    //DRIVE
    sb_drive.setDefaultCommand( new RunCommand(() -> {

      sb_drive.traction(pilot.getLeftY() * (pilot.getRightBumper() ? .5 : 1), pilot.getLeftX() * (pilot.getRightBumper() ? .5 : 1));

    }, sb_drive));
*/
 /* 
    //ELEVADOR
    sb_elev.setDefaultCommand( new RunCommand(() -> {

      /*if      (pilot.getLeftTriggerAxis() > 0) sb_elev.move_el(pilot.getLeftTriggerAxis() * .3);
      else if (pilot.getRightTriggerAxis() > 0) sb_elev.move_el(-pilot.getRightTriggerAxis() * .3);
      else if (pilot.getAButton()) while(!sb_elev.getLmt()) sb_elev.move_el(Math.min(sb_elev.getEnc(),1450)/1500.0);
      else if (pilot.getYButton()) while(sb_elev.getEnc() < 1000) sb_elev.move_el(-.3);
      else     sb_elev.move_el(0);
      
      if (coPilot.getXButtonReleased()){

        stagesActv = !stagesActv;
        sb_elev.setCurrentPoint();

      }
 
      if (stagesActv) {
        sb_elev.adjust(Math.round(-coPilot.getLeftY()*10)/10.0);

        if (coPilot.getYButtonReleased()) sb_elev.upStage();
        else if (coPilot.getAButtonReleased()) sb_elev.downStage();

      } else {

        sb_elev.move((Math.round(coPilot.getLeftY() * 10)/10.0) * .7);

      }
      
      //sb_elev.adjust(Math.round(-coPilot.getLeftY()*10)/10.0);

      if (pilot.getYButtonReleased()) sb_elev.upStage();
      else if (pilot.getAButtonReleased()) sb_elev.downStage();

      //sb_elev.move((pilot.getRightTriggerAxis() - pilot.getLeftTriggerAxis()) * 0.7);
      //sb_elev.move((Math.round(coPilot.getLeftY() * 10)/10.0) * .7);

    }, sb_elev));
    
    */
    //GARRA
    /*sb_claw.setDefaultCommand( new RunCommand(() -> {

      if (pilot.getLeftBumper()) sb_claw.collect(.4);
      else if (pilot.getRightBumper()) sb_claw.collect(-.4);
      else    sb_claw.collect(.0);
      
    }, sb_claw));

    //PITCH_GARRA
    sb_pitch.setDefaultCommand( new RunCommand(() -> {

      sb_pitch.move_pitch(pilot.getRightY(), 0.5);
      
    }, sb_pitch));

*/
  }
  public Command getAutonomousCommand() {

    return null;  /*cmd_autoC;*/

  }

}
/*
- teste de giroscopio para equilibrio na balança - 3.1

- estagios do elevador - 2.1

- controle de pitch da garra - 1.1
	- encoder + limit !
	-

- mudanças nos controles dos pilotos - 1.2

- limelight (QR, detecção objetos de jogo) - 2.2
	- coletar

- shuffleboard (auto) - 2.3
	-  modulo (encapsulado)
 
- Sistemas de controle - 3.2
 */