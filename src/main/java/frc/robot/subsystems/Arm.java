package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {

  WPI_VictorSPX ct_arm, ct_roll;
  Encoder enc_roll;
  DigitalInput sr_front, sr_back;

  double pos1,
         setPoint1;

  double pos2,
         setPoint2;

  public Arm() {

    ct_arm  = new WPI_VictorSPX(Constants.Arm.id_arm);
    ct_roll = new WPI_VictorSPX(Constants.Arm.id_roll);

    sr_front = new DigitalInput(Constants.Arm.id_srFront);
    sr_back  = new DigitalInput(Constants.Arm.id_srBack);

    enc_roll = new Encoder(Constants.Arm.id1_encRoll, Constants.Arm.id2_encRoll);
    enc_roll.setDistancePerPulse(.5);

  }

  public void moveArm (double vel) {

    ct_arm.set(vel);

  }

  public void moveRoll (double vel) {
    
    ct_roll.set(vel);

  }

  public void position1 () {

    pos1 = (setPoint1 - enc_roll.getDistance()) * 0.01;
    moveRoll(pos1);

  }

  public void position2 () {

    pos2 = (setPoint2 - enc_roll.getDistance()) * 0.01;
    moveRoll(pos2);

  }

  public boolean getSr_front() {

    return sr_front.get();

  }

  public boolean getSr_back() {

    return sr_back.get();

  }
  

  @Override
  public void periodic() {

    SmartDashboard.putBoolean("BRACO_FRENTE", sr_front.get());
    SmartDashboard.putBoolean("BRACO_TRAS", sr_back.get());
    SmartDashboard.putNumber("enc_roll", enc_roll.getDistance());

  }
}
