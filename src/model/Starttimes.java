package model;

import java.time.LocalTime;

@SuppressWarnings("ALL")
public class Starttimes {
    private static LocalTime starttime_1 = LocalTime.now();
    private static LocalTime starttime_2 = LocalTime.now();
    private static LocalTime starttime_3 = LocalTime.now();
    
    public static synchronized void setStarttime_1(LocalTime starttime_1) {
        Starttimes.starttime_1 = starttime_1;
    }
    
    public static synchronized void setStarttime_2(LocalTime starttime_2) {
        Starttimes.starttime_2 = starttime_2;
    }
    
    public static synchronized void setStarttime_3(LocalTime starttime_3) {
        Starttimes.starttime_3 = starttime_3;
    }
    
    public static LocalTime getStarttime_1() {
        return starttime_1;
    }
    
    public static LocalTime getStarttime_2() {
        return starttime_2;
    }
    
    public static LocalTime getStarttime_3() {
        return starttime_3;
    }
    
    public static synchronized LocalTime[] getStarttimes() {
        return new LocalTime[] {starttime_1, starttime_2, starttime_3};
    }
}
