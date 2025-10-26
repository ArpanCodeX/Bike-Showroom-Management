package showroom.brands;

import java.awt.*;
import javax.swing.*;
import showroom.MainPage;

public class HeroPage extends JFrame {

    private JPanel detailPanel;
    private JLabel bikeNameLabel;
    private JLabel bikeImageLabel;
    private JTextArea bikeDetailsArea;

    public HeroPage() {
        setTitle("Hero Bikes Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Full-screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Top panel with navigation buttons
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(244, 67, 54)); // Hero Red
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        String[] navButtons = {"Home", "New Bikes", "Used Bikes", "Offers", "Contact"};
        for (String text : navButtons) {
            JButton btn = new JButton(text);
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(229, 57, 53));
            topPanel.add(btn);
        }

        add(topPanel, BorderLayout.NORTH);

        // Main container (left list + right details)
        JPanel container = new JPanel(new GridLayout(1, 2));

        // Left panel with bike buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setLayout(new GridLayout(0, 2, 20, 20));

        String[] bikeModels = {
            "Hero Splendor Plus", "Hero Passion XPro", "Hero Glamour",
            "Hero Super Splendor", "Hero HF Deluxe", "Hero XPulse 200",
            "Hero Xtreme 160R", "Hero Maestro Edge", "Hero Pleasure+"
        };

        for (String bike : bikeModels) {
            JButton bikeBtn = new JButton(bike);
            bikeBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
            bikeBtn.setBackground(Color.WHITE);
            bikeBtn.setFocusPainted(false);
            bikeBtn.addActionListener(e -> showBikeDetails(bike));
            mainPanel.add(bikeBtn);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        container.add(scrollPane);

        // Right panel for details
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        detailPanel.setBackground(Color.WHITE);

        bikeNameLabel = new JLabel("Select a bike to see details");
        bikeNameLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        bikeNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bikeImageLabel = new JLabel();
        bikeImageLabel.setPreferredSize(new Dimension(400, 250));
        bikeImageLabel.setOpaque(true);
        bikeImageLabel.setBackground(new Color(230, 230, 230));
        bikeImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        bikeImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bikeDetailsArea = new JTextArea(8, 30);
        bikeDetailsArea.setEditable(false);
        bikeDetailsArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        bikeDetailsArea.setLineWrap(true);
        bikeDetailsArea.setWrapStyleWord(true);
        bikeDetailsArea.setBackground(new Color(245, 245, 245));

        JButton quitButton = new JButton("Quit");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setBackground(new Color(229, 57, 53));
        quitButton.setForeground(Color.WHITE);
        quitButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        quitButton.addActionListener(e -> {
            dispose();
            new MainPage("User").setVisible(true);
        });

        detailPanel.add(bikeNameLabel);
        detailPanel.add(Box.createVerticalStrut(20));
        detailPanel.add(bikeImageLabel);
        detailPanel.add(Box.createVerticalStrut(20));
        detailPanel.add(bikeDetailsArea);
        detailPanel.add(Box.createVerticalStrut(30));
        detailPanel.add(quitButton);

        container.add(detailPanel);
        add(container, BorderLayout.CENTER);

        setVisible(true);
    }

    // Method to display bike specs
    private void showBikeDetails(String bikeName) {
        String details = "";

        switch (bikeName) {
            case "Hero Splendor Plus":
                details = "Variants: Self Start, i3S, Black and Accent\nPrices: ₹75,000 / ₹77,000 / ₹79,000\nColors: Black, Red, Grey";
                break;
            case "Hero Passion XPro":
                details = "Variants: Drum, Disc\nPrices: ₹79,000 / ₹83,000\nColors: Red, Blue, Grey";
                break;
            case "Hero Glamour":
                details = "Variants: Drum, Disc\nPrices: ₹82,000 / ₹86,000\nColors: Red, Blue, Black";
                break;
            case "Hero Super Splendor":
                details = "Variants: Drum, Disc\nPrices: ₹83,000 / ₹87,000\nColors: Black, Grey, Blue";
                break;
            case "Hero HF Deluxe":
                details = "Variants: Kick Start, Self Start\nPrices: ₹66,000 / ₹71,000\nColors: Red, Black, Grey";
                break;
            case "Hero XPulse 200":
                details = "Variants: Standard, 4V\nPrices: ₹1,40,000 / ₹1,45,000\nColors: White, Red, Black";
                break;
            case "Hero Xtreme 160R":
                details = "Variants: Single Disc, Dual Disc, Stealth\nPrices: ₹1,18,000 / ₹1,25,000 / ₹1,30,000\nColors: Grey, Red, Black";
                break;
            case "Hero Maestro Edge":
                details = "Variants: 110, 125\nPrices: ₹79,000 / ₹86,000\nColors: Blue, Red, Silver";
                break;
            case "Hero Pleasure+":
                details = "Variants: Standard, Xtec\nPrices: ₹70,000 / ₹78,000\nColors: Red, Yellow, Silver";
                break;
        }

        bikeNameLabel.setText(bikeName);
        bikeDetailsArea.setText(details);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HeroPage().setVisible(true));
    }
}