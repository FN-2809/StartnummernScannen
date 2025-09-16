package view.publicPanels;

import java.awt.*;
import java.time.LocalTime;

public class TimerPanel_3 extends TimerPanel {
    @Override
    protected Color getTrackColor() {
        return Color.GREEN;
    }
    
    @Override
    protected String getTitle() {
        return "Timer Strecke 3";
    }
    
    @Override
    protected LocalTime getStartTime() {
        return model.Starttimes.getStarttime_3();
    }
}
