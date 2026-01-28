package project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // ===== 1. Add Customer =====
    public boolean addCustomer(Customer1 customer) {
        String sql = "INSERT INTO Customer1 DEFAULT VALUES"; // Only Id is auto-generated

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println(" Error adding customer: " + e.getMessage());
        }

        return false;
    }

    // ===== 2. Get All Customers =====
    public List<Customer1> getAllCustomers() {
        List<Customer1> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer1";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer1 customer = new Customer1(rs.getInt("Id"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching customers: " + e.getMessage());
        }

        return customers;
    }

    // ===== 3. Find Customer By ID =====
    public Customer1 getCustomerById(int customerId) {
        String sql = "SELECT * FROM Customer1 WHERE Id = ?";
        Customer1 customer = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer1(rs.getInt("Id"));
                }
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching customer by ID: " + e.getMessage());
        }

        return customer;
    }

    // ===== 4. Delete Customer =====
    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM Customer1 WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println(" Error deleting customer: " + e.getMessage());
            return false;
        }
    }
}
