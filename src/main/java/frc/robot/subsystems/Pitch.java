package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pitch extends SubsystemBase {

  public CANSparkMax ct_pitch;
  public Encoder enc_pitch;

  public DigitalInput lmt_pitch;
  public double posStages[] = {800, 725, 650};
  public double setPoint, enc, moveStage, stages, pos, correct;
  public int idStage = 0;

  public Pitch() {

    ct_pitch  = new CANSparkMax(Constants.Pitch.id_pitch, MotorType.kBrushed);
    ct_pitch.setInverted(true);

    enc_pitch = new Encoder(Constants.Pitch.id1_enc, Constants.Pitch.id2_enc);
    enc_pitch.setDistancePerPulse(1);
    enc_pitch.reset();

    lmt_pitch = new DigitalInput(Constants.Pitch.id_lmtPitchBack);
    resetEnc();

    pos = 0;
  }

  public void move (double v) {

    ct_pitch.setVoltage(v * 6);
    if (!lmt_pitch.get() && ct_pitch.get() < 0) ct_pitch.set(0.0);

  }

  /*public void updatePitch(double c ) {

    pos = posStages[idStage];
    
    pos = (pos - enc) * 0.002 + c;

    if (!lmt_pitch.get() && pos < 0) {
      ct_pitch.set(0.0);
      enc_pitch.reset();
      } 

    if (pos > 0) {
    pos = Math.signum(pos) * Math.min(Math.abs(pos), 0.5);
    ct_pitch.set(pos);
    } else {
      pos = Math.signum(pos) * Math.min(Math.abs(pos), 0.6);
      ct_pitch.set(pos);
    }  
  }*/

  public void move_pitch(double v, double pos){

    pos = (pos - enc) * 0.002;

    if (!lmt_pitch.get() && pos < 0) {
      ct_pitch.set(0.0);
      } 

    pos = Math.signum(pos) * Math.min(Math.abs(pos), 0.5);
    ct_pitch.set(pos);

  }

  public boolean isMove (){
  return Math.abs(enc) > 700;
  }

  public void setCurrentPoint() {
    moveStage = enc;

  }

  public void upStage(){
    if (idStage + 1 < posStages.length) {
      idStage++;
    }
  }  

  public void downStage(){
    if (idStage > 0) {
      moveStage = posStages[--idStage];
    }
  }  

  public void pitchEnc(){
    enc_pitch.getDistance();
  }

  public void resetEnc(){
    enc_pitch.reset();
  }

  
  @Override
  public void periodic() {
    enc = enc_pitch.getDistance();

    SmartDashboard.putNumber("pos_pitch", pos);
    SmartDashboard.putNumber("enc_pitch", enc_pitch.getDistance());
    SmartDashboard.putBoolean("lm_Back", lmt_pitch.get());
    SmartDashboard.putNumber("VEL_PITCH", ct_pitch.get());

  }
}
