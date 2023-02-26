package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
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

    double correct,
           P = 0.0;

    private DifferentialDriveOdometry m_odometry;

  public Drivetrain() {

    ct_rf = new WPI_TalonSRX(Constants.Drivetrain.id_rfront);
    ct_lb = new WPI_VictorSPX(Constants.Drivetrain.id_lback);
    ct_rb = new WPI_VictorSPX(Constants.Drivetrain.id_rback);
    ct_lf = new WPI_TalonSRX(Constants.Drivetrain.id_lfront);
    right = new MotorControllerGroup(ct_rf, ct_rb);
    left  = new MotorControllerGroup(ct_lf, ct_lb);
    drive = new DifferentialDrive(right, left);

    ct_lf.configFactoryDefault();
    ct_rf.configFactoryDefault();
    ct_lf.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    ct_rf.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    ct_lf.setSensorPhase(true);
    ct_rf.setSensorPhase(true); 

    resetEncoders();

    m_odometry = new DifferentialDriveOdometry(new Rotation2d(), 
    ct_lf.getSelectedSensorPosition() * (Math.PI* 0.1524)/4096, 
    ct_rf.getSelectedSensorPosition() * (Math.PI* 0.1524)/4096);
  
  }

  public void traction (double x, double y) {

    drive.arcadeDrive(x, y);

  }

  /*public void route (double vel, double dis) {

    correct = (dis - ((ct_lf.getSelectedSensorPosition() + ct_rf.getSelectedSensorPosition())/2)) * P;
    if (correct > vel) correct = vel;
    traction(0, correct);

  } //*/

  public Pose2d getPose() {

    return m_odometry.getPoseMeters();

  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {

    return new DifferentialDriveWheelSpeeds(
    ct_lf.getSelectedSensorVelocity() * (Math.PI/4096) * 0.1524
    , ct_rf.getSelectedSensorVelocity() * (Math.PI/4096) * 0.1524);

  }

  public void resetOdometry(Pose2d pose) {

    resetEncoders();
    m_odometry.resetPosition(new Rotation2d(), 
    ct_lf.getSelectedSensorPosition() * (Math.PI* 0.1524)/4096, 
    ct_rf.getSelectedSensorPosition() * (Math.PI* 0.1524)/4096, 
    pose);

  }

  public void tankDriveVolts (double leftVolts, double rightVolts) {

    left.setVoltage(-leftVolts);
    right.setVoltage(-rightVolts);
    drive.feed();

  }

  public double getAverageEncoderDistance() {

    return (ct_lf.getSelectedSensorPosition() + ct_rf.getSelectedSensorPosition()) * (Math.PI* 0.1524)/4096 / 2.0;

  }

  public void setMaxOutput(double maxOutput) {

    drive.setMaxOutput(maxOutput);

  }

  public void resetEncoders() {

    ct_lf.setSelectedSensorPosition(0);
    ct_rf.setSelectedSensorPosition(0);

  }

  @Override
  public void periodic() {
    
    m_odometry.update(new Rotation2d(),  
    ct_lf.getSelectedSensorPosition() * (Math.PI* 0.1524)/4096, 
    ct_rf.getSelectedSensorPosition() * (Math.PI* 0.1524)/4096);
    SmartDashboard.putNumber("ss", ct_lf.getSelectedSensorPosition() * (Math.PI* -0.1524)/4096);
    SmartDashboard.putNumber("ENC_ESQ", ct_lf.getSelectedSensorPosition());
    SmartDashboard.putNumber("ENC_DIR", ct_lf.getSelectedSensorPosition());
  }
}
