package project;

import project.DBConnection;
import project.Product1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // ===== 1. Insert Product =====
    public boolean addProduct(Product1 product) {
        String sql = "INSERT INTO Product1 (Name, Price, Qty, SupplierId) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQty());
            stmt.setInt(4, product.getSupplierId());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println(" Error inserting product: " + e.getMessage());
            return false;
        }
    }

    // ===== 2. Get All Products =====
    public List<Product1> getAllProducts() {
        List<Product1> products = new ArrayList<>();
        String sql = "SELECT * FROM Product1";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product1 product = new Product1(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getInt("Qty"),
                        rs.getInt("SupplierId")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching products: " + e.getMessage());
        }

        return products;
    }

    // ===== 3. Update Product =====
    public boolean updateProduct(Product1 product) {
        String sql = "UPDATE Product1 SET Name = ?, Price = ?, Qty = ?, SupplierId = ? WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQty());
            stmt.setInt(4, product.getSupplierId());
            stmt.setInt(5, product.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println(" Error updating product: " + e.getMessage());
            return false;
        }
    }

    // ===== 4. Delete Product =====
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM Product1 WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println(" Error deleting product: " + e.getMessage());
            return false;
        }
    }

    // ===== 5. Find Product By ID =====
    public Product1 getProductById(int productId) {
        String sql = "SELECT * FROM Product1 WHERE Id = ?";
        Product1 product = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    product = new Product1(
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getDouble("Price"),
                            rs.getInt("Qty"),
                            rs.getInt("SupplierId")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching product by ID: " + e.getMessage());
        }

        return product;
    }
}
