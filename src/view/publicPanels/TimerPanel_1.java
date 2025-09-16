package view.publicPanels;

import java.awt.*;
import java.time.LocalTime;

public class TimerPanel_1 extends TimerPanel {
    @Override
    protected Color getTrackColor() {
        return Color.RED;
    }
    
    @Override
    protected String getTitle() {
        return "Timer Strecke 1";
    }
    
    @Override
    protected LocalTime getStartTime() {
        return model.Starttimes.getStarttime_1();
    }
}
