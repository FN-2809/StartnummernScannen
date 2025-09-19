package controller;

import model.DBCommunicator;
import model.Rider;
import view.DetailView;
import view.detailPanels.ArrivedPanel;
import view.detailPanels.Scoreboards;
import view.publicPanels.CurrentArrivalsPanel;

import java.awt.*;

public class ScannerManager {
    private final ArrivedPanel arrivedPanel;
    private final Scoreboards scoreboards;
    private final CurrentArrivalsPanel currentArrivalsPanel;
    private final Component errorParent;
    
    public ScannerManager(
            ArrivedPanel arrivedPanel,
            Scoreboards scoreboards,
            CurrentArrivalsPanel currentArrivalsPanel,
            DetailView detailView) {
        this.arrivedPanel = arrivedPanel;
        this.scoreboards = scoreboards;
        this.currentArrivalsPanel = currentArrivalsPanel;
        this.errorParent = detailView;
    }
    
    public void logArrivedRider(int startnummer){
        Rider arrivedRider;
        if ((arrivedRider = DBCommunicator.riderArrived(startnummer, errorParent)) == null) return;
        
        arrivedPanel.addRider(arrivedRider);
        scoreboards.addRider(arrivedRider);
        currentArrivalsPanel.logRider(arrivedRider);
    }
    
    public void logAllArrivedRiders(){
        for (Rider rider : DBCommunicator.getAllArrivedRiders(errorParent)){
            arrivedPanel.addRider(rider);
            scoreboards.addRider(rider);
        }
    }
    
    public void clearAllRiders() {
        arrivedPanel.clearAllRiders();
        scoreboards.clearAllRiders();
    }
    
    public void scannedNumber(String number) {
        if (number.charAt(0) != '#') {
            LogHelper.writeLog("Ignored scanned input: " + number);
            return;
        }
        try {
            int startnummer = Integer.parseInt(number.substring(1));
            logArrivedRider(startnummer);
            System.out.println("Scanned number: " + startnummer);
        } catch (NumberFormatException e) {
            LogHelper.writeLog("Ignored scanned input: " + number);
        }
    }
}
