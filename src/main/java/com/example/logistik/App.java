package com.example.logistik;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.example.logistik.controller.MainController;
import com.example.logistik.model.DatabaseManager;
import com.example.logistik.view.MainView;
import com.formdev.flatlaf.FlatDarkLaf;

public class App {
    public static void main(String[] args) {
        // Set the modern look and feel
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Failed to initialize LaF: " + e.getMessage());
        }

        // Run the application
        SwingUtilities.invokeLater(() -> {
            MainView view = new MainView();
            DatabaseManager dbManager = new DatabaseManager();
            new MainController(view, dbManager);
            view.setVisible(true);
        });
    }
}
