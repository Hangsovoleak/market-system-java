package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductUI {
    private static ProductDAO productDAO = new ProductDAO();
    private static JTable table;
    private static DefaultTableModel model;

    public static void showUI() {
        JFrame frame = new JFrame("Product Management");
        frame.setSize(700, 400);
        frame.setLayout(new BorderLayout());

        // ===== Table =====
        model = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Qty", "SupplierId"}, 0);
        table = new JTable(model);
        refreshTable();

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);

        frame.add(panel, BorderLayout.SOUTH);

        // ===== Button Actions =====
        addBtn.addActionListener(e -> addProduct());
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ===== Refresh Table =====
    private static void refreshTable() {
        model.setRowCount(0);
        List<Product1> products = productDAO.getAllProducts();
        for (Product1 p : products) {
            model.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getQty(), p.getSupplierId()});
        }
    }

    // ===== Add Product =====
    private static void addProduct() {
        String name = JOptionPane.showInputDialog("Product Name:");
        String priceStr = JOptionPane.showInputDialog("Price:");
        String qtyStr = JOptionPane.showInputDialog("Quantity:");
        String supplierStr = JOptionPane.showInputDialog("Supplier ID:");

        try {
            double price = Double.parseDouble(priceStr);
            int qty = Integer.parseInt(qtyStr);
            int supplierId = Integer.parseInt(supplierStr);

            Product1 p = new Product1(0, name, price, qty, supplierId);
            if (productDAO.addProduct(p)) {
                JOptionPane.showMessageDialog(null, " Product added successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, " Failed to add product!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, " Invalid input!");
        }
    }

    // ===== Update Product =====
    private static void updateProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, " Please select a product to update!");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String name = JOptionPane.showInputDialog("New Name:", table.getValueAt(selectedRow, 1));
        String priceStr = JOptionPane.showInputDialog("New Price:", table.getValueAt(selectedRow, 2));
        String qtyStr = JOptionPane.showInputDialog("New Quantity:", table.getValueAt(selectedRow, 3));
        String supplierStr = JOptionPane.showInputDialog("New Supplier ID:", table.getValueAt(selectedRow, 4));

        try {
            double price = Double.parseDouble(priceStr);
            int qty = Integer.parseInt(qtyStr);
            int supplierId = Integer.parseInt(supplierStr);

            Product1 p = new Product1(id, name, price, qty, supplierId);
            if (productDAO.updateProduct(p)) {
                JOptionPane.showMessageDialog(null, " Product updated successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, " Failed to update product!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, " Invalid input!");
        }
    }

    // ===== Delete Product =====
    private static void deleteProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, " Please select a product to delete!");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        if (JOptionPane.showConfirmDialog(null, "Are you sure to delete this product?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (productDAO.deleteProduct(id)) {
                JOptionPane.showMessageDialog(null, " Product deleted successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, " Failed to delete product!");
            }
        }
    }
}
