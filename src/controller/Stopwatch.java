package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static view.publicPanels.Constants.NUMBER_OF_TIMERS;

public class Stopwatch implements ActionListener {
    private final JLabel[] timerLabels;
    private final JLabel[] startTimeLabels;
    
    public Stopwatch(JLabel[] timerLabels, JLabel[] startTimeLabels) {
        if (timerLabels == null) throw new NullPointerException();
        if (timerLabels.length != NUMBER_OF_TIMERS) throw new IllegalArgumentException("Number of timerLabels must be " + NUMBER_OF_TIMERS);
        
        if (startTimeLabels == null) throw new NullPointerException();
        if (startTimeLabels.length != NUMBER_OF_TIMERS) throw new IllegalArgumentException("Number of startTimeLabels must be " + NUMBER_OF_TIMERS);
        
        this.timerLabels = timerLabels;
        this.startTimeLabels = startTimeLabels;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < NUMBER_OF_TIMERS; i++) {
            Duration duration = Duration.between(model.Starttimes.getStarttimes()[i], LocalDateTime.now());
            long seconds = duration.getSeconds(); // gesamte Dauer in Sekunden
            long absSeconds = Math.abs(seconds);
            
            long hours   = absSeconds / 3600;
            long minutes = (absSeconds % 3600) / 60;
            long secs    = absSeconds % 60;
            
            String formatted = String.format("%02d:%02d:%02d", hours, minutes, secs);
            
            timerLabels[i].setText(formatted);
            startTimeLabels[i].setText("Seit Start um " + model.Starttimes.getStarttimes()[i].format(DateTimeFormatter.ofPattern("HH:mm")));
        }
    }
}
