package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {

  private WPI_VictorSPX ct_arm;
  private DigitalInput lmt_front;
  public Timer t;

  public Arm() {

    ct_arm    = new WPI_VictorSPX(Constants.Arm.id_arm);
    lmt_front = new DigitalInput(0);

  }

  public void moveArm (double vel) {

    ct_arm.set(vel);

  }

  public boolean lmt_arm () {
    return lmt_front.get();
  }

  @Override
  public void periodic() {

  }
}
