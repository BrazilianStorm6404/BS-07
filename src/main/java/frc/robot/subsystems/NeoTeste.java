package frc.robot.subsystems;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAlternateEncoder.Type;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NeoTeste extends SubsystemBase {

  DigitalInput l1, l2;

  public NeoTeste() {

    l1 = new DigitalInput(6);
    l2 = new DigitalInput(7);

  }

  @Override
  public void periodic() {
  
    SmartDashboard.putBoolean("l2", l2.get());
    SmartDashboard.putBoolean("l1", l1.get());

  }
}
