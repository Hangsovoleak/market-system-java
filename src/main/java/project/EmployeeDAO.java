package project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // ===== 1. Add Employee =====
    public boolean addEmployee(Employee1 employee) {
        String sql = "INSERT INTO Employee1 (UserName, Role) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getUsername());
            stmt.setString(2, employee.getRole());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println(" Error adding employee: " + e.getMessage());
            return false;
        }
    }

    // ===== 2. Get All Employees =====
    public List<Employee1> getAllEmployees() {
        List<Employee1> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee1";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee1 employee = new Employee1(
                        rs.getInt("Id"),
                        rs.getString("UserName"),
                        rs.getString("Role")
                );
                employees.add(employee);
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching employees: " + e.getMessage());
        }

        return employees;
    }

    // ===== 3. Update Employee =====
    public boolean updateEmployee(Employee1 employee) {
        String sql = "UPDATE Employee1 SET UserName = ?, Role = ? WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getUsername());
            stmt.setString(2, employee.getRole());
            stmt.setInt(3, employee.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println(" Error updating employee: " + e.getMessage());
            return false;
        }
    }

    // ===== 4. Delete Employee =====
    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM Employee1 WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println(" Error deleting employee: " + e.getMessage());
            return false;
        }
    }

    // ===== 5. Find Employee By ID =====
    public Employee1 getEmployeeById(int employeeId) {
        String sql = "SELECT * FROM Employee1 WHERE Id = ?";
        Employee1 employee = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee1(
                            rs.getInt("Id"),
                            rs.getString("UserName"),
                            rs.getString("Role")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println(" Error fetching employee by ID: " + e.getMessage());
        }

        return employee;
    }

    // ===== 6. Login Method =====
    public Employee1 login(String username) {
        String sql = "SELECT * FROM Employee1 WHERE UserName = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Employee1(
                        rs.getInt("Id"),
                        rs.getString("UserName"),
                        rs.getString("Role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
