package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {

  CANSparkMax ct_elevR, ct_elevL;
  Encoder enc_elev;
  DigitalInput lmt_upper,lmt_lower;

  public Elevator() {

    ct_elevR = new  CANSparkMax(Constants.Elevator.id_elevR, MotorType.kBrushed);
    ct_elevL = new  CANSparkMax(Constants.Elevator.id_elevL, MotorType.kBrushed);
    lmt_upper = new DigitalInput(Constants.Elevator.id_lmtUpper);
    lmt_lower = new DigitalInput(Constants.Elevator.id_lmtLower);
    enc_elev = new Encoder(Constants.Elevator.id1_enc, Constants.Elevator.id2_enc);
    enc_elev.setDistancePerPulse(.5);

  }

  public void move_elev (double vel) {

    ct_elevR.set(vel);
    ct_elevL.follow(ct_elevR);
    //.ct_elevL.setInverted(true);

  }

  public void reset_encElev () {

    enc_elev.reset();

  }

  @Override
  public void periodic() {

    SmartDashboard.putNumber("enc_elev", enc_elev.getDistance());

  }
}
