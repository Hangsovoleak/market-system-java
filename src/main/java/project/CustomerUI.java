package project;

import project.Customer1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerUI {
    private static CustomerDAO customerDAO = new CustomerDAO();
    private static JTable table;
    private static DefaultTableModel model;

    public static void showUI() {
        UiTheme.apply();
        JFrame frame = new JFrame("Customer Management");
        frame.setSize(600, 400);
        JPanel root = UiTheme.createRootPanel();
        frame.setContentPane(root);

        // ===== Table =====
        model = new DefaultTableModel(new String[]{"ID"}, 0);
        table = new JTable(model);
        UiTheme.styleTable(table);
        refreshTable();

        root.add(UiTheme.createTitlePanel("Customers"), BorderLayout.NORTH);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== Buttons =====
        JButton addBtn = new JButton("Add Customer");
        JButton deleteBtn = new JButton("Delete Customer");

        JPanel buttonRow = UiTheme.createButtonRow(addBtn, deleteBtn);
        root.add(buttonRow, BorderLayout.SOUTH);

        // ===== Button Actions =====
        addBtn.addActionListener(e -> addCustomer());
        deleteBtn.addActionListener(e -> deleteCustomer());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ===== Refresh Table =====
    private static void refreshTable() {
        model.setRowCount(0);
        List<Customer1> customers = customerDAO.getAllCustomers();
        for (Customer1 c : customers) {
            model.addRow(new Object[]{c.getId()});
        }
    }

    // ===== Add Customer =====
    private static void addCustomer() {
        Customer1 c = new Customer1();
        if (customerDAO.addCustomer(c)) {
            JOptionPane.showMessageDialog(null, " Customer added successfully! ID = " + c.getId());
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, " Failed to add customer!");
        }
    }

    // ===== Delete Customer =====
    private static void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, " Please select a customer to delete!");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        if (JOptionPane.showConfirmDialog(null, "Are you sure to delete this customer?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (customerDAO.deleteCustomer(id)) {
                JOptionPane.showMessageDialog(null, " Customer deleted successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, " Failed to delete customer!");
            }
        }
    }
}
