
package showroom.auth.view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.Border;
import showroom.MainPage;
import showroom.admin.view.AdminDashboard;
import showroom.auth.controller.AuthController;
import showroom.auth.model.User;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private AuthController authController;
    private final Color accentColor = new Color(255, 103, 31);
    private final Color darkBgColor = new Color(24, 24, 24);
    private final Color fieldBgColor = new Color(40, 40, 40);
    private final Color placeholderColor = Color.GRAY;
    private final Color textColor = Color.WHITE;

    public LoginView() {
        authController = new AuthController();
        initializeUI();
        setupEventListeners();
    }

    private void addPlaceholderStyle(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(placeholderColor);

        if (field instanceof JPasswordField) {
            ((JPasswordField) field).setEchoChar((char) 0);
        }

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getForeground().equals(placeholderColor)) {
                    field.setText("");
                    field.setForeground(textColor);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('•');
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String currentText = (field instanceof JPasswordField)
                        ? new String(((JPasswordField) field).getPassword())
                        : field.getText();
                if (currentText.isEmpty()) {
                    field.setForeground(placeholderColor);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    private void setupEventListeners() {
        // Hover effect for button
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(255, 123, 51));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(accentColor);
            }
        });

        // Enter key listener for password field to trigger login
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!emailField.getText().isEmpty() && passwordField.getPassword().length > 0) {
                        loginButton.doClick();
                    }
                }
            }
        });

        // Focus border effects
        Border defaultBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        Border focusedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        emailField.setBorder(defaultBorder);
        passwordField.setBorder(defaultBorder);

        FocusListener borderFocusListener = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ((JComponent) e.getComponent()).setBorder(focusedBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                ((JComponent) e.getComponent()).setBorder(defaultBorder);
            }
        };

        emailField.addFocusListener(borderFocusListener);
        passwordField.addFocusListener(borderFocusListener);
    }

    private void initializeUI() {
        setTitle("Login - Bike Showroom Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);

        JPanel loginContainerPanel = new JPanel(new GridBagLayout());
        loginContainerPanel.setBackground(darkBgColor);

        JPanel formContentPanel = new JPanel(new GridBagLayout());
        formContentPanel.setOpaque(false);
        formContentPanel.setPreferredSize(new Dimension(400, 500));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel welcomeLabel = new JLabel("Welcome Back");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(accentColor);
        formContentPanel.add(welcomeLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 30, 0);
        JLabel subtitleLabel = new JLabel("Sign in to your showroom management account");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        formContentPanel.add(subtitleLabel, gbc);
        gbc.insets = new Insets(5, 0, 5, 0);

        // Email field
        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setForeground(textColor);
        formContentPanel.add(emailLabel, gbc);

        gbc.gridy++;
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailField.setBackground(fieldBgColor);
        emailField.setForeground(textColor);
        emailField.setCaretColor(textColor);
        addPlaceholderStyle(emailField, "Enter your email");
        formContentPanel.add(emailField, gbc);

        // Password field
        gbc.gridy++;
        gbc.insets = new Insets(15, 0, 5, 0);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(textColor);
        formContentPanel.add(passwordLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBackground(fieldBgColor);
        passwordField.setForeground(textColor);
        passwordField.setCaretColor(textColor);
        addPlaceholderStyle(passwordField, "Enter your password");
        formContentPanel.add(passwordField, gbc);

        // Options panel
        gbc.gridy++;
        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setOpaque(false);

        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rememberMe.setForeground(textColor);
        rememberMe.setOpaque(false);
        rememberMe.setFocusPainted(false);

        JButton forgotPassword = new JButton("Forgot password?");
        forgotPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        forgotPassword.setForeground(accentColor);
        forgotPassword.setBorderPainted(false);
        forgotPassword.setContentAreaFilled(false);
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));

        optionsPanel.add(rememberMe, BorderLayout.WEST);
        optionsPanel.add(forgotPassword, BorderLayout.EAST);
        formContentPanel.add(optionsPanel, gbc);

        // Sign in button
        gbc.gridy++;
        gbc.insets = new Insets(25, 0, 5, 0);
        loginButton = new JButton("Sign in");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(accentColor);
        loginButton.setForeground(textColor);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this::handleLogin);
        formContentPanel.add(loginButton, gbc);

        // Sign up link
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 5, 0);
        JPanel signUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        signUpPanel.setOpaque(false);

        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        noAccountLabel.setForeground(Color.LIGHT_GRAY);

        JButton signUpLink = new JButton("Sign up");
        signUpLink.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signUpLink.setForeground(accentColor);
        signUpLink.setBorderPainted(false);
        signUpLink.setContentAreaFilled(false);
        signUpLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLink.addActionListener(e -> new RegistrationView().setVisible(true));

        signUpPanel.add(noAccountLabel);
        signUpPanel.add(signUpLink);
        formContentPanel.add(signUpPanel, gbc);

        loginContainerPanel.add(formContentPanel);

        // Right Panel: Image
        JLabel imagePanel = new JLabel();
        imagePanel.setLayout(new BorderLayout());
        try {
            String imagePath = "showroom/assets/image.png";
            ImageIcon imageIcon = new ImageIcon(new File(imagePath).getAbsolutePath());
            if (imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                imagePanel.setIcon(imageIcon);
                imagePanel.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        Image scaledImg = imageIcon.getImage()
                            .getScaledInstance(imagePanel.getWidth(), imagePanel.getHeight(), Image.SCALE_SMOOTH);
                        imagePanel.setIcon(new ImageIcon(scaledImg));
                    }
                });
            } else throw new Exception("Image loading failed.");
        } catch (Exception e) {
            System.err.println("Warning: Could not load image - " + e.getMessage());
            imagePanel.setBackground(Color.DARK_GRAY);
            imagePanel.setOpaque(true);
        }

        JPanel brandingPanel = new JPanel();
        brandingPanel.setLayout(new BoxLayout(brandingPanel, BoxLayout.Y_AXIS));
        brandingPanel.setOpaque(false);
        brandingPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 0));

        JLabel brandLabel = new JLabel("Premium Bike Showroom");
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        brandLabel.setForeground(textColor);

        JLabel brandTagline = new JLabel("Manage your inventory with style");
        brandTagline.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        brandTagline.setForeground(Color.LIGHT_GRAY);

        brandingPanel.add(brandLabel);
        brandingPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        brandingPanel.add(brandTagline);

        imagePanel.add(brandingPanel, BorderLayout.SOUTH);

        splitPane.setLeftComponent(loginContainerPanel);
        splitPane.setRightComponent(imagePanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(0.4);
                removeComponentListener(this);
            }
        });

        add(splitPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void handleLogin(ActionEvent e) {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || email.equals("Enter your email") || password.isEmpty() || password.equals("Enter your password")) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!authController.validateEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ Admin credentials check
        String adminEmail = "admin@showroom.com";
        String adminPassword = "admin123";

        if (email.equals(adminEmail) && password.equals(adminPassword)) {
            JOptionPane.showMessageDialog(this, "Welcome Admin!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(() -> new AdminDashboard("Admin").setVisible(true)); // ✅ FIXED HERE
            return;
        }

        // ✅ Normal user login
        User user = authController.loginUser(email, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getName() + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(() -> new MainPage(user.getName()).setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(LoginView::new);
    }
}
