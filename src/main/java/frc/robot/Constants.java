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

    public static final int id_elevR      = 15;
    public static final int id_elevL      = 14;
    public static final int id_lmtLower   = 1;
    public static final int id1_enc       = 2;
    public static final int id2_enc       = 3;
 
  }

  public static class Claw {

    public static final int id_left = 10;

  }

  public static class Pitch {

    public static final int id_pitch    = 11;
    public static final int id_lmtPitchBack = 0;
    public static final int id1_enc     = 5;
    public static final int id2_enc     = 6;

  }

}
