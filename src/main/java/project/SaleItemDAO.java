package project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleItemDAO {

    // ===== 1. Add Sale Item =====
    public boolean addSaleItem(SaleItem1 item) {
        String sql = "INSERT INTO SaleItem1 (SaleId, ProductId, Qty, Price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, item.getSaleId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQty());
            stmt.setDouble(4, item.getPrice());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        item.setSaleItemId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println(" Error adding sale item: " + e.getMessage());
        }

        return false;
    }

    // ===== 2. Get Sale Items By SaleId =====
    public List<SaleItem1> getSaleItemsBySaleId(int saleId) {
        List<SaleItem1> items = new ArrayList<>();
        String sql = "SELECT * FROM SaleItem1 WHERE SaleId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, saleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SaleItem1 item = new SaleItem1(
                            rs.getInt("SaleItemId"),
                            rs.getInt("SaleId"),
                            rs.getInt("ProductId"),
                            rs.getInt("Qty"),
                            rs.getDouble("Price")
                    );
                    items.add(item);
                }
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching sale items: " + e.getMessage());
        }

        return items;
    }
}
