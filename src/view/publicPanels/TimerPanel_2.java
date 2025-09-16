package view.publicPanels;

import java.awt.*;
import java.time.LocalTime;

public class TimerPanel_2 extends TimerPanel {
    @Override
    protected Color getTrackColor() {
        return Color.YELLOW;
    }
    
    @Override
    protected String getTitle() {
        return "Timer Strecke 2";
    }
    
    @Override
    protected LocalTime getStartTime() {
        return model.Starttimes.getStarttime_2();
    }
}
