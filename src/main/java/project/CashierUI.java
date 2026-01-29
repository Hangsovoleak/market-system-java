package project;

import javax.swing.*;
import java.awt.*;

public class CashierUI extends JFrame {
    public static void showUI(Employee1 emp) {
        new CashierUI(emp).setVisible(true);
    }

    public CashierUI(Employee1 employee) {
        UiTheme.apply();
        setTitle("Cashier Panel - " + employee.getUsername());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = UiTheme.createRootPanel();
        setContentPane(root);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setOpaque(false);

        JButton btnCustomers = new JButton("Manage Customers");
        JButton btnSales = new JButton("Process Sales");

        panel.add(btnCustomers);
        panel.add(btnSales);

        root.add(UiTheme.createTitlePanel("Cashier Menu"), BorderLayout.NORTH);
        root.add(panel, BorderLayout.CENTER);

        btnCustomers.addActionListener(e -> CustomerUI.showUI());
        btnSales.addActionListener(e -> SaleUI.showUI());
    }
}
