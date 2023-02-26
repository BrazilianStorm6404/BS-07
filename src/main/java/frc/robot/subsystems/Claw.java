package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {

  WPI_VictorSPX ct_left, ct_right;

  public Claw() {

    ct_left  = new WPI_VictorSPX(Constants.Claw.id_left);
    ct_right = new WPI_VictorSPX(Constants.Claw.id_right);

  }

  public void collect(double vel) {

    ct_left.set(VictorSPXControlMode.PercentOutput, .5);
    ct_right.follow(ct_left);
    ct_right.setInverted(true);

  }

  @Override
  public void periodic() {
  }
}
