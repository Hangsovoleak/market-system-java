package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SupplierUI {
    private static SupplierDAO supplierDAO = new SupplierDAO();
    private static JTable table;
    private static DefaultTableModel model;

    public static void showUI() {
        UiTheme.apply();
        JFrame frame = new JFrame("Supplier Management");
        frame.setSize(700, 400);
        JPanel root = UiTheme.createRootPanel();
        frame.setContentPane(root);

        // ===== Table =====
        model = new DefaultTableModel(new String[]{"ID", "Name", "Phone"}, 0);
        table = new JTable(model);
        UiTheme.styleTable(table);
        refreshTable();

        root.add(UiTheme.createTitlePanel("Suppliers"), BorderLayout.NORTH);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== Buttons =====
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        JPanel buttonRow = UiTheme.createButtonRow(addBtn, updateBtn, deleteBtn);
        root.add(buttonRow, BorderLayout.SOUTH);

        // ===== Button Actions =====
        addBtn.addActionListener(e -> addSupplier());
        updateBtn.addActionListener(e -> updateSupplier());
        deleteBtn.addActionListener(e -> deleteSupplier());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ===== Refresh Table =====
    private static void refreshTable() {
        model.setRowCount(0);
        List<Supplier1> suppliers = supplierDAO.getAllSuppliers();
        for (Supplier1 s : suppliers) {
            model.addRow(new Object[]{s.getId(), s.getName(), s.getPhone()});
        }
    }

    // ===== Add Supplier =====
    private static void addSupplier() {
        String name = JOptionPane.showInputDialog("Supplier Name:");
        String phone = JOptionPane.showInputDialog("Phone:");

        Supplier1 s = new Supplier1(0, name, phone);
        if (supplierDAO.addSupplier(s)) {
            JOptionPane.showMessageDialog(null, "✅ Supplier added successfully!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, " Failed to add supplier!");
        }
    }

    // ===== Update Supplier =====
    private static void updateSupplier() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, " Please select a supplier to update!");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String name = JOptionPane.showInputDialog("New Name:", table.getValueAt(selectedRow, 1));
        String phone = JOptionPane.showInputDialog("New Phone:", table.getValueAt(selectedRow, 2));

        Supplier1 s = new Supplier1(id, name, phone);
        if (supplierDAO.updateSupplier(s)) {
            JOptionPane.showMessageDialog(null, " Supplier updated successfully!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, " Failed to update supplier!");
        }
    }

    // ===== Delete Supplier =====
    private static void deleteSupplier() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, " Please select a supplier to delete!");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        if (JOptionPane.showConfirmDialog(null, "Are you sure to delete this supplier?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (supplierDAO.deleteSupplier(id)) {
                JOptionPane.showMessageDialog(null, " Supplier deleted successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, " Failed to delete supplier!");
            }
        }
    }
}
