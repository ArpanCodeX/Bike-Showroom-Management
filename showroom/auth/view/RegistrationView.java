package showroom.auth.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import showroom.auth.controller.AuthController;

public class RegistrationView extends JFrame {
    // --- UI Components ---
    private JTextField nameField, phoneField, emailField, cityField, districtField, stateField, pinCodeField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private AuthController authController;

    // --- UI Styling Constants ---
    private final Color accentColor = new Color(255, 103, 31);
    private final Color darkBgColor = new Color(24, 24, 24);
    private final Color fieldBgColor = new Color(40, 40, 40);
    private final Color placeholderColor = Color.GRAY;
    private final Color textColor = Color.WHITE;

    public RegistrationView() {
        authController = new AuthController();
        initializeUI();
        setupEventListeners();
    }

    /**
     * Creates a placeholder effect for a text field.
     * @param field The JTextField or JPasswordField.
     * @param placeholder The placeholder text to display.
     */
    private void addPlaceholderStyle(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(placeholderColor);

        if (field instanceof JPasswordField) {
            ((JPasswordField) field).setEchoChar((char) 0); // Show placeholder text
        }

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getForeground().equals(placeholderColor)) {
                    field.setText("");
                    field.setForeground(textColor);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('•'); // Set back to bullet points
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String currentText = (field instanceof JPasswordField) ? new String(((JPasswordField) field).getPassword()) : field.getText();
                if (currentText.isEmpty()) {
                    field.setForeground(placeholderColor);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0); // Show placeholder text again
                    }
                }
            }
        });
    }

    private void setupEventListeners() {
        // Register button hover effect
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(new Color(255, 123, 51));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(accentColor);
            }
        });
        
        // --- Setup focus border effects for text fields ---
        Border defaultBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        Border focusedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JTextField[] allFields = {nameField, phoneField, emailField, passwordField, cityField, districtField, stateField, pinCodeField};
        for(JTextField field : allFields) {
            field.setBorder(defaultBorder);
        }

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

        for(JTextField field : allFields) {
            field.addFocusListener(borderFocusListener);
        }
    }

    private void initializeUI() {
        setTitle("Register - Bike Showroom Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to not exit the whole app
        setSize(1200, 800);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);

        // --- Main Split Pane ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);

        // --- LEFT PANEL: Registration Form ---
        JPanel registrationContainerPanel = new JPanel(new GridBagLayout());
        registrationContainerPanel.setBackground(darkBgColor);
        
        // Use a JScrollPane to handle smaller screen sizes
        JScrollPane scrollPane = new JScrollPane(registrationContainerPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel formContentPanel = new JPanel(new GridBagLayout());
        formContentPanel.setOpaque(false);
        formContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formContentPanel.setPreferredSize(new Dimension(600, 700));
        
        // Create a container for the form with padding
        JPanel formWrapper = new JPanel(new GridBagLayout());
        formWrapper.setOpaque(false);
        GridBagConstraints wrapperGbc = new GridBagConstraints();
        wrapperGbc.anchor = GridBagConstraints.NORTH;
        wrapperGbc.weighty = 1.0;
        formWrapper.add(formContentPanel, wrapperGbc);
        
        registrationContainerPanel.add(formWrapper, new GridBagConstraints());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Vertical spacing and some padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        // gbc.ipadx = 350; // Removed fixed horizontal padding

        // Header Panel for Welcome Text
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));

        // Welcome text
        JLabel welcomeLabel = new JLabel("Create an Account");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(accentColor);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Join our premium bike showroom network");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitleLabel);
        
        formContentPanel.add(headerPanel, gbc);
        
        gbc.insets = new Insets(5, 10, 5, 10); // Reset insets

        // --- Form Fields ---
        // Name
        gbc.gridy++;
        formContentPanel.add(createLabel("Full Name"), gbc);
        gbc.gridy++;
        nameField = createTextField("Enter your full name");
        formContentPanel.add(nameField, gbc);

        // Phone
        gbc.gridy++;
        formContentPanel.add(createLabel("Contact No"), gbc);
        gbc.gridy++;
        phoneField = createTextField("Enter 10-digit mobile number");
        formContentPanel.add(phoneField, gbc);
        
        // Address Panel (with 2x2 grid)
        gbc.gridy++;
        formContentPanel.add(createLabel("Address"), gbc); // General Address label above grid
        gbc.gridy++;
        JPanel addressPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 cols, 10px gaps
        addressPanel.setOpaque(false);

        cityField = createTextField("City");
        districtField = createTextField("District");
        stateField = createTextField("State");
        pinCodeField = createTextField("Pin Code");

        addressPanel.add(cityField);
        addressPanel.add(districtField);
        addressPanel.add(stateField);
        addressPanel.add(pinCodeField);
        formContentPanel.add(addressPanel, gbc); // Add the address grid to the form

        // Email
        gbc.gridy++;
        formContentPanel.add(createLabel("Email"), gbc);
        gbc.gridy++;
        emailField = createTextField("Enter your email");
        formContentPanel.add(emailField, gbc);

        // Password
        gbc.gridy++;
        formContentPanel.add(createLabel("Password"), gbc);
        gbc.gridy++;
        passwordField = createPasswordField("Enter your password");
        formContentPanel.add(passwordField, gbc);

        // Register button
        gbc.gridy++;
        gbc.insets = new Insets(25, 10, 5, 10);
        registerButton = new JButton("Create Account");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setBackground(accentColor);
        registerButton.setForeground(textColor);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(this::handleRegistration);
        formContentPanel.add(registerButton, gbc);

        // Login link section
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 30, 0);
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        loginPanel.setOpaque(false);

        JLabel haveAccountLabel = new JLabel("Already have an account?");
        haveAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        haveAccountLabel.setForeground(Color.LIGHT_GRAY);

        JButton loginLink = new JButton("Login");
        loginLink.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginLink.setForeground(accentColor);
        loginLink.setBorderPainted(false);
        loginLink.setContentAreaFilled(false);
        loginLink.setFocusPainted(false);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect to the login link
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginLink.setForeground(new Color(255, 123, 51));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLink.setForeground(accentColor);
            }
        });

        // Add click action to redirect to login
        loginLink.addActionListener(_ -> {
            dispose(); // Close registration window
            SwingUtilities.invokeLater(() -> {
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            });
        });

        loginPanel.add(haveAccountLabel);
        loginPanel.add(Box.createHorizontalStrut(5));
        loginPanel.add(loginLink);
        formContentPanel.add(loginPanel, gbc);

        // --- RIGHT PANEL: Image (Same as LoginView) ---
        JLabel imagePanel = setupImagePanel();

        // --- Final Assembly ---
        splitPane.setLeftComponent(scrollPane);
        splitPane.setRightComponent(imagePanel);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Adjust divider to make the image panel roughly half or less of the screen
                splitPane.setDividerLocation(0.6); // 60% for the form, 40% for the image
                removeComponentListener(this);
            }
        });

        add(splitPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(textColor);
        return label;
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        styleField(field, placeholder);
        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        styleField(field, placeholder);
        return field;
    }

    private void styleField(JTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBackground(fieldBgColor);
        field.setForeground(textColor);
        field.setCaretColor(textColor);
        addPlaceholderStyle(field, placeholder);
    }
    
    private JLabel setupImagePanel() {
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
                        Image scaledImg = imageIcon.getImage().getScaledInstance(imagePanel.getWidth(), imagePanel.getHeight(), Image.SCALE_SMOOTH);
                        imagePanel.setIcon(new ImageIcon(scaledImg));
                    }
                });
            } else { throw new Exception("Image loading failed."); }
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
        
        return imagePanel;
    }


    private void handleRegistration(ActionEvent event) {
        // Retrieve text from all fields
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        String city = cityField.getText().trim();
        String district = districtField.getText().trim();
        String state = stateField.getText().trim();
        String pinCode = pinCodeField.getText().trim();

        // Check for empty fields or placeholders
        if (name.isEmpty() || name.equals("Enter your full name") ||
            phone.isEmpty() || phone.equals("Enter 10-digit mobile number") ||
            email.isEmpty() || email.equals("Enter your email") ||
            password.isEmpty() || password.equals("Enter your password") ||
            city.isEmpty() || city.equals("City") ||
            district.isEmpty() || district.equals("District") ||
            state.isEmpty() || state.equals("State") ||
            pinCode.isEmpty() || pinCode.equals("Pin Code")) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- Validation ---
        if (!authController.validateEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!authController.validatePhone(phone)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit phone number.", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!authController.validatePassword(password)) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Invalid Password", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- Combine address fields into a single string ---
        String fullAddress = String.join(", ", city, district, state) + " - " + pinCode;
        
        try {
            if (authController.registerUser(name, phone, fullAddress, email, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close registration window
                new LoginView().setVisible(true); // Open login window
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(RegistrationView::new);
    }
}