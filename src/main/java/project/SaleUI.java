package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaleUI {
    private static SaleDAO saleDAO = new SaleDAO();
    private static SaleItemDAO saleItemDAO = new SaleItemDAO();
    private static ProductDAO productDAO = new ProductDAO();
    private static CustomerDAO customerDAO = new CustomerDAO();

    private static JTable itemTable;
    private static DefaultTableModel itemModel;

    private static List<SaleItem1> saleItems = new ArrayList<>();
    private static int selectedCustomerId = -1;

    public static void showUI() {
        JFrame frame = new JFrame("Process Sale");
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // ===== Top Panel: Select Customer =====
        JPanel topPanel = new JPanel();
        JComboBox<String> customerCombo = new JComboBox<>();
        for (Customer1 c : customerDAO.getAllCustomers()) {
            customerCombo.addItem(c.getId() + "");
        }
        customerCombo.addActionListener(e -> selectedCustomerId = Integer.parseInt((String) customerCombo.getSelectedItem()));
        topPanel.add(new JLabel("Select Customer ID:"));
        topPanel.add(customerCombo);

        frame.add(topPanel, BorderLayout.NORTH);

        // ===== Center Table: Sale Items =====
        itemModel = new DefaultTableModel(new String[]{"ProductID", "Name", "Qty", "Price", "Subtotal"}, 0);
        itemTable = new JTable(itemModel);
        frame.add(new JScrollPane(itemTable), BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel panel = new JPanel();

        JButton addItemBtn = new JButton("Add Item");
        JButton removeItemBtn = new JButton("Remove Item");
        JButton finalizeBtn = new JButton("Finalize Sale");

        panel.add(addItemBtn);
        panel.add(removeItemBtn);
        panel.add(finalizeBtn);

        frame.add(panel, BorderLayout.SOUTH);

        // ===== Button Actions =====
        addItemBtn.addActionListener(e -> addItem());
        removeItemBtn.addActionListener(e -> removeItem());
        finalizeBtn.addActionListener(e -> finalizeSale());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (customerCombo.getItemCount() > 0) {
            selectedCustomerId = Integer.parseInt((String) customerCombo.getSelectedItem());
        }
    }

    // ===== Add Item to Sale =====
    private static void addItem() {
        String productIdStr = JOptionPane.showInputDialog("Product ID:");
        String qtyStr = JOptionPane.showInputDialog("Quantity:");

        try {
            int productId = Integer.parseInt(productIdStr);
            int qty = Integer.parseInt(qtyStr);

            Product1 p = productDAO.getProductById(productId);
            if (p == null) {
                JOptionPane.showMessageDialog(null, " Product not found!");
                return;
            }

            if (qty > p.getQty()) {
                JOptionPane.showMessageDialog(null, " Not enough stock!");
                return;
            }

            double subtotal = qty * p.getPrice();
            SaleItem1 item = new SaleItem1(0, 0, productId, qty, p.getPrice());
            saleItems.add(item);

            itemModel.addRow(new Object[]{productId, p.getName(), qty, p.getPrice(), subtotal});

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, " Invalid input!");
        }
    }

    // ===== Remove Item from Sale =====
    private static void removeItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, " Please select an item to remove!");
            return;
        }

        saleItems.remove(selectedRow);
        itemModel.removeRow(selectedRow);
    }

    // ===== Finalize Sale =====
    private static void finalizeSale() {
        if (selectedCustomerId == -1 || saleItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, " Select a customer and add at least one item!");
            return;
        }

        double total = saleItems.stream().mapToDouble(item -> item.getQty() * item.getPrice()).sum();
        String paymentType = JOptionPane.showInputDialog("Payment Type (Cash/Card):");

        if (!paymentType.equals("Cash") && !paymentType.equals("Card")) {
            JOptionPane.showMessageDialog(null, " Invalid payment type!");
            return;
        }

        Sale1 sale = new Sale1();
        sale.setCustomerId(selectedCustomerId);
        sale.setEmployeeId(1); // Assume logged-in cashier ID = 1
        sale.setTotalAmount(total);
        sale.setPaymentType(paymentType);
        sale.setSaleDate(new Date());

        int saleId = saleDAO.addSale(sale);
        if (saleId != -1) {
            for (SaleItem1 item : saleItems) {
                item.setSaleId(saleId);
                saleItemDAO.addSaleItem(item);

                // Update product stock
                Product1 p = productDAO.getProductById(item.getProductId());
                p.setQty(p.getQty() - item.getQty());
                productDAO.updateProduct(p);
            }

            JOptionPane.showMessageDialog(null, " Sale completed! Total = " + total);
            saleItems.clear();
            itemModel.setRowCount(0);

        } else {
            JOptionPane.showMessageDialog(null, " Failed to process sale!");
        }
    }
}
