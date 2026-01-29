package project;

import javax.swing.*;
import java.awt.*;

public class ManagerUI extends JFrame{
    public static void showUI(Employee1 emp) {
        new ManagerUI(emp).setVisible(true);
    }

    public ManagerUI(Employee1 employee) {
        UiTheme.apply();
        setTitle("Manager Panel - " + employee.getUsername());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = UiTheme.createRootPanel();
        setContentPane(root);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setOpaque(false);

        JButton btnProducts = new JButton("Manage Products");
        JButton btnSuppliers = new JButton("Manage Suppliers");
        JButton btnEmployees = new JButton("Manage Employees");

        panel.add(btnProducts);
        panel.add(btnSuppliers);
        panel.add(btnEmployees);

        root.add(UiTheme.createTitlePanel("Manager Menu"), BorderLayout.NORTH);
        root.add(panel, BorderLayout.CENTER);

        btnProducts.addActionListener(e -> ProductUI.showUI());
        btnSuppliers.addActionListener(e -> SupplierUI.showUI());
        btnEmployees.addActionListener(e -> EmployeeUI.showUI());
    }
}
