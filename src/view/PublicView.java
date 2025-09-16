package view;

import view.publicPanels.CurrentArrivalsPanel;
import view.publicPanels.Timers;

import javax.swing.*;
import java.awt.*;

import static view.publicPanels.Constants.*;

public class PublicView extends JFrame {
    private final CurrentArrivalsPanel currentArrivalsPanel;
    
    public PublicView() {
        Timers timers = new Timers();
        this.currentArrivalsPanel = new CurrentArrivalsPanel();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(MAIN_SPLIT);
        splitPane.setDividerLocation(MAIN_SPLIT);
        splitPane.setDividerSize(0);
        splitPane.setTopComponent(timers);
        splitPane.setBottomComponent(currentArrivalsPanel);
        
        setTitle("Öffentliche Anzeige");
        
        // Alle verfügbaren Bildschirme holen
        Rectangle bounds = getRectangle();
        
        // Frame auf diesem Bildschirm platzieren
        int x = bounds.x + (bounds.width - TOTAL_WIDTH) / 2;
        int y = bounds.y + (bounds.height - TOTAL_HEIGHT) / 2;
        setLocation(x, y);
        
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(new Dimension(TOTAL_WIDTH, TOTAL_HEIGHT));
        
        add(splitPane, BorderLayout.CENTER);
        
        pack();
        
        setVisible(true);
    }
    
    private static Rectangle getRectangle() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        
        // Beispiel: zweiten Bildschirm nehmen, falls vorhanden
        int screenIndex = 1; // 0 = Hauptbildschirm, 1 = zweiter Bildschirm
        if (screenIndex >= screens.length) {
            screenIndex = 0; // Falls kein zweiter Bildschirm da ist → Hauptbildschirm
        }
        
        // Den Bereich (Bounds) des gewünschten Bildschirms holen
        return screens[screenIndex].getDefaultConfiguration().getBounds();
    }
    
    public CurrentArrivalsPanel getCurrentArrivalsPanel() {
        return currentArrivalsPanel;
    }
}
