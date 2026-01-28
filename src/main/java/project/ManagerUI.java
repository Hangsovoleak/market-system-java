package project;

import javax.swing.*;
import java.awt.*;

public class ManagerUI extends JFrame{
    public static void showUI(Employee1 emp) {
        JFrame frame = new JFrame("Manager Panel - " + emp.getUsername());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1, 10, 10));

        JButton productBtn = new JButton("Manage Products");
        JButton supplierBtn = new JButton("Manage Suppliers");
        JButton employeeBtn = new JButton("Manage Employees");

        frame.add(productBtn);
        frame.add(supplierBtn);
        frame.add(employeeBtn);

        productBtn.addActionListener(e -> ProductUI.showUI());
        supplierBtn.addActionListener(e -> SupplierUI.showUI());
        employeeBtn.addActionListener(e -> EmployeeUI.showUI());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public ManagerUI(Employee1 employee) {
        setTitle("Manager Panel - " + employee.getUsername());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnProducts = new JButton("Manage Products");
        JButton btnSuppliers = new JButton("Manage Suppliers");
        JButton btnEmployees = new JButton("Manage Employees");

        panel.add(btnProducts);
        panel.add(btnSuppliers);
        panel.add(btnEmployees);

        add(panel);
    }
}
