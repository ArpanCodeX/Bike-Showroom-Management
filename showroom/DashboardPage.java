

package showroom;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import showroom.auth.view.LoginView;
import showroom.auth.view.RegistrationView;
import showroom.chat.view.ChatbotPage;

public class DashboardPage extends JFrame {

    public DashboardPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Bike Showroom Management");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Title Section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>Bike Showroom<br>Management</div></html>");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(new Color(255, 103, 31));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel taglineLabel = new JLabel("Streamline your inventory, sales, and customer management");
        taglineLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
        taglineLabel.setForeground(Color.LIGHT_GRAY);
        taglineLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        JPanel taglinePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        taglinePanel.setBackground(Color.BLACK);
        taglinePanel.add(taglineLabel);
        titlePanel.add(taglinePanel, BorderLayout.SOUTH);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonsPanel.setBackground(Color.BLACK);

        JButton signInButton = createGradientButton("Sign In", new Color(255, 103, 31));
        JButton createAccountButton = createGradientButton("Create Account", Color.BLACK);
        JButton chatbotButton = createGradientButton("Chat Assistant", new Color(255, 103, 31));

        buttonsPanel.add(signInButton);
        buttonsPanel.add(createAccountButton);
        buttonsPanel.add(chatbotButton);

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(Color.BLACK);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        statsPanel.add(createStatsCard("500+", "Bikes Managed"));
        statsPanel.add(createStatsCard("1000+", "Happy Customers"));
        statsPanel.add(createStatsCard("24/7", "System Access"));

        // Layout
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(buttonsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(statsPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);

        // Actions
        signInButton.addActionListener(this::navigateToLogin);
        createAccountButton.addActionListener(this::navigateToRegistration);
        chatbotButton.addActionListener(this::navigateToChatbot);
    }

    private void navigateToChatbot(ActionEvent e) {
        new ChatbotPage().setVisible(true);
        dispose();
    }

    private void navigateToLogin(ActionEvent e) {
        new LoginView().setVisible(true);
        dispose();
    }

    private void navigateToRegistration(ActionEvent e) {
        new RegistrationView().setVisible(true);
        dispose();
    }

    private JButton createGradientButton(String text, Color initialFillColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, width, height, 30, 30);

                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 103, 31),
                        width, height, new Color(255, 153, 51));
                g2.setStroke(new BasicStroke(3f));
                g2.setPaint(gp);
                g2.drawRoundRect(1, 1, width - 3, height - 3, 30, 30);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setPreferredSize(new Dimension(200, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(initialFillColor);
        button.setForeground(initialFillColor.equals(Color.BLACK) ? new Color(255, 103, 31) : Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            private final Color originalBg = initialFillColor;
            private final Color originalFg = button.getForeground();

            @Override
            public void mouseEntered(MouseEvent e) {
                if (originalBg.equals(Color.BLACK)) {
                    button.setBackground(new Color(255, 103, 31, 40));
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(originalBg.darker());
                    button.setForeground(Color.WHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBg);
                button.setForeground(originalFg);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(originalBg.darker().darker());
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(originalBg);
                button.setForeground(originalFg);
            }
        });

        return button;
    }

    private JPanel createStatsCard(String number, String label) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(15, 15, 15));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        numberLabel.setForeground(new Color(255, 103, 31));
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textLabel.setForeground(Color.LIGHT_GRAY);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(numberLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(textLabel);

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // ✅ Use Nimbus for better visuals
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            new DashboardPage().setVisible(true);
        });
    }
}
