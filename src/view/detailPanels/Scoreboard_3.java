package view.detailPanels;

import javax.swing.*;
import java.awt.*;

public class Scoreboard_3 extends Scoreboard {
    public Scoreboard_3() {
        super();
        setBorder(BorderFactory.createTitledBorder("Strecke 3"));
    }
    
    @Override
    protected Color getHeaderColor() {
        return Color.GREEN;
    }
}
