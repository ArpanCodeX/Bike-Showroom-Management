package showroom.admin.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class AdminDashboard extends JFrame {
    private final Color PRIMARY_COLOR = new Color(255, 103, 31); // Orange
    private final Color SECONDARY_COLOR = new Color(26, 26, 26); // Dark Black
    private final Color ACCENT_COLOR = new Color(255, 140, 60); // Light Orange
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BACKGROUND_COLOR = new Color(20, 20, 20); // Very Dark
    
    private JPanel sidebar;
    private JPanel mainPanel;
    private JButton logoutButton;

    public AdminDashboard(String adminName) {
        setTitle("Admin Dashboard - Bike Showroom Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Create Sidebar
        createSidebar(adminName);
        
        // Create Main Panel
        createMainPanel(adminName);

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void createSidebar(String adminName) {
        sidebar = new JPanel();
        sidebar.setBackground(SECONDARY_COLOR);
        sidebar.setPreferredSize(new Dimension(280, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, PRIMARY_COLOR));

        // Logo/Title Section
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(SECONDARY_COLOR);
        logoPanel.setMaximumSize(new Dimension(280, 100));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel("🏍 ADMIN");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoLabel.setForeground(PRIMARY_COLOR);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome, " + adminName);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        welcomeLabel.setForeground(new Color(200, 200, 200));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        logoPanel.add(logoLabel);
        logoPanel.add(Box.createVerticalStrut(8));
        logoPanel.add(welcomeLabel);

        sidebar.add(logoPanel);
        sidebar.add(createSeparator());

        // Navigation Buttons
        JPanel navPanel = new JPanel();
        navPanel.setBackground(SECONDARY_COLOR);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setMaximumSize(new Dimension(280, Integer.MAX_VALUE));

        navPanel.add(createSidebarButton("🏍 Manage Bikes", () -> new ManageBikesView()));
        navPanel.add(Box.createVerticalStrut(8));
        navPanel.add(createSidebarButton("👥 Manage Customers", () -> 
            JOptionPane.showMessageDialog(null, "Coming Soon!")));
        navPanel.add(Box.createVerticalStrut(8));
        navPanel.add(createSidebarButton("👨‍💼 Manage Staff", () -> new StaffManagementView().setVisible(true)));
        navPanel.add(Box.createVerticalStrut(8));
        navPanel.add(createSidebarButton("📦 Stock Management", () -> new StockManagementView().setVisible(true)));
        navPanel.add(Box.createVerticalStrut(8));
        navPanel.add(createSidebarButton("📊 Reports", () -> 
            JOptionPane.showMessageDialog(null, "Coming Soon!")));
        navPanel.add(Box.createVerticalStrut(8));
        navPanel.add(createSidebarButton("⚙️ Settings", () -> 
            JOptionPane.showMessageDialog(null, "Coming Soon!")));

        sidebar.add(navPanel);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(createSeparator());

        // Logout Button
        logoutButton = new JButton("🚪 Logout");
        logoutButton.setMaximumSize(new Dimension(250, 45));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setBackground(PRIMARY_COLOR);
        logoutButton.setForeground(TEXT_COLOR);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutButton.setBorderPainted(false);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.setMargin(new Insets(10, 10, 10, 10));

        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(ACCENT_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(PRIMARY_COLOR);
            }
        });

        logoutButton.addActionListener(e -> handleLogout());

        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(SECONDARY_COLOR);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 20, 15));
        logoutPanel.add(logoutButton);

        sidebar.add(logoutPanel);
    }

    private void createMainPanel(String adminName) {
        mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 80));

        JLabel headerTitle = new JLabel("Dashboard");
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headerTitle.setForeground(PRIMARY_COLOR);
        headerTitle.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        headerPanel.add(headerTitle, BorderLayout.WEST);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Welcome Card
        JPanel welcomeCard = createWelcomeCard(adminName);

        contentPanel.add(welcomeCard, BorderLayout.NORTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createWelcomeCard(String adminName) {
        JPanel card = new JPanel();
        card.setBackground(BACKGROUND_COLOR);
        card.setBorder(BorderFactory.createEmptyBorder(60, 30, 60, 30));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        card.setPreferredSize(new Dimension(0, 250));

        JLabel welcomeText = new JLabel("Welcome Admin");
        welcomeText.setFont(new Font("Segoe UI", Font.BOLD, 64));
        welcomeText.setForeground(PRIMARY_COLOR);
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionText = new JLabel("Here you can manage all");
        descriptionText.setFont(new Font("Segoe UI", Font.BOLD, 48));
        descriptionText.setForeground(TEXT_COLOR);
        descriptionText.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(welcomeText);
        card.add(Box.createVerticalStrut(20));
        card.add(descriptionText);

        return card;
    }

    private JButton createSidebarButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(250, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBackground(SECONDARY_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
                button.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
                button.setForeground(TEXT_COLOR);
            }
        });

        button.addActionListener(e -> action.run());

        return button;
    }

    private JPanel createSeparator() {
        JPanel sep = new JPanel();
        sep.setBackground(SECONDARY_COLOR);
        sep.setMaximumSize(new Dimension(280, 1));
        sep.setPreferredSize(new Dimension(280, 1));
        JPanel line = new JPanel();
        line.setBackground(PRIMARY_COLOR);
        line.setPreferredSize(new Dimension(250, 1));
        sep.add(line);
        return sep;
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            JOptionPane.showMessageDialog(null, "Logged out successfully!", "Logout", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard("Admin"));
    }
}
