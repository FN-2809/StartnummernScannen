package view.publicPanels;

import controller.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static view.publicPanels.Constants.*;

public class Timers extends JPanel {
    public Timers() {
        super(new GridLayout(1, 3));
        setPreferredSize(new Dimension(TOTAL_WIDTH, (int) (TOTAL_HEIGHT * MAIN_SPLIT)));
        TimerPanel[] timers = new TimerPanel[]{new TimerPanel_1(), new TimerPanel_2(), new TimerPanel_3()};
        for (TimerPanel timer : timers) add(timer);
        
        Timer timer = new Timer(1000, new Stopwatch(
                Arrays.stream(timers).map(TimerPanel::getTimerLabel).toArray(JLabel[]::new),
                Arrays.stream(timers).map(TimerPanel::getStartTimeLabel).toArray(JLabel[]::new)));
        timer.start();
    }
}
