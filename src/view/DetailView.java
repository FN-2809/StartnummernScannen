package view;

import controller.ScannerListener;
import controller.ScannerManager;
import view.detailPanels.*;

import javax.swing.*;
import java.awt.*;

public class DetailView extends JFrame {
    
    public DetailView(){
        Scoreboards scoreboards = new Scoreboards();
        ScannerListener scannerListener = new ScannerListener();
        ArrivedPanel arrivedPanel = new ArrivedPanel(scoreboards, scannerListener);
        PublicView publicView = new PublicView();
        ScannerManager scannerManager = new ScannerManager(arrivedPanel, scoreboards, publicView.getCurrentArrivalsPanel(), this);
        scannerListener.setScannerManager(scannerManager);
        ConfigurationPanel configurationPanel = new ConfigurationPanel(scannerManager);
        JPanel dataPanel = new JPanel(new GridLayout(1, 2));
        
        setTitle("Detail Ansicht");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        dataPanel.add(arrivedPanel);
        dataPanel.add(scoreboards);
        
        add(configurationPanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        
        pack();
        
        setVisible(true);
        
        scannerManager.logAllArrivedRiders();
    }
}
