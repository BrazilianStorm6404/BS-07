package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {

  public  CANSparkMax ct_elevR, ct_elevL;
  public Encoder enc_elev;
  public DigitalInput lmt_lower, lmt_up;

  public double pos, enc, adjust, moveStage, setPoint, perdaUp, erro, lastPos, ac, vel, lastAdjust;
  public double[] stages = {0, 250, 500};
  public int idStage = 0;
  public boolean breakActv = false;

  public Timer t;

  public Elevator() {

    ct_elevR = new CANSparkMax(Constants.Elevator.id_elevR, MotorType.kBrushless);
    ct_elevL = new CANSparkMax(Constants.Elevator.id_elevL, MotorType.kBrushless);

    ct_elevL.restoreFactoryDefaults();
    ct_elevR.restoreFactoryDefaults();

    ct_elevL.setIdleMode(IdleMode.kBrake);
    ct_elevR.setIdleMode(IdleMode.kBrake);

    t = new Timer();
    t.reset();
    t.start();

    lmt_lower = new DigitalInput(Constants.Elevator.id_lmtLower);
    enc_elev  = new Encoder(Constants.Elevator.id1_enc, Constants.Elevator.id2_enc);
    enc_elev.setDistancePerPulse(1);
    enc_elev.reset();

    //adjust = 0;
    setPoint = 0;

  }


  public void controlAuto (double v, double h) {

    setPoint = h;

    vel = (setPoint - enc) * 0.005;

    if (!lmt_lower.get() && vel < 0) {
      vel = 0;
      enc_elev.reset();
    } 

    if (vel > 0) {
      vel = Math.signum(vel) * Math.min(Math.abs(vel), 0.1);
      ct_elevR.set(vel);
      ct_elevL.set(vel);
    } 
    else {
      vel = Math.signum(vel) * Math.min(Math.abs(vel), 0.2);
      ct_elevR.set(vel);
      ct_elevL.set(vel);
    }
  }

  public boolean isMove (){
    return Math.abs(enc) > 420;
  }

  public boolean isfinal (){
    return !lmt_lower.get();
  }

  public void updateStages() {

    setPoint = moveStage;

    if (lastPos != setPoint) {
      lastPos = setPoint;
      t.reset();
      t.start();
    }

    ac = Math.min(t.get()/1.0, 1);

    //setPoint =+ adjust;

    vel = (setPoint - enc) * ac * 0.005;
    

    if (!lmt_lower.get()){
      enc_elev.reset();
      if (vel < 0) vel = 0;
    } 

    /*if (vel > 0) {
      adjust = 0;
    }*/
    
    if (vel > 0) {
      vel = Math.signum(vel) * Math.min(Math.abs(vel), 0.1);
      ct_elevR.set(vel);
      ct_elevL.set(vel);
    } 
    else {
      vel = Math.signum(vel) * Math.min(Math.abs(vel), 0.8);
      ct_elevR.set(vel);
      ct_elevL.set(vel);
    }
    
  }

  /*public void adjust(double ad){
    if ((ad > 0 && lmt_up.get()) || (ad < 0 && lmt_lower.get())) {
      adjust += ad * 10;
    } 
  
  } */

  public void resetEnc_elev() {

    enc_elev.reset();

  }
  
  public void setCurrentPoint() {
    moveStage = enc;
    //adjust = 0;
  }

  public void upStage(){
    //adjust = 0;
    if (idStage + 1 < stages.length) {
      moveStage = stages[++idStage];
    }

  }

  public void downStage(){
    //adjust = 0;
    if (idStage > 0) {
      moveStage = stages[--idStage];
    }
    
  }
  
  public double getEnc() {
    return enc_elev.getDistance();
  }

  public int getStage() {
    return idStage;
  }

  @Override
  public void periodic() {

    lmt_lower.get();
    enc = enc_elev.getDistance();

    SmartDashboard.putBoolean("LMT_lower", lmt_lower.get());
    SmartDashboard.putNumber("VEL_ELEV", vel);
    SmartDashboard.putNumber("ENC_ELEV", enc);

  }
}
