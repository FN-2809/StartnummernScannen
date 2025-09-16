package view.publicPanels;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class TimerPanel extends JPanel {
    private final JLabel timerLabel;
    private final JLabel startTimeLabel;
    
    public TimerPanel() {
        super(new GridLayout(2, 1));
        setBorder(BorderFactory.createTitledBorder(getTitle()));
        setBackground(getTrackColor());
        
        timerLabel = new JLabel("00:00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 100));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(timerLabel);
        
        startTimeLabel = new JLabel("Seit Start um " + getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        startTimeLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        startTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(startTimeLabel);
    }
    
    public JLabel getTimerLabel() {
        return timerLabel;
    }
    
    public JLabel getStartTimeLabel() {
        return startTimeLabel;
    }
    
    protected abstract Color getTrackColor();
    protected abstract String getTitle();
    protected abstract LocalTime getStartTime();
}
