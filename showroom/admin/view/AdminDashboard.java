package showroom.admin.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminDashboard extends JFrame {
    private final Color accentColor = new Color(0, 120, 215); // Blue accent
    private JPanel sidebar;
    private JPanel mainPanel;
    private JButton logoutButton;

    public AdminDashboard(String adminName) {
        setTitle("Admin Dashboard - Bike Showroom Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = new JPanel();
        sidebar.setBackground(new Color(30, 30, 30));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Admin Panel");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        sidebar.add(title);

        // Sidebar buttons
        sidebar.add(createSidebarButton("🏍 Manage Bikes", () -> {
            new ManageBikesView();
        }));

        sidebar.add(createSidebarButton("👥 Manage Customers", () -> {
            JOptionPane.showMessageDialog(null, "Opening Manage Customers...");
            // Replace with your actual ManageCustomersView if available
        }));

        sidebar.add(createSidebarButton("👨‍💼 Manage Staff", () -> {
            new StaffManagementView().setVisible(true); // Opens your Staff Management window
        }));

        sidebar.add(createSidebarButton("📦 Stock Management", () -> {
            new StockManagementView().setVisible(true); // Opens your Stock Management window (replace with your class)
        }));

        sidebar.add(createSidebarButton("📊 Reports", () -> {
            JOptionPane.showMessageDialog(null, "Opening Reports...");
            // Replace with actual Reports view
        }));

        sidebar.add(createSidebarButton("⚙️ Settings", () -> {
            JOptionPane.showMessageDialog(null, "Opening Settings...");
            // Replace with actual Settings view
        }));

        sidebar.add(Box.createVerticalGlue());

        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setBackground(accentColor);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBorderPainted(false);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.setMaximumSize(new Dimension(150, 40));

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                JOptionPane.showMessageDialog(null, "Logged out successfully!");
                // Redirect to LoginView if needed
            }
        });

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(logoutButton);

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + adminName + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(accentColor);
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Sidebar button with Runnable action
    private JButton createSidebarButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(accentColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(45, 45, 45));
            }
        });

        button.addActionListener(e -> action.run());

        return button;
    }

    // For testing the dashboard directly
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard("Admin"));
    }
}
