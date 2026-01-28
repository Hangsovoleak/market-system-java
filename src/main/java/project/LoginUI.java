package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField txtUsername;
    private JButton btnLogin;

    private EmployeeDAO employeeDAO;

    public LoginUI() {
        employeeDAO = new EmployeeDAO();

        setTitle("SuperMarket Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        // Username Field
        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        add(txtUsername, gbc);

        // Login Button
        btnLogin = new JButton("Login");
        gbc.gridy = 1;
        gbc.gridx = 1;
        add(btnLogin, gbc);

        // Button action
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });
    }

    private void loginAction() {
        String username = txtUsername.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify user
        Employee1 employee = employeeDAO.login(username);
        if (employee != null) {
            JOptionPane.showMessageDialog(this, "Welcome, " + employee.getUsername() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Open different UI depending on role
            if (employee.getRole().equalsIgnoreCase("Manager")) {
                new ManagerUI(employee).setVisible(true);
            } else if (employee.getRole().equalsIgnoreCase("Cashier")) {
                new CashierUI(employee).setVisible(true);
            }

            // Close login window
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
