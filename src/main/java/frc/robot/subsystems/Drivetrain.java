package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

    private WPI_TalonSRX         ct_rf, ct_lf;
    private WPI_VictorSPX        ct_rb, ct_lb;
    private MotorControllerGroup right, left;
    private DifferentialDrive    drive;

    double correct,r,l, lastDis = 0, setPoint,
           P = 0.00017;

    private Timer t, tIsMove;

    boolean isMove = false;
    

  public Drivetrain() {

    ct_rf = new WPI_TalonSRX(Constants.Drivetrain.id_rfront);
    ct_lb = new WPI_VictorSPX(Constants.Drivetrain.id_lback);
    ct_rb = new WPI_VictorSPX(Constants.Drivetrain.id_rback);
    ct_lf = new WPI_TalonSRX(Constants.Drivetrain.id_lfront);

    ct_lb.setNeutralMode(NeutralMode.Brake);
    ct_lf.setNeutralMode(NeutralMode.Brake);
    ct_rb.setNeutralMode(NeutralMode.Brake);
    ct_rf.setNeutralMode(NeutralMode.Brake);

    ct_lf.configFactoryDefault();
    ct_rf.configFactoryDefault();
    ct_lf.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    ct_rf.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    ct_lf.setSensorPhase(true);
    ct_rf.setSensorPhase(false); 

    right = new MotorControllerGroup(ct_rf, ct_rb);
    left  = new MotorControllerGroup(ct_lf, ct_lb);
    drive = new DifferentialDrive(right, left);

    resetEncoders();

    t = new Timer();
    tIsMove = new Timer();

  }

  public void traction (double y, double x) {

    drive.arcadeDrive(y, x);

    

    isMove = y != 0 || x != 0;

  }

  public void route (double vel, double dis) {

    if (lastDis != dis) {
      lastDis = dis;
      t.reset();
      t.start();
    }

    double acceleration = Math.min(t.get()/1, 1);

    if (r != l) r =l;

    setPoint = (dis * 150) - ((r+l) / 2);

    correct = acceleration * vel * setPoint * 0.0003;

    if (Math.abs(correct) > vel) correct = vel * Math.signum(correct);
    
    traction(correct, 0);
    
  }

  public boolean isMove () {
    return Math.abs(setPoint) > 1500;
  }

  public void resetEncoders() {

    ct_lf.setSelectedSensorPosition(0);
    ct_rf.setSelectedSensorPosition(0);

  }

  public double timeMove(){

    if(isMove) {      
      return tIsMove.get();

    } else {
      tIsMove.reset();
      return 0;
    }

  }

  @Override
  public void periodic() {
    
    SmartDashboard.putNumber("ENC_ESQ", ct_lf.getSelectedSensorPosition());
    SmartDashboard.putNumber("ENC_DIR", ct_rf.getSelectedSensorPosition());
    SmartDashboard.putNumber("velDrive", correct);

    r = ct_rf.getSelectedSensorPosition();
    l = ct_lf.getSelectedSensorPosition();

  }
}
