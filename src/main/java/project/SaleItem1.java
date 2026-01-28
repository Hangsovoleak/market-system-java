package project;

public class SaleItem1 {
    private int saleItemId;
    private int saleId;
    private int productId;
    private int qty;
    private double price;

    public SaleItem1() {}

    public SaleItem1(int saleItemId, int saleId, int productId, int qty, double price) {
        this.saleItemId = saleItemId;
        this.saleId = saleId;
        this.productId = productId;
        this.qty = qty;
        this.price = price;
    }

    public int getSaleItemId() { return saleItemId; }
    public void setSaleItemId(int saleItemId) { this.saleItemId = saleItemId; }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "SaleItem{" +
                "saleItemId=" + saleItemId +
                ", saleId=" + saleId +
                ", productId=" + productId +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
