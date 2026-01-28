package project;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::showLoginScreen);
    }

    private static void showLoginScreen() {
        JFrame frame = new JFrame("Supermarket System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLayout(new GridBagLayout());

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JButton loginButton = new JButton("Login");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0; gbc.gridy = 0; frame.add(userLabel, gbc);
        gbc.gridx = 1; frame.add(userField, gbc);
        gbc.gridx = 1; gbc.gridy = 1; frame.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter username!");
                return;
            }

            EmployeeDAO dao = new EmployeeDAO();
            Employee1 emp = dao.login(username);

            if (emp == null) {
                JOptionPane.showMessageDialog(frame, " Login failed! User not found.");
            } else {
                JOptionPane.showMessageDialog(frame, " Welcome, " + emp.getUsername() + " (" + emp.getRole() + ")");
                frame.dispose();

                if (emp.getRole().equals("Manager")) {
                    ManagerUI.showUI(emp);
                } else if (emp.getRole().equals("Cashier")) {
                    CashierUI.showUI(emp);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
