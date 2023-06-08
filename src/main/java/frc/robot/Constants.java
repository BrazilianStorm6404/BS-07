package frc.robot;

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

  }

  public static class Elevator {

    public static final int id_elevR      = 13;
    public static final int id_elevL      = 14;
    public static final int id_breakPoint = 8;
    public static final int id_lmtLower   = 9;
    public static final int id1_enc       = 2;
    public static final int id2_enc       = 3;
 
  }

  public static class Arm {

    public static final int id_arm     = 13;
    public static final int id_srFront = 8;
    public static final int id_srBack  = 9;

    public static final int id_roll     = 12;
    public static final int id1_encRoll = 0;
    public static final int id2_encRoll = 1;

  }

  public static class Claw {

    public static final int id_left  = 10;
    public static final int id_right = 11;

  }

}
