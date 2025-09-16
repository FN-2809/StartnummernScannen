package view.detailPanels;

import controller.DBInformationSetter;
import controller.ScannerManager;
import controller.StartTimeSetter;
import raven.datetime.TimePicker;

import static view.detailPanels.Constants.*;
import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JPanel {
    private JLabel timeLabel1;
    private JLabel timeLabel2;
    private JLabel timeLabel3;
    private JLabel dbUrlLabel;
    private JLabel dbTableLabel;
    private JLabel dbUsernameLabel;
    private JLabel dbPasswordLabel;
    
    private final JFormattedTextField timeField1 = new JFormattedTextField();
    private final JFormattedTextField timeField2 = new JFormattedTextField();
    private final JFormattedTextField timeField3 = new JFormattedTextField();
    
    private final JTextField dbUrlField = new JTextField();
    private final JTextField dbTableField = new JTextField();
    private final JTextField dbUsernameField = new JTextField();
    private final JPasswordField dbPasswordField = new JPasswordField();
    
    private JButton dbButton;
    
    private final TimePicker timePicker1 = new TimePicker();
    private final TimePicker timePicker2 = new TimePicker();
    private final TimePicker timePicker3 = new TimePicker();
    
    public ConfigurationPanel(ScannerManager scannerManager) {
        super(new GridLayout(1, 7));
        setPreferredSize(new Dimension(HEADER_WIDTH, HEADER_HEIGHT));
        
        initComponents(scannerManager);
        
        add(timeLabel1);
        add(timeField1);
        add(timeLabel2);
        add(timeField2);
        add(timeLabel3);
        add(timeField3);
        add(dbUrlLabel);
        add(dbUrlField);
        add(dbTableLabel);
        add(dbTableField);
        add(dbUsernameLabel);
        add(dbUsernameField);
        add(dbPasswordLabel);
        add(dbPasswordField);
        add(dbButton);
    }
    
    private void initComponents(ScannerManager scannerManager) {
        timeLabel1 = new JLabel("Start Strecke 1: ");
        timeLabel2 = new JLabel("Start Strecke 2: ");
        timeLabel3 = new JLabel("Start Strecke 3: ");
        dbUrlLabel = new JLabel("Database URL: ");
        dbTableLabel = new JLabel("Database Table: ");
        dbUsernameLabel = new JLabel("Database Username: ");
        dbPasswordLabel = new JLabel("Database Password: ");
        
        dbButton = new JButton("set db information");
        dbButton.addActionListener(new DBInformationSetter(dbUrlField, dbTableField, dbUsernameField, dbPasswordField, scannerManager));
        
        for (JLabel label : new JLabel[]{timeLabel1, timeLabel2, timeLabel3, dbUrlLabel, dbTableLabel, dbUsernameLabel, dbPasswordLabel}) label.setHorizontalAlignment(SwingConstants.RIGHT);
        
        StartTimeSetter startTimeSetter = new StartTimeSetter(new TimePicker[]{timePicker1, timePicker2, timePicker3});
        
        for (TimePicker timePicker : new TimePicker[]{timePicker1, timePicker2, timePicker3}) {
            timePicker.setOrientation(SwingConstants.HORIZONTAL);
            timePicker.set24HourView(true);
            timePicker.addTimeSelectionListener(startTimeSetter);
        }
        
        timePicker1.setEditor(timeField1);
        timePicker2.setEditor(timeField2);
        timePicker3.setEditor(timeField3);
    }
}
