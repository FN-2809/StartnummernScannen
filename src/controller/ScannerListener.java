package controller;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ScannerListener extends KeyAdapter {
    ScannerManager scannerManager;
    
    public void setScannerManager(ScannerManager scannerManager) {
        this.scannerManager = scannerManager;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        JTextField searchField = (JTextField) e.getSource();
        
        if (e.getKeyCode() != KeyEvent.VK_ENTER) return;
        if (searchField.getText().length() < 5) return;
        try {
            String startnummer = searchField.getDocument().getText(searchField.getDocument().getLength() - 5, 5);
            scannerManager.scannedNumber(startnummer);
            // Letzte 5 Zeichen löschen
            searchField.getDocument().remove(searchField.getDocument().getLength() - 5, 5);
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }
}
