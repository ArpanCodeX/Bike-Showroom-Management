package showroom.brands;

import java.awt.*;
import javax.swing.*;
import showroom.MainPage;

public class KTMPage extends JFrame {

    private JPanel detailPanel;
    private JLabel bikeNameLabel;
    private JLabel bikeImageLabel;
    private JTextArea bikeDetailsArea;

    public KTMPage() {
        setTitle("KTM Bikes Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Top navigation panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 87, 34)); // KTM Orange
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        String[] navButtons = {"Home", "New Bikes", "Used Bikes", "Offers", "Contact"};
        for (String text : navButtons) {
            JButton btn = new JButton(text);
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(255, 87, 34));
            topPanel.add(btn);
        }

        add(topPanel, BorderLayout.NORTH);

        // Main container
        JPanel container = new JPanel(new GridLayout(1, 2));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setLayout(new GridLayout(0, 2, 20, 20));

        String[] bikeModels = {
            "KTM Duke 125", "KTM Duke 200", "KTM Duke 250",
            "KTM RC 125", "KTM RC 200", "KTM RC 390",
            "KTM Adventure 250", "KTM 390 Adventure", "KTM 250 Duke"
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

        // Detail panel
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
        quitButton.setBackground(new Color(255, 87, 34));
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

    private void showBikeDetails(String bikeName) {
        String details = "";

        switch (bikeName) {
            case "KTM Duke 125":
                details = "Variants: Standard\nPrices: ₹1,60,000\nColors: Orange, Black";
                break;
            case "KTM Duke 200":
                details = "Variants: Standard\nPrices: ₹1,90,000\nColors: Orange, White";
                break;
            case "KTM Duke 250":
                details = "Variants: Standard\nPrices: ₹2,10,000\nColors: Orange, Black";
                break;
            case "KTM RC 125":
                details = "Variants: Standard\nPrices: ₹1,80,000\nColors: Orange, Black";
                break;
            case "KTM RC 200":
                details = "Variants: Standard\nPrices: ₹2,05,000\nColors: Orange, White";
                break;
            case "KTM RC 390":
                details = "Variants: Standard\nPrices: ₹3,00,000\nColors: Orange, Black";
                break;
            case "KTM Adventure 250":
                details = "Variants: Standard\nPrices: ₹2,50,000\nColors: Orange, Black";
                break;
            case "KTM 390 Adventure":
                details = "Variants: Standard\nPrices: ₹3,20,000\nColors: Orange, White";
                break;
            case "KTM 250 Duke":
                details = "Variants: Standard\nPrices: ₹2,20,000\nColors: Orange, Black";
                break;
        }

        bikeNameLabel.setText(bikeName);
        bikeDetailsArea.setText(details);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KTMPage().setVisible(true));
    }
}