package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
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
  DigitalInput lmt_pitchFront, lmt_pitchBack;
  double posStages[] = {1000, 2000, 3000, 4000};
  double setPoint, pos;

  Elevator elevator;
  Drivetrain drive;

  public Pitch() {

    ct_pitch  = new VictorSPX(Constants.Pitch.id_pitch);
    lmt_pitchFront = new DigitalInput(Constants.Pitch.id_lmtPitchFront);
    lmt_pitchBack = new DigitalInput(Constants.Pitch.id_lmtPitchBack);
    enc_pitch = new Encoder(Constants.Pitch.id1_enc, Constants.Pitch.id2_enc);
    enc_pitch.setDistancePerPulse(1);
    enc_pitch.reset();
    //elevator = elev;
    //drive = dr;

    ct_pitch.setNeutralMode(NeutralMode.Brake);

    resetEnc();

  }

  public void move_pitch(double adjust, double v){

    /*if (drive.timeMove() > 2 && elevator.getStage() == 0) {
      pos = enc_pitch.getDistance() * -0.0009;

    } else {
      pos = (posStages[elevator.getStage()] + a - enc_pitch.getDistance()) * 0.0009;

    }*/


    pos = (adjust * 500 - enc_pitch.getDistance()) * 0.005;

    if (pos > 0) {

      pos = Math.signum(pos) * Math.min(Math.abs(pos), 0.2);
      
    } else {

      pos = Math.signum(pos) * Math.min(Math.abs(pos), v);
      
    }

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

  }
}
