package showroom.chat.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import showroom.chat.model.ChatMessage;
import showroom.chat.service.GeminiService;

public class ChatbotPage extends JFrame {

    // --- Colors and Fonts remain mostly the same ---
    private static final Color DARK_BG = new Color(15, 15, 15);
    private static final Color DARKER_BG = new Color(25, 25, 25);
    private static final Color ACCENT_COLOR = new Color(255, 87, 34);
    private static final Color ACCENT_HOVER = new Color(255, 110, 64);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color USER_BUBBLE_COLOR = new Color(255, 87, 34);
    private static final Color BOT_BUBBLE_COLOR = new Color(55, 55, 55);
    private static final Color BOT_BUBBLE_BORDER = new Color(70, 70, 70); // Unused, but kept
    private static final Color TYPING_INDICATOR_COLOR = new Color(150, 150, 150);
    private static final Color PLACEHOLDER_TEXT_COLOR = new Color(120, 120, 120);

    private static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font HEADER_FONT = new Font("Segoe UI Semibold", Font.BOLD, 24);
    private static final Font TIMESTAMP_FONT = new Font("Segoe UI", Font.PLAIN, 11);

    private JPanel chatPanel;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane scrollPane;
    private final SimpleDateFormat timeFormat;
    private final GeminiService geminiService;
    private JPanel typingIndicator;
    private final String placeholder = "Type your message...";

    public ChatbotPage() {
        this.timeFormat = new SimpleDateFormat("HH:mm");

        try {
            this.geminiService = new GeminiService();
        } catch (RuntimeException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Could not initialize Gemini Service.\nCheck your .env configuration.",
                    "Initialization Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            throw new IllegalStateException("Gemini service failed to initialize.", e);
        }

        // --- MODIFIED: Window Title ---
        setTitle("AI Chat Assistant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initializeUI();
        addWelcomeMessage();
    }

    private void initializeUI() {
        getContentPane().setBackground(DARK_BG);

        // --- MODIFIED: Header ---
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0)); // Added horizontal gap
        headerPanel.setBackground(DARKER_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Icon (using emoji for simplicity, matches your typing indicator)
        JLabel iconLabel = new JLabel("🤖");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setForeground(ACCENT_COLOR);
        headerPanel.add(iconLabel, BorderLayout.WEST);

        // Title and Subtitle Container
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false); // Transparent background

        JLabel titleLabel = new JLabel("AI Chat Assistant"); // Changed text
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        JLabel subtitleLabel = new JLabel("Local Knowledge Base"); // Changed text
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Removed italic
        subtitleLabel.setForeground(new Color(150, 150, 150));
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        headerPanel.add(titlePanel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // --- Chat Area (Unchanged) ---
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(DARK_BG);
        chatPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(DARK_BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(80, 80, 80);
                this.trackColor = DARK_BG;
            }
        });

        typingIndicator = createTypingIndicator();
        typingIndicator.setVisible(false);

        add(scrollPane, BorderLayout.CENTER);

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new BorderLayout(15, 0));
        inputPanel.setBackground(DARKER_BG);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        // --- MODIFIED: Input Field (with Placeholder) ---
        inputField = new JTextField(placeholder);
        inputField.setFont(MAIN_FONT);
        inputField.setForeground(PLACEHOLDER_TEXT_COLOR); // Start with placeholder color
        inputField.setBackground(new Color(35, 35, 35));
        inputField.setCaretColor(ACCENT_COLOR);
        // Use a rounded border
        inputField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(35, 35, 35), 2, true), // Border matches bg
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
        inputField.setPreferredSize(new Dimension(0, 38));

        // Placeholder text logic
        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals(placeholder)) {
                    inputField.setText("");
                    inputField.setForeground(TEXT_COLOR);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setForeground(PLACEHOLDER_TEXT_COLOR);
                    inputField.setText(placeholder);
                }
            }
        });

        // --- MODIFIED: Send Button (Icon Button) ---
        sendButton = new JButton("➤") { // Use a paper plane-like symbol
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Change color on hover
                if (getModel().isRollover()) {
                    g2.setColor(ACCENT_HOVER);
                } else {
                    g2.setColor(ACCENT_COLOR);
                }
                
                // Draw rounded rectangle
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); 
                g2.dispose();
                
                // Let the button's superclass paint the text (the "➤" icon)
                super.paintComponent(g);
            }
        };
        sendButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        sendButton.setForeground(TEXT_COLOR);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sendButton.setOpaque(false); // We are custom painting the background
        sendButton.setContentAreaFilled(false); // Required for custom painting
        sendButton.setPreferredSize(new Dimension(55, 38)); // Squarish, matches height

        // (Original mouse listener removed as paintComponent now handles hover)

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Actions
        ActionListener sendAction = _ -> sendMessage();
        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                inputField.requestFocusInWindow();
            }
        });
    }

    // --- MODIFIED: Welcome Message ---
    private void addWelcomeMessage() {
        String welcomeText = "Welcome! I'm your AI assistant. Ask me anything!";
        addMessage(new ChatMessage(welcomeText, false));
    }

    private void sendMessage() {
        String userText = inputField.getText().trim();
        // --- MODIFIED: Check for placeholder ---
        if (userText.isEmpty() || userText.equals(placeholder)) return;

        addMessage(new ChatMessage(userText, true));
        
        // --- MODIFIED: Reset to placeholder ---
        inputField.setForeground(PLACEHOLDER_TEXT_COLOR);
        inputField.setText(placeholder);

        inputField.setEnabled(false);
        sendButton.setEnabled(false);

        showTypingIndicator();

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                return geminiService.generateResponse(userText);
            }

            @Override
            protected void done() {
                try {
                    String botResponse = get();
                    hideTypingIndicator();
                    addMessage(new ChatMessage(botResponse, false));
                } catch (Exception e) {
                    e.printStackTrace();
                    hideTypingIndicator();
                    addMessage(new ChatMessage("⚠️ Oops! Something went wrong. Try again.", false));
                } finally {
                    inputField.setEnabled(true);
                    sendButton.setEnabled(true);
                    inputField.requestFocusInWindow();
                }
            }
        };
        worker.execute();
    }

    // --- MODIFIED: addMessage (simplified) ---
    private void addMessage(ChatMessage message) {
        // The timestamp panel is now created *inside* createMessageBubble
        JPanel bubble = createMessageBubble(message);
        
        chatPanel.add(bubble);
        // chatPanel.add(timePanel); // Removed
        chatPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Added more space

        chatPanel.revalidate();
        chatPanel.repaint();
        scrollToBottom();
    }

    // --- MODIFIED: createMessageBubble (now includes timestamp) ---
    private JPanel createMessageBubble(ChatMessage message) {
        JTextArea textArea = new JTextArea(message.getMessage());
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setForeground(new Color(245, 245, 245));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setOpaque(false); // Make transparent, parent panel will paint
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 18, 5, 18)); // Main padding
        textArea.setBackground(new Color(0, 0, 0, 0)); // Fully transparent background

        // --- NEW: Timestamp Label (created inside the bubble) ---
        JLabel timeLabel = new JLabel(timeFormat.format(new Date(message.getTimestamp())));
        timeLabel.setFont(TIMESTAMP_FONT);
        timeLabel.setForeground(new Color(220, 220, 220, 200)); // White-ish, slightly transparent

        // Align timestamp right for user, left for bot
        int align = message.isUser() ? FlowLayout.RIGHT : FlowLayout.LEFT;
        JPanel timePanel = new JPanel(new FlowLayout(align, 18, 0)); // 18px horizontal gap
        timePanel.setOpaque(false);
        timePanel.add(timeLabel);
        timePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Padding at bottom

        // Custom rounded panel (from your original code, simplified)
        JPanel rounded = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Use flat color (no gradient)
                Color bgColor = message.isUser() ? USER_BUBBLE_COLOR : BOT_BUBBLE_COLOR;
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                // Add a subtle border for bot messages
                if (!message.isUser()) {
                    g2.setColor(new Color(80, 80, 80));
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 25, 25);
                }
                
                g2.dispose();
                // Don't call super.paintComponent(g) as we are fully custom painting
            }
        };
        rounded.setOpaque(false);
        rounded.setLayout(new BorderLayout());
        rounded.add(textArea, BorderLayout.CENTER);
        rounded.add(timePanel, BorderLayout.SOUTH); // Add timestamp panel to bottom
        
        // Set a max width for the bubble
        rounded.setMaximumSize(new Dimension(650, 9999)); 

        // Wrapper panel for alignment (from your original code)
        JPanel wrapper = new JPanel(new FlowLayout(message.isUser() ? FlowLayout.RIGHT : FlowLayout.LEFT));
        wrapper.setBackground(DARK_BG);
        wrapper.add(rounded);

        return wrapper;
    }

    // --- DELETED: createTimestampPanel() method is no longer needed ---

    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = scrollPane.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }

    // --- MODIFIED: Typing Indicator Text ---
    private JPanel createTypingIndicator() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel avatar = new JLabel("🤖");
        avatar.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel typingLabel = new JLabel("AI Chat Assistant is typing..."); // Changed text
        typingLabel.setFont(TIMESTAMP_FONT);
        typingLabel.setForeground(TYPING_INDICATOR_COLOR);

        JPanel dotsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        dotsPanel.setBackground(DARK_BG);

        for (int i = 0; i < 3; i++) {
            JLabel dot = new JLabel("●");
            dot.setFont(new Font("Segoe UI", Font.BOLD, 8));
            dot.setForeground(TYPING_INDICATOR_COLOR);
            dotsPanel.add(dot);
        }

        panel.add(avatar);
        panel.add(typingLabel);
        panel.add(dotsPanel);

        return panel;
    }

    private void showTypingIndicator() {
        SwingUtilities.invokeLater(() -> {
            chatPanel.add(typingIndicator);
            typingIndicator.setVisible(true);
            chatPanel.revalidate();
            chatPanel.repaint();
            scrollToBottom();
        });
    }

    private void hideTypingIndicator() {
        SwingUtilities.invokeLater(() -> {
            chatPanel.remove(typingIndicator);
            typingIndicator.setVisible(false);
            chatPanel.revalidate();
            chatPanel.repaint();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatbotPage().setVisible(true));
    }
}