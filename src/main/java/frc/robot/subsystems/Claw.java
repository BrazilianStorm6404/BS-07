package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {

  private WPI_VictorSPX ct_left;
  private Timer t;

  private double lastVel, timeActive = 0;

  public Claw() {

    ct_left  = new WPI_VictorSPX(Constants.Claw.id_left);

    ct_left.setNeutralMode(NeutralMode.Brake);
    
    t = new Timer();

  }

  public void collect(double vel) {

    if (lastVel != vel) {
      lastVel = vel;
      t.reset();
      t.start();
    }

    ct_left.set(VictorSPXControlMode.PercentOutput, vel);

  }

  public boolean isActive () {
    return t.get() < timeActive;
  }

  public void setTime(double time) {
    timeActive = time;
  }

  @Override
  public void periodic() {
  }
}
