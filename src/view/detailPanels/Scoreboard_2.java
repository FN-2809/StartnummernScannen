package view.detailPanels;

import javax.swing.*;
import java.awt.*;

public class Scoreboard_2 extends Scoreboard {
    public Scoreboard_2() {
        super();
        setBorder(BorderFactory.createTitledBorder("Strecke 2"));
    }
    
    @Override
    protected Color getHeaderColor() {
        return Color.YELLOW;
    }
}
