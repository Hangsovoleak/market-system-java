package project;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    // ===== 1. Add Sale =====
    public int addSale(Sale1 sale) {
        String sql = "INSERT INTO Sale1 (EmployeeId, CustomerId, TotalAmount, PaymentType, SaleDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, sale.getEmployeeId());
            stmt.setInt(2, sale.getCustomerId());
            stmt.setDouble(3, sale.getTotalAmount());
            stmt.setString(4, sale.getPaymentType());
            stmt.setTimestamp(5, new Timestamp(sale.getSaleDate().getTime()));

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        sale.setSaleId(generatedKeys.getInt(1));
                        return sale.getSaleId();
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println(" Error adding sale: " + e.getMessage());
        }

        return -1; // Failed
    }

    // ===== 2. Get All Sales =====
    public List<Sale1> getAllSales() {
        List<Sale1> sales = new ArrayList<>();
        String sql = "SELECT * FROM Sale1";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sale1 sale = new Sale1(
                        rs.getInt("SaleId"),
                        rs.getInt("EmployeeId"),
                        rs.getInt("CustomerId"),
                        rs.getDouble("TotalAmount"),
                        rs.getString("PaymentType"),
                        rs.getTimestamp("SaleDate")
                );
                sales.add(sale);
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching sales: " + e.getMessage());
        }

        return sales;
    }

    // ===== 3. Find Sale By ID =====
    public Sale1 getSaleById(int saleId) {
        String sql = "SELECT * FROM Sale1 WHERE SaleId = ?";
        Sale1 sale = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, saleId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    sale = new Sale1(
                            rs.getInt("SaleId"),
                            rs.getInt("EmployeeId"),
                            rs.getInt("CustomerId"),
                            rs.getDouble("TotalAmount"),
                            rs.getString("PaymentType"),
                            rs.getTimestamp("SaleDate")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching sale by ID: " + e.getMessage());
        }

        return sale;
    }
}
