package project;

import javax.swing.*;
import java.awt.*;

public class MarketApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginUI loginUI = new LoginUI();
            loginUI.setVisible(true);
        });
    }

    private static void showLoginScreen() {
        JFrame frame = new JFrame("Supermarket System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        frame.setLayout(new GridBagLayout());

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginBtn = new JButton("Login");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0; gbc.gridy = 0; frame.add(userLabel, gbc);
        gbc.gridx = 1; frame.add(userField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1; frame.add(passLabel, gbc);
        gbc.gridx = 1; frame.add(passField, gbc);

        // Login Button
        gbc.gridx = 1; gbc.gridy = 2; frame.add(loginBtn, gbc);

        // Login Action
        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both username and password!");
                return;
            }

            EmployeeDAO dao = new EmployeeDAO();
            Employee1 emp = dao.login(username);

            if (emp == null) {
                JOptionPane.showMessageDialog(frame, "Login failed! Invalid username or password.");
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Welcome " + emp.getUsername() + " (" + emp.getRole() + ")");

                frame.dispose(); // Close login window

                // Redirect based on role
                if ("Manager".equalsIgnoreCase(emp.getRole())) {
                    ManagerUI.showUI(emp);
                } else if ("Cashier".equalsIgnoreCase(emp.getRole())) {
                    CashierUI.showUI(emp);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
