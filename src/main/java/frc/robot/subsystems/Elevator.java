package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.PIDController;
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
  public double[] stages = {0, 300, 600, 1000};
  public int idStage = 0;
  public boolean breakActv = false;
  public PIDController pid;

  public Timer t;

  public Elevator() {

    ct_elevR = new  WPI_VictorSPX(Constants.Elevator.id_elevR);
    ct_elevL = new  WPI_VictorSPX(Constants.Elevator.id_elevL);

    ct_elevL.setNeutralMode(NeutralMode.Brake);
    ct_elevR.setNeutralMode(NeutralMode.Brake);

    //ct_elevR.setInverted(true);
    ct_elevL.follow(ct_elevR);

    lmt_lower = new DigitalInput(Constants.Elevator.id_lmtLower);
    enc_elev = new Encoder(Constants.Elevator.id1_enc, Constants.Elevator.id2_enc);
    enc_elev.setDistancePerPulse(1);
    enc_elev.reset();

    adjust = 0;
    setPoint = 0;

    pid = new PIDController(0.0035, 0, 0);

  }

  public boolean getLmt() {
    return lmt_lower.get();
  }

  public void controlAuto (double h, double v) {

    pos = h;

  }

  public void updateStages() {

    setPoint = Math.max(pos + moveStage + adjust, 0);

    double vel = (pid.calculate(enc, setPoint));

    //double vel = (setPoint - enc) * 0.003;

    if (vel < 0) {
      vel = Math.signum(vel) * Math.min(Math.abs(vel), 0.75);
      ct_elevR.set(vel);

    } else {
      vel = Math.signum(vel) * Math.min(Math.abs(vel), 0.2);
      ct_elevR.set(vel);

    }
    SmartDashboard.putNumber("ELEVATOR_VEL", vel);
    
  }

  public boolean isMove () {
    return !(Math.abs(setPoint - enc_elev.get()) < 500);
  }

  public void move (double v) {

    if ((lmt_lower.get() && v < 0)) {
      ct_elevR.set(0);
      SmartDashboard.putNumber("v", 0);

    } else {
      ct_elevR.set(v);
      SmartDashboard.putNumber("v", v);

    }

    SmartDashboard.putBoolean("lmt_lower.get()", lmt_lower.get());

  }

  public void adjust(double ad){
    if ((ad > 0 && setPoint < stages[stages.length-1]) || (ad < 0 && (setPoint > stages[0] ))) {
      adjust += ad * 10;
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

    SmartDashboard.putNumber("SETPOINT", setPoint);
    SmartDashboard.putNumber("ENCODER_ELEVATOR", enc_elev.getDistance());
    SmartDashboard.putBoolean("LIMIT_ELEVADOR", lmt_lower.get());

  }
}
