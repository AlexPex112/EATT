
package com.example.logistik.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.example.logistik.model.Product;

public class MainView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton searchButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField searchField;

    public MainView() {
        setTitle("Lagerlogistik");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
    String[] columnNames = {"ID", "Art-Nr", "Bezeichnung", "Bestand", "Halle", "Regal", "Fach", "Seriennummer"};
    tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
    // Hide ID column
    table.removeColumn(table.getColumnModel().getColumn(0));
        JScrollPane scrollPane = new JScrollPane(table);

        // Toolbar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Suchen");
    addButton = new JButton("Artikel hinzufügen");
    editButton = new JButton("Bearbeiten");
    deleteButton = new JButton("Löschen");
    topPanel.add(new JLabel("Suche:"));
    topPanel.add(searchField);
    topPanel.add(searchButton);
    topPanel.add(addButton);
    topPanel.add(editButton);
    topPanel.add(deleteButton);

        // Layout
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public void displayProducts(List<Product> products) {
        tableModel.setRowCount(0); // Clear existing data
        for (Product product : products) {
        Object[] row = {
            product.getId(),
            product.getItemNumber(),
            product.getItemDescription(),
            product.getQuantity(),
            product.getHall(),
            product.getRack(),
            product.getShelf(),
            product.getSerialNumber()
        };
            tableModel.addRow(row);
        }
    }
    
    public String getSearchTerm() {
        return searchField.getText();
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTable getTable() {
        return table;
    }

    public Integer getSelectedProductId() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) return null;
        // Model index: since ID column removed from view, map to model
        int modelRow = table.convertRowIndexToModel(viewRow);
        Object idObj = tableModel.getValueAt(modelRow, 0);
        if (idObj instanceof Number) return ((Number) idObj).intValue();
        try {
            return Integer.parseInt(idObj.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
