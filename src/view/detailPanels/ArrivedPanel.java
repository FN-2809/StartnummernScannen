package view.detailPanels;

import model.Rider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;

import static view.detailPanels.Constants.*;

public class ArrivedPanel extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable table;
    
    public ArrivedPanel(Scoreboards scoreboards, KeyAdapter scannerListener) {
        setPreferredSize(new Dimension(TOTAL_WIDTH / 2, TOTAL_HEIGHT - HEADER_HEIGHT));
        setBorder(BorderFactory.createTitledBorder("Angekommene Fahrer"));
        setLayout(new BorderLayout());

        // Suchfeld oben
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Suche (Startnummer oder Nachname) und gleichzeitig Scanner input(!): ");
        JTextField searchField = new JTextField();
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        // Spalten: Startnummer, Vorname, Nachname, Strecke
        String[] columns = {"Startnummer", "Vorname", "Nachname", "Strecke"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Monospaced", Font.PLAIN, 18));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 20));
        table.getTableHeader().setBackground(Color.GRAY);
        table.getTableHeader().setForeground(Color.WHITE);

        // Sortierung nach Startnummer (Spalte 0) aufsteigend
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setComparator(0, (a, b) -> {
            try {
                return Integer.compare(Integer.parseInt(a.toString()), Integer.parseInt(b.toString()));
            } catch (NumberFormatException e) {
                return a.toString().compareTo(b.toString());
            }
        });
        table.setRowSorter(sorter);
        sorter.toggleSortOrder(0); // Standardmäßig aufsteigend sortieren

        searchField.addKeyListener(scannerListener);
        
        // Filter für Suche
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchField.getText().trim();
                if (text.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(new RowFilter<>() {
                        @Override
                        public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                            String startnummer = entry.getStringValue(0);
                            String nachname = entry.getStringValue(2).toLowerCase();
                            String suchbegriff = text.toLowerCase();
                            return startnummer.contains(suchbegriff) || nachname.contains(suchbegriff);
                        }
                    });
                }
                scoreboards.searchRider(text);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addRider(Rider rider) {
        tableModel.addRow(new Object[]{rider.startnummer(), rider.vorname(), rider.nachname(), rider.strecke()});
    }
    
    public void clearAllRiders() {
        tableModel.setRowCount(0);
        if (table.getRowSorter() != null) {
            table.getRowSorter().allRowsChanged();
        }
    }
}
