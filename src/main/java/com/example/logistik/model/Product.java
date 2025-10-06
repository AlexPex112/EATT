package com.example.logistik.model;

public class Product {
    private int id;
    private String itemNumber;
    private String itemDescription;
    private int quantity;
    private String hall;
    private String rack;
    private String shelf;
    private String serialNumber;

    public Product(int id, String itemNumber, String itemDescription, int quantity, String hall, String rack, String shelf, String serialNumber) {
        this.id = id;
        this.itemNumber = itemNumber;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.hall = hall;
        this.rack = rack;
        this.shelf = shelf;
        this.serialNumber = serialNumber;
    }

    // Getters
    public int getId() { return id; }
    public String getItemNumber() { return itemNumber; }
    public String getItemDescription() { return itemDescription; }
    public int getQuantity() { return quantity; }
    public String getHall() { return hall; }
    public String getRack() { return rack; }
    public String getShelf() { return shelf; }
    public String getSerialNumber() { return serialNumber; }
}
