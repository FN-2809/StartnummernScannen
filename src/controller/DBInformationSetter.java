package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBInformationSetter implements ActionListener {
    private final JTextField dbUrlField, dbUsernameField, dbTableField;
    private final JPasswordField dbPasswordField;
    private final ScannerManager scannerManager;
    
    public DBInformationSetter(JTextField dbUrlField, JTextField dbTableField, JTextField dbUsernameField, JPasswordField dbPasswordField, ScannerManager scannerManager) {
        this.dbUrlField = dbUrlField;
        this.dbTableField = dbTableField;
        this.dbUsernameField = dbUsernameField;
        this.dbPasswordField = dbPasswordField;
        this.scannerManager = scannerManager;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        model.DBCommunicator.setUrl(dbUrlField.getText());
        model.DBCommunicator.setTable(dbTableField.getText());
        model.DBCommunicator.setUser(dbUsernameField.getText());
        model.DBCommunicator.setPassword(dbPasswordField.getText());
        
        scannerManager.clearAllRiders();
        scannerManager.logAllArrivedRiders();
    }
}
