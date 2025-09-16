package view.detailPanels;

import javax.swing.*;
import java.awt.*;

public class Scoreboard_1 extends Scoreboard {
    public Scoreboard_1() {
        super();
        setBorder(BorderFactory.createTitledBorder("Strecke 1"));
    }
    
    @Override
    protected Color getHeaderColor() {
        return Color.RED;
    }
}
