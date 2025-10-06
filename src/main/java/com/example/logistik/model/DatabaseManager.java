// ... Datei beginnt hier ...
package com.example.logistik.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    public void updateProduct(Product product) {
        String sql = "UPDATE inventory SET item_number=?, item_description=?, quantity=?, hall=?, rack=?, shelf=?, serial_number=? WHERE id=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getItemNumber());
            pstmt.setString(2, product.getItemDescription());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setString(4, product.getHall());
            pstmt.setString(5, product.getRack());
            pstmt.setString(6, product.getShelf());
            pstmt.setString(7, product.getSerialNumber());
            pstmt.setInt(8, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static final String DB_URL = "jdbc:sqlite:logistik.db";

    public DatabaseManager() {
        createNewTable();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS inventory (" 
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "item_number TEXT NOT NULL, "
                + "item_description TEXT, "
                + "quantity INTEGER NOT NULL, "
                + "hall TEXT, "
                + "rack TEXT, "
                + "shelf TEXT, "
                + "serial_number TEXT UNIQUE" 
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM inventory";
        List<Product> products = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("item_number"),
                        rs.getString("item_description"),
                        rs.getInt("quantity"),
                        rs.getString("hall"),
                        rs.getString("rack"),
                        rs.getString("shelf"),
                        rs.getString("serial_number")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM inventory WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("item_number"),
                            rs.getString("item_description"),
                            rs.getInt("quantity"),
                            rs.getString("hall"),
                            rs.getString("rack"),
                            rs.getString("shelf"),
                            rs.getString("serial_number")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Product> searchProducts(String searchTerm) {
        String sql = "SELECT * FROM inventory WHERE item_number LIKE ? OR item_description LIKE ? OR serial_number LIKE ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String likeTerm = "%" + searchTerm + "%";
            pstmt.setString(1, likeTerm);
            pstmt.setString(2, likeTerm);
            pstmt.setString(3, likeTerm);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("item_number"),
                        rs.getString("item_description"),
                        rs.getInt("quantity"),
                        rs.getString("hall"),
                        rs.getString("rack"),
                        rs.getString("shelf"),
                        rs.getString("serial_number")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO inventory(item_number, item_description, quantity, hall, rack, shelf, serial_number) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getItemNumber());
            pstmt.setString(2, product.getItemDescription());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setString(4, product.getHall());
            pstmt.setString(5, product.getRack());
            pstmt.setString(6, product.getShelf());
            pstmt.setString(7, product.getSerialNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM inventory WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
