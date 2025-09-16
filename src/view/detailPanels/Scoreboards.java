package view.detailPanels;

import model.Rider;

import javax.swing.*;
import java.awt.*;

public class Scoreboards extends JPanel {
    private final Scoreboard[] scoreboards;
    
    public Scoreboards() {
        super(new GridLayout(3, 1));
        scoreboards = new Scoreboard[] {new Scoreboard_1(), new Scoreboard_2(), new Scoreboard_3()};
        for (Scoreboard scoreboard : scoreboards) add(scoreboard);
    }
    
    public void addRider(Rider rider) {
        if (rider.strecke() < 1 || rider.strecke() > 3) return;
        scoreboards[rider.strecke() - 1].createScoreRow(rider.startnummer(), rider.vorname(), rider.nachname());
    }
    
    public void searchRider(String nameOrNumber) {
        for (Scoreboard scoreboard : scoreboards) {
            scoreboard.searchRider(nameOrNumber);
        }
    }
    
    public void clearAllRiders() {
        for (Scoreboard scoreboard : scoreboards) {
            scoreboard.clearAllRiders();
        }
    }
}
