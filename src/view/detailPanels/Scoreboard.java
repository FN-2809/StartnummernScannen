package view.detailPanels;

import javax.swing.*;
import java.awt.*;

import static view.detailPanels.Constants.*;

public abstract class Scoreboard extends JPanel {
    private final JPanel scoreboardPanel;
    private final JScrollPane scoreboardScrollPane;
    private int arrivals;

    public Scoreboard() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(TOTAL_WIDTH / 2, (TOTAL_HEIGHT - HEADER_HEIGHT) / 3));

        arrivals = 0;

        // ---------- Header ----------
        JPanel headerPanel = new JPanel(new GridLayout(1, 4));
        headerPanel.setBackground(Color.GRAY);
        headerPanel.setBorder(BorderFactory.createMatteBorder(2, 3, 2, 3, getHeaderColor()));
        headerPanel.add(createHeaderLabel("Platz"));
        headerPanel.add(createHeaderLabel("Startnummer"));
        headerPanel.add(createHeaderLabel("Vorname"));
        headerPanel.add(createHeaderLabel("Nachname"));

        // ---------- Content Panel mit dynamischen Zeilen ----------
        scoreboardPanel = new JPanel();
        scoreboardPanel.setLayout(new BoxLayout(scoreboardPanel, BoxLayout.Y_AXIS));

        // ---------- ScrollPane ----------
        scoreboardScrollPane = new JScrollPane(scoreboardPanel);
        scoreboardScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Panels hinzufügen
        add(headerPanel, BorderLayout.NORTH);
        add(scoreboardScrollPane, BorderLayout.CENTER);
    }

    // Hilfsmethode für Header-Labels
    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 25));
        return label;
    }

    // Hilfsmethode für Score-Zeilen
    public void createScoreRow(int number, String firstName, String lastName) {
        JPanel row = new JPanel(new GridLayout(1, 4));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        row.setBackground(Color.LIGHT_GRAY);

        row.add(createCell(String.valueOf(++this.arrivals)));
        row.add(createCell(String.valueOf(number)));
        row.add(createCell(firstName));
        row.add(createCell(lastName));

        this.scoreboardPanel.add(row);
        this.scoreboardPanel.revalidate();
    }

    // Hilfsmethode für einzelne Zellen
    private JLabel createCell(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 20));
        return label;
    }

    protected abstract Color getHeaderColor();
    
    public void searchRider(String nameOrNumber) {
        // Durchsuchen der Zeilen im scoreboardPanel
        for (Component comp : scoreboardPanel.getComponents()) {
            if (comp instanceof JPanel row) {
                boolean match = false;
                for (Component cellComp : row.getComponents()) {
                    if (cellComp instanceof JLabel cell) {
                        if (cell.getText().toLowerCase().contains(nameOrNumber.toLowerCase())) {
                            match = true;
                            break;
                        }
                    }
                }
                // Zeile hervorheben, wenn ein Treffer gefunden wurde
                if (match && !nameOrNumber.isEmpty()) {
                    row.setBackground(Color.YELLOW);
                    Rectangle rect = row.getBounds();
                    scoreboardPanel.scrollRectToVisible(rect);
                } else {
                    row.setBackground(Color.LIGHT_GRAY);
                }
            }
        }
        // Optional: Implementieren Sie eine Suchfunktion, um Fahrer in der Rangliste hervorzuheben oder zu filtern
        
        scoreboardScrollPane.getVerticalScrollBar().setUnitIncrement(10);
    }
    
    public void clearAllRiders() {
        this.scoreboardPanel.removeAll();
        this.scoreboardPanel.revalidate();
        this.scoreboardPanel.repaint();
        this.arrivals = 0;
    }
}

