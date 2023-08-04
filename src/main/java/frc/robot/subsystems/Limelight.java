package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

  double tid;
  double id = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid").getDouble(0.0);
  
  public Boolean LimeC() {
    return tid == 2 || tid == 7;
  }

  public Boolean LimeRL() {
    return tid == 1 || tid == 3 || tid == 8 || tid == 6;
  }
  
  @Override
  public void periodic() {

    double id = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid").getDouble(0.0);
    tid = id;
    SmartDashboard.putNumber("LL_ID", id);
  
  }

}
