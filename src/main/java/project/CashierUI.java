package project;

import javax.swing.*;
import java.awt.*;

public class CashierUI extends JFrame {
    public static void showUI(Employee1 emp) {
        JFrame frame = new JFrame("Cashier Panel - " + emp.getUsername());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(2, 1, 10, 10));

        JButton customerBtn = new JButton("Manage Customers");
        JButton saleBtn = new JButton("Process Sales");

        frame.add(customerBtn);
        frame.add(saleBtn);

        customerBtn.addActionListener(e -> CustomerUI.showUI());
        saleBtn.addActionListener(e -> SaleUI.showUI());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public CashierUI(Employee1 employee) {
        setTitle("Cashier Panel - " + employee.getUsername());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton btnCustomers = new JButton("Manage Customers");
        JButton btnSales = new JButton("Process Sales");

        panel.add(btnCustomers);
        panel.add(btnSales);

        add(panel);
    }
}
