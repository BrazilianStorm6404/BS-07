package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tid = table.getEntry("tid");
  double x    = tx.getDouble(0.0);
  double y    = ty.getDouble(0.0);
  double area = ta.getDouble(0.0);
  double id   = tid.getDouble(0.0);
  
  public Boolean LimeAuto() {
    return id == 2 || id == 7;
  }
  
  /*public int limeSubs() {
    if(id == 4 || id == 5){
    }*/

  @Override
  public void periodic() {

    SmartDashboard.putNumber("LL_ID", id);
  
  }

}
