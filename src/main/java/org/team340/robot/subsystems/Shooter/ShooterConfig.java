package org.team340.robot.subsystems.Shooter;

public class ShooterConfig {

    //Speeds 'n' stuff

    //THESE SHOULD BE BACKWARDS :)
    protected static double STOPPED = 0;
    //public static double HUB = -0.775;
    protected static double SNOWBLOW = -0.25;
    public static double MAN = -0.1; // 1= 100% .1 = 10% .01 = 1%
    public static double AUTOMAN = 0;
    public static double AUTO = 0;

    //these should also be backwards, because i dont know how to invert motors yet :) || i know how to invert motors now, im not doing that || <- this guy sucks (the same guy award)
    public static void speedUp() {
        MAN -= 0.02;
        System.out.println(MAN);
    }

    public static void speedDown() {
        MAN += 0.02;
        System.out.println(MAN);
    }

    public static void autoSpeedUp() {
        MAN -= 0.02;
        System.out.println(MAN);
    }

    public static void autoSpeedDown() {
        MAN += 0.02;
        System.out.println(MAN);
    }

    //DTH = distance to hub, found in somewhere :) good luck! check advantagescope
    //1.5 dth = too close
    //2.0 dth = 55%
    //2.2 dth = 57%
    //2.7 dth = 61%
    //3.1 dth = 65%
    //3.5 dth = 68%
    //4.0 dth = 80%
    //4.7 dth = 85%
}
