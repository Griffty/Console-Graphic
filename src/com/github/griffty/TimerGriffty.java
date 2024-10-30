package com.github.griffty;

import java.util.Date;

public class TimerGriffty {
    public static double MakeShapeTime = 0;
    public static double updateSettingsTime = 0;
    public static double clearTime = 0;
    public static double moveTime = 0;
    public static double drawTime = 0;
    public static double refreshTime = 0;
    private static double startTime;
    private static double startMovableTime;

    public static void start(){
        startTime = new Date().getTime();
    }
    public static double end(){
        double endTime = new Date().getTime();
        return (endTime -startTime);
    }
    public static void movableStart(){
        startMovableTime = new Date().getTime();
    }
    public static double movableEnd(){
        double endMovableTime = new Date().getTime();
        return (endMovableTime -startMovableTime);
    }
}
