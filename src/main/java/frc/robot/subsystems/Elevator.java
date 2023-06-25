package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {

  private WPI_VictorSPX ct_elevR, ct_elevL;
  private Encoder enc_elev;
  private DigitalInput lmt_lower;

  public double pos, enc, adjust, moveStage, setPoint, perdaUp, erro;
  public double[] stages = {0, 2800, 2900, 4000};
  public int idStage = 0;
  public boolean breakActv = false;

  public Timer t;

  public Elevator() {

    ct_elevR = new  WPI_VictorSPX(Constants.Elevator.id_elevR);
    ct_elevL = new  WPI_VictorSPX(Constants.Elevator.id_elevL);

    //ct_elevL.setNeutralMode(NeutralMode.Brake);
    ct_elevR.setNeutralMode(NeutralMode.Brake);

    //ct_elevR.setInverted(true);
    //ct_elevL.follow(ct_elevR);

    lmt_lower = new DigitalInput(Constants.Elevator.id_lmtLower);
    enc_elev = new Encoder(Constants.Elevator.id1_enc, Constants.Elevator.id2_enc);
    enc_elev.setDistancePerPulse(1);
    enc_elev.reset();

    adjust = 0;
    setPoint = 0;

  }

  public boolean getLmt() {
    return lmt_lower.get();
  }

  public void controlAuto (double h, double v) {

    pos = h;

  }

  public void updateStages() {

    setPoint = pos + moveStage + adjust;

    double vel = (setPoint - enc) * 0.001;
    ct_elevR.set(vel);
    if (vel < 0 && lmt_lower.get()) {

      ct_elevR.set(0);

    } else {
      ct_elevR.set(vel);

    }

  }

  public boolean isMove () {
    return !(Math.abs(setPoint - enc_elev.get()) < 500);
  }

  public void move (double v) {

    if ((lmt_lower.get() && v > 0)) {
      ct_elevR.set(0);
      SmartDashboard.putNumber("v", 0);

    } else {
      ct_elevR.set(-v);
      SmartDashboard.putNumber("v", v);

    }

    SmartDashboard.putBoolean("breakActv", breakActv);
    SmartDashboard.putBoolean("lmt_lower.get()", lmt_lower.get());

  }

  public void adjust(double vel){
    if ((vel > 0 && setPoint < stages[stages.length-1]) || (vel < 0 && (setPoint > stages[0] ))) {
      adjust += vel * 50;
    } 
  }

  public void resetEnc_elev() {

    enc_elev.reset();

  }
  
  public void setCurrentPoint() {
    moveStage = enc;
    adjust = 0;
  }

  public void upStage(){
    adjust = 0;
    if (idStage + 1 < stages.length) {
      moveStage = stages[++idStage];
    }

  }

  public void downStage(){
    adjust = 0;
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

    enc = enc_elev.getDistance();
    updateStages();
    SmartDashboard.putNumber("pos", pos);
    SmartDashboard.putNumber("enc", enc_elev.getDistance());
    SmartDashboard.putBoolean("mlt", getLmt());

  }
}
