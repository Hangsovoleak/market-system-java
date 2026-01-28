package project;

import project.DBConnection;
import project.Supplier1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    // ===== 1. Add Supplier =====
    public boolean addSupplier(Supplier1 supplier) {
        String sql = "INSERT INTO Supplier1 (Name, Phone) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getPhone());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println(" Error adding supplier: " + e.getMessage());
            return false;
        }
    }

    // ===== 2. Get All Suppliers =====
    public List<Supplier1> getAllSuppliers() {
        List<Supplier1> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier1";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Supplier1 supplier = new Supplier1(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Phone")
                );
                suppliers.add(supplier);
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching suppliers: " + e.getMessage());
        }

        return suppliers;
    }

    // ===== 3. Update Supplier =====
    public boolean updateSupplier(Supplier1 supplier) {
        String sql = "UPDATE Supplier1 SET Name = ?, Phone = ? WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getPhone());
            stmt.setInt(3, supplier.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println(" Error updating supplier: " + e.getMessage());
            return false;
        }
    }

    // ===== 4. Delete Supplier =====
    public boolean deleteSupplier(int supplierId) {
        String sql = "DELETE FROM Supplier1 WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplierId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println(" Error deleting supplier: " + e.getMessage());
            return false;
        }
    }

    // ===== 5. Find Supplier By ID =====
    public Supplier1 getSupplierById(int supplierId) {
        String sql = "SELECT * FROM Supplier1 WHERE Id = ?";
        Supplier1 supplier = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplierId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    supplier = new Supplier1(
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getString("Phone")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching supplier by ID: " + e.getMessage());
        }

        return supplier;
    }
}
