package controller;

import model.DBCommunicator;
import model.Rider;
import view.DetailView;
import view.detailPanels.ArrivedPanel;
import view.detailPanels.Scoreboards;
import view.publicPanels.CurrentArrivalsPanel;

import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

public class ScannerManager {
    private final ArrivedPanel arrivedPanel;
    private final Scoreboards scoreboards;
    private final CurrentArrivalsPanel currentArrivalsPanel;
    private final Component errorParent;
    private boolean stableConnection = false;
    private LinkedList<FailedLog> failedNumbers = new LinkedList<>();
    
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
        Rider arrivedRider = new Rider(startnummer, 0, "", "", "");
        try {
            if ((arrivedRider = DBCommunicator.riderArrived(startnummer)) == null) return;
            arrivedPanel.addRider(arrivedRider);
            scoreboards.addRider(arrivedRider);
            if (!stableConnection){
                stableConnection = true;
                logFailedNumbers();
            }
        } catch (SQLException e) {
            stableConnection = false;
            failedNumbers.add(new FailedLog(startnummer, new Timestamp(System.currentTimeMillis())));
        } finally {
            currentArrivalsPanel.logRider(arrivedRider);
        }
        
    }
    
    private void logFailedNumbers() {
        LinkedList<FailedLog> stillFailed = new LinkedList<>();
        for (FailedLog failedLog : failedNumbers) {
            try {
                DBCommunicator.logRiderWithTimestamp(failedLog.startnummer(), failedLog.timestamp());
            } catch (SQLException e) {
                stillFailed.add(failedLog);
                stableConnection = false;
            }
        }
        failedNumbers = stillFailed;
        logAllArrivedRiders();
    }
    
    public void logAllArrivedRiders(){
        Rider[] arrivedRiders = DBCommunicator.getAllArrivedRiders(errorParent);
        clearAllRiders();
        for (Rider rider : arrivedRiders){
            arrivedPanel.addRider(rider);
            scoreboards.addRider(rider);
        }
    }
    
    private void clearAllRiders() {
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
    
    public record FailedLog(int startnummer, Timestamp timestamp) {}
}
