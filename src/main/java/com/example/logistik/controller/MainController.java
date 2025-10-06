package com.example.logistik.controller;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.example.logistik.model.DatabaseManager;
import com.example.logistik.model.Product;
import com.example.logistik.view.MainView;

public class MainController {
    private MainView view;
    private DatabaseManager dbManager;

    public MainController(MainView view, DatabaseManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;

        // Add Listeners
        this.view.getSearchButton().addActionListener(e -> searchProducts());
        this.view.getAddButton().addActionListener(e -> showAddProductDialog());
        this.view.getEditButton().addActionListener(e -> showEditProductDialog());
    this.view.getDeleteButton().addActionListener(e -> deleteSelectedProduct());

        // Initial data load
        loadAllProducts();
    }

    private void deleteSelectedProduct() {
        Integer selectedId = view.getSelectedProductId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view, "Bitte wählen Sie zuerst einen Eintrag aus.", "Keine Auswahl", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Möchten Sie den ausgewählten Artikel wirklich löschen?", "Löschen bestätigen", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dbManager.deleteProduct(selectedId);
            loadAllProducts();
        }
    }

    private void loadAllProducts() {
        List<Product> products = dbManager.getAllProducts();
        view.displayProducts(products);
    }

    private void searchProducts() {
        String searchTerm = view.getSearchTerm();
        List<Product> products = dbManager.searchProducts(searchTerm);
        view.displayProducts(products);
    }

    private void showAddProductDialog() {
        // Create a panel with input fields
        JTextField itemNumberField = new JTextField(10);
        JTextField itemDescriptionField = new JTextField(20);
        JTextField quantityField = new JTextField(5);
        JTextField hallField = new JTextField(5);
        JTextField rackField = new JTextField(5);
        JTextField shelfField = new JTextField(5);
        JTextField serialNumberField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Artikelnummer:"));
        panel.add(itemNumberField);
        panel.add(new JLabel("Bezeichnung:"));
        panel.add(itemDescriptionField);
        panel.add(new JLabel("Bestand:"));
        panel.add(quantityField);
        panel.add(new JLabel("Halle:"));
        panel.add(hallField);
        panel.add(new JLabel("Regal:"));
        panel.add(rackField);
        panel.add(new JLabel("Regalfach:"));
        panel.add(shelfField);
        panel.add(new JLabel("Seriennummer:"));
        panel.add(serialNumberField);

        int result = JOptionPane.showConfirmDialog(view, panel, "Neuen Artikel anlegen",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String itemNumber = itemNumberField.getText();
                String itemDescription = itemDescriptionField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String hall = hallField.getText();
                String rack = rackField.getText();
                String shelf = shelfField.getText();
                String serialNumber = serialNumberField.getText();

                if (itemNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Artikelnummer ist ein Pflichtfeld.", "Validierungsfehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Product newProduct = new Product(0, itemNumber, itemDescription, quantity, hall, rack, shelf, serialNumber);
                dbManager.addProduct(newProduct);
                loadAllProducts(); // Refresh the table

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Bitte geben Sie eine gültige Zahl für den Bestand ein.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditProductDialog() {
        Integer selectedId = view.getSelectedProductId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view, "Bitte wählen Sie zuerst einen Eintrag aus.", "Keine Auswahl", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product product = dbManager.getProductById(selectedId);
        if (product == null) {
            JOptionPane.showMessageDialog(view, "Produkt nicht gefunden.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField itemNumberField = new JTextField(product.getItemNumber(), 10);
        JTextField itemDescriptionField = new JTextField(product.getItemDescription(), 20);
        JTextField quantityField = new JTextField(String.valueOf(product.getQuantity()), 5);
        JTextField hallField = new JTextField(product.getHall(), 5);
        JTextField rackField = new JTextField(product.getRack(), 5);
        JTextField shelfField = new JTextField(product.getShelf(), 5);
        JTextField serialNumberField = new JTextField(product.getSerialNumber(), 15);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Artikelnummer:"));
        panel.add(itemNumberField);
        panel.add(new JLabel("Bezeichnung:"));
        panel.add(itemDescriptionField);
        panel.add(new JLabel("Bestand:"));
        panel.add(quantityField);
        panel.add(new JLabel("Halle:"));
        panel.add(hallField);
        panel.add(new JLabel("Regal:"));
        panel.add(rackField);
        panel.add(new JLabel("Regalfach:"));
        panel.add(shelfField);
        panel.add(new JLabel("Seriennummer:"));
        panel.add(serialNumberField);

        int result = JOptionPane.showConfirmDialog(view, panel, "Artikel bearbeiten",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String itemNumber = itemNumberField.getText();
                String itemDescription = itemDescriptionField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String hall = hallField.getText();
                String rack = rackField.getText();
                String shelf = shelfField.getText();
                String serialNumber = serialNumberField.getText();

                if (itemNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Artikelnummer ist ein Pflichtfeld.", "Validierungsfehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Product updated = new Product(product.getId(), itemNumber, itemDescription, quantity, hall, rack, shelf, serialNumber);
                dbManager.updateProduct(updated);
                loadAllProducts(); // Refresh the table

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Bitte geben Sie eine gültige Zahl für den Bestand ein.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
