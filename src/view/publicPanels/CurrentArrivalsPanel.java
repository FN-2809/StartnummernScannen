package view.publicPanels;

import model.Rider;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static view.publicPanels.Constants.*;

public class CurrentArrivalsPanel extends JPanel {
    private final JPanel contentPanel;
    private final BlockingQueue<Rider> queue = new LinkedBlockingQueue<>();
    
    public CurrentArrivalsPanel() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(TOTAL_WIDTH, (int) (TOTAL_HEIGHT * (1 - MAIN_SPLIT))));
        
        // ---------- Header ----------
        JPanel headerPanel = new JPanel(new GridLayout(1, 5));
        headerPanel.setBackground(Color.GRAY);
        headerPanel.setBorder(BorderFactory.createMatteBorder(2, 3, 2, 3, Color.BLACK));
        headerPanel.add(createHeaderLabel("Startnummer"));
        headerPanel.add(createHeaderLabel("Vorname"));
        headerPanel.add(createHeaderLabel("Nachname"));
        headerPanel.add(createHeaderLabel("Ort"));
        headerPanel.add(createHeaderLabel("Strecke"));
        
        
        // ---------- Content Panel mit dynamischen Zeilen ----------
        contentPanel = new JPanel(new GridLayout(SIMULTANEOUS_RIDERS, 1));
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        startConsumerThread();
    }

    private void startConsumerThread() {
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    Rider data = queue.take();
                    SwingUtilities.invokeLater(() ->
                        createScoreRow(data.startnummer(), data.strecke(), data.vorname(), data.nachname(), data.ort())
                    );
                    Thread.sleep(MINIMUM_DELAY_MILLIS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        consumer.setDaemon(true);
        consumer.start();
    }
    
    // Hilfsmethode für Score-Zeilen
    private void createScoreRow(int startnummer, int strecke, String vorname, String nachname, String ort) {
        JPanel row = new JPanel(new GridLayout(1, 5));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        row.setBackground(Color.LIGHT_GRAY);
        
        row.add(createCell(String.valueOf(startnummer)));
        row.add(createCell(vorname));
        row.add(createCell(nachname));
        row.add(createCell(ort));
        row.add(createCell(String.valueOf(strecke)));
        
        contentPanel.add(row);
        if (contentPanel.getComponentCount() > SIMULTANEOUS_RIDERS) contentPanel.remove(0);
        contentPanel.revalidate();
    }
    
    // Hilfsmethode für einzelne Zellen
    private JLabel createCell(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 45));
        return label;
    }
    
    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("SansSerif", Font.BOLD, 50));
        return label;
    }
    
    public void logRider(Rider rider) {
        queue.add(rider);
    }
}
