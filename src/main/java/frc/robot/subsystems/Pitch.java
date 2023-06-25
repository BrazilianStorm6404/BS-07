package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pitch extends SubsystemBase {

  VictorSPX ct_pitch;
  Encoder enc_pitch;
  DigitalInput lmt_pitch;
  double posStages[] = {1000, 2000, 3000, 4000};
  double setPoint, pos;

  Elevator elevator;
  Drivetrain drive;

  public Pitch(Elevator elev) {

    ct_pitch  = new VictorSPX(Constants.Pitch.id_pitch);
    lmt_pitch = new DigitalInput(Constants.Pitch.id_lmtPitch);
    enc_pitch = new Encoder(Constants.Pitch.id1_enc, Constants.Pitch.id2_enc);
    enc_pitch.setDistancePerPulse(1);
    enc_pitch.reset();
    elevator = elev;
    //drive = dr;
    resetEnc();

  }

  public void move_pitch(double adjust, double v){

    /*if (drive.timeMove() > 2 && elevator.getStage() == 0) {
      pos = enc_pitch.getDistance() * -0.0009;

    } else {
      pos = (posStages[elevator.getStage()] + a - enc_pitch.getDistance()) * 0.0009;

    }*/


    pos = (posStages[elevator.getStage()] + adjust * 5000 - enc_pitch.getDistance()) * 0.001;

    pos = Math.signum(pos) * Math.min(Math.abs(pos), v);

  }

  public void resetEnc(){
    enc_pitch.reset();
  }

  public void updatePitch() {
    /*if (!lmt_pitch.get()) ct_pitch.set(VictorSPXControlMode.PercentOutput, pos);
    else ct_pitch.set(VictorSPXControlMode.PercentOutput, 0);
*/
    ct_pitch.set(VictorSPXControlMode.PercentOutput, pos);
  }

  @Override
  public void periodic() {
    updatePitch();

    SmartDashboard.putNumber("pos_pitch", pos);
    SmartDashboard.putNumber("enc_pitch", enc_pitch.getDistance());
    SmartDashboard.putNumber("stage_elev", elevator.getStage());

  }
}
