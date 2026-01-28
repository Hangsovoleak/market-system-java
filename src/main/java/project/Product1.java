package project;

public class Product1 {
    private int id;
    private String name;
    private double price;
    private int qty;
    private int supplierId;

    public Product1() {}

    public Product1(int id, String name, double price, int qty, int supplierId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.supplierId = supplierId;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    @Override
    public String toString() {
        return name + " ($" + price + ") - Qty: " + qty;
    }
}
