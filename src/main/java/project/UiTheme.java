package project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class UiTheme {
    private static boolean applied = false;

    private UiTheme() {}

    public static void apply() {
        if (applied) {
            return;
        }
        applied = true;

        setLookAndFeel();
        Font baseFont = new Font("Segoe UI", Font.PLAIN, 13);
        setDefaultFont(baseFont);
    }

    public static JPanel createRootPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    public static JPanel createTitlePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel label = new JLabel(title);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(label, BorderLayout.WEST);
        return panel;
    }

    public static JPanel createButtonRow(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panel.setOpaque(false);
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(24);
        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(header.getFont().deriveFont(Font.BOLD));
        }
    }

    private static void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static void setDefaultFont(Font font) {
        UIManager.getDefaults().forEach((key, value) -> {
            if (value instanceof Font) {
                UIManager.put(key, font);
            }
        });
    }
}
