package model;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import static view.detailPanels.Constants.DB_TABLE_WHITELIST;

@SuppressWarnings("SpellCheckingInspection")
public class DBCommunicator {
    private static volatile String url = "jdbc:mysql://localhost:3306/surm";
    private static volatile String table = "jos_surm";
    private static volatile String user = "root";
    private static volatile String password = "REMOVED";
    
    public static Rider riderArrived(int startnummer, Component errorParent) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement ps2 = conn.prepareStatement("SELECT strecke, vname, nname, ort, status FROM " + table + " WHERE snr = ?");
            ps2.setInt(1, startnummer);
            ResultSet rs = ps2.executeQuery();
            if (!rs.next()) return null;
            if (rs.getTimestamp("status") != null) return null;
            
            PreparedStatement ps = conn.prepareStatement("UPDATE " + table + " SET status = NOW() WHERE snr = ?");
            ps.setInt(1, startnummer);
            if (ps.executeUpdate() == 0) return null;
            
            return new Rider(startnummer,
                    Integer.parseInt(rs.getString("strecke")),
                    rs.getString("vname"),
                    rs.getString("nname"),
                    rs.getString("ort"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(errorParent, "Fehler bei der Datenbankverbindung:\n" + e.getMessage(), "Datenbankfehler", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (NumberFormatException e) {
            return new Rider(startnummer, -1, "Fehlerhafte", "Startnummer", "");
        }
    }
    
    public static Rider[] getAllArrivedRiders(Component errorParent) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            ResultSet countRs = conn.createStatement().executeQuery("SELECT COUNT(*) AS count FROM " + table + " WHERE status IS NOT NULL");
            if (!countRs.next() || countRs.getInt("count") == 0) return new Rider[0];
            int size = countRs.getInt("count");
            PreparedStatement ps = conn.prepareStatement("SELECT snr, strecke, vname, nname, ort, status FROM " + table + " WHERE status IS NOT NULL ORDER BY status ASC");
            ResultSet rs = ps.executeQuery();
            Rider[] riders = new Rider[size];
            int index = 0;
            while (rs.next()) {
                try {
                    Integer.parseInt(rs.getString("strecke"));
                } catch (NumberFormatException e) {
                    riders[index++] = new Rider(
                            rs.getInt("snr"),
                            -1,
                            rs.getString("vname"),
                            rs.getString("nname"),
                            rs.getString("ort")
                    );
                    continue;
                }
                riders[index++] = new Rider(
                        rs.getInt("snr"),
                        Integer.parseInt(rs.getString("strecke")),
                        rs.getString("vname"),
                        rs.getString("nname"),
                        rs.getString("ort")
                );
            }
            return riders;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(errorParent, "Fehler bei der Datenbankverbindung:\n" + e.getMessage(), "Datenbankfehler", JOptionPane.ERROR_MESSAGE);
            return new Rider[0];
        }
    }
    
    public static void setUrl(String url) {
        DBCommunicator.url = url;
    }
    
    public static void setTable(String table) {
        for (String whitelistedName : DB_TABLE_WHITELIST) {
            if (whitelistedName.equals(table)) {
                DBCommunicator.table = table;
                return;
            }
        }
    }
    
    public static void setUser(String user) {
        DBCommunicator.user = user;
    }
    
    public static void setPassword(String password) {
        DBCommunicator.password = password;
    }
}
