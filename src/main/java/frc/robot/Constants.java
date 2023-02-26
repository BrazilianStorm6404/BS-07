package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public final class Constants {

    //ThroughBore(8192),CTREMagTalon(4096), E4T(360);

  public static class Joystick {

    public static final int id_pilot   = 0;
    public static final int id_copilot = 1;

  }
  
  public static class Drivetrain {

    public static final int id_rfront = 0;
    public static final int id_lfront = 2;
    public static final int id_rback  = 1;
    public static final int id_lback  = 3;

    public static final double meters_per_rot = .001;

    public static final double ksVolts                      =  .80936;
    public static final double kvVoltSecondsPerMeter        = 3.8381;
    public static final double kaVoltSecondsSquaredPerMeter = 1.0081;
    public static final double kPDriveVel                   = 2.1303;

    public static final double kTrackwidthMeters                     = .69;
    public static final DifferentialDriveKinematics kDriveKinematics =
    new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final double kMaxSpeedMetersPerSecond               = 1;
    public static final double kMaxAccelerationMetersPerSecondSquared = .2;

    public static final double kRamseteB    = 2;
    public static final double kRamseteZeta = .7;

  }

  public static class Elevator {

    public static final int id_elevR    = 14;
    public static final int id_elevL    = 15;
    public static final int id_lmtUpper = 2;
    public static final int id_lmtLower = 3;
    public static final int id1_enc     = 6;
    public static final int id2_enc     = 7;

  }

  public static class Arm {

    public static final int id_arm     = 13;
    public static final int id_srFront = 4;
    public static final int id_srBack  = 5;

    public static final int id_roll     = 12;
    public static final int id1_encRoll = 0;
    public static final int id2_encRoll = 1;

  }

  public static class Claw {

    public static final int id_left  = 10;
    public static final int id_right = 11;

  }

}
