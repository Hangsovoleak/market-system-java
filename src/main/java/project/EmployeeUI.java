package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeUI {
    private static EmployeeDAO employeeDAO = new EmployeeDAO();
    private static JTable table;
    private static DefaultTableModel model;

    public static void showUI() {
        UiTheme.apply();
        JFrame frame = new JFrame("Employee Management");
        frame.setSize(700, 400);
        JPanel root = UiTheme.createRootPanel();
        frame.setContentPane(root);

        // ===== Table =====
        model = new DefaultTableModel(new String[]{"ID", "Username", "Role"}, 0);
        table = new JTable(model);
        UiTheme.styleTable(table);
        refreshTable();

        root.add(UiTheme.createTitlePanel("Employees"), BorderLayout.NORTH);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== Buttons =====
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        JPanel buttonRow = UiTheme.createButtonRow(addBtn, updateBtn, deleteBtn);
        root.add(buttonRow, BorderLayout.SOUTH);

        // ===== Button Actions =====
        addBtn.addActionListener(e -> addEmployee());
        updateBtn.addActionListener(e -> updateEmployee());
        deleteBtn.addActionListener(e -> deleteEmployee());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ===== Refresh Table =====
    private static void refreshTable() {
        model.setRowCount(0);
        List<Employee1> employees = employeeDAO.getAllEmployees();
        for (Employee1 e : employees) {
            model.addRow(new Object[]{e.getId(), e.getUsername(), e.getRole()});
        }
    }

    // ===== Add Employee =====
    private static void addEmployee() {
        String username = JOptionPane.showInputDialog("Username:");
        String role = JOptionPane.showInputDialog("Role (Manager/Cashier):");

        if (!role.equals("Manager") && !role.equals("Cashier")) {
            JOptionPane.showMessageDialog(null, " Invalid role!");
            return;
        }

        Employee1 e = new Employee1(0, username, role);
        if (employeeDAO.addEmployee(e)) {
            JOptionPane.showMessageDialog(null, " Employee added successfully!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, " Failed to add employee!");
        }
    }

    // ===== Update Employee =====
    private static void updateEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, " Please select an employee to update!");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String username = JOptionPane.showInputDialog("New Username:", table.getValueAt(selectedRow, 1));
        String role = JOptionPane.showInputDialog("New Role (Manager/Cashier):", table.getValueAt(selectedRow, 2));

        if (!role.equals("Manager") && !role.equals("Cashier")) {
            JOptionPane.showMessageDialog(null, " Invalid role!");
            return;
        }

        Employee1 e = new Employee1(id, username, role);
        if (employeeDAO.updateEmployee(e)) {
            JOptionPane.showMessageDialog(null, " Employee updated successfully!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, " Failed to update employee!");
        }
    }

    // ===== Delete Employee =====
    private static void deleteEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, " Please select an employee to delete!");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        if (JOptionPane.showConfirmDialog(null, "Are you sure to delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (employeeDAO.deleteEmployee(id)) {
                JOptionPane.showMessageDialog(null, " Employee deleted successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, " Failed to delete employee!");
            }
        }
    }
}
