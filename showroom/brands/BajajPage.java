package showroom.brands;

import java.awt.*;
import javax.swing.*;
import showroom.MainPage;

public class BajajPage extends JFrame {

    private JPanel detailPanel;
    private JLabel bikeNameLabel;
    private JLabel bikeImageLabel;
    private JTextArea bikeDetailsArea;

    public BajajPage() {
        setTitle("Bajaj Bikes Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Top navigation panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 152, 0)); // Bajaj Orange
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        String[] navButtons = {"Home", "New Bikes", "Used Bikes", "Offers", "Contact"};
        for (String text : navButtons) {
            JButton btn = new JButton(text);
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(255, 152, 0));
            topPanel.add(btn);
        }

        add(topPanel, BorderLayout.NORTH);

        // Main container: left bike list, right details
        JPanel container = new JPanel(new GridLayout(1, 2));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setLayout(new GridLayout(0, 2, 20, 20));

        String[] bikeModels = {
            "Bajaj Pulsar 150", "Bajaj Pulsar NS200", "Bajaj Dominar 400",
            "Bajaj Avenger Cruise 220", "Bajaj Platina 100", "Bajaj CT100",
            "Bajaj Pulsar 220F", "Bajaj Chetak", "Bajaj Avenger Street 160"
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
        quitButton.setBackground(new Color(255, 152, 0));
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
            case "Bajaj Pulsar 150":
                details = "Variants: DTS-i, Twin Disc\nPrices: ₹1,10,000 / ₹1,15,000\nColors: Black, Red, Blue";
                break;
            case "Bajaj Pulsar NS200":
                details = "Variants: Standard, BS6\nPrices: ₹1,30,000 / ₹1,35,000\nColors: Black, Grey";
                break;
            case "Bajaj Dominar 400":
                details = "Variants: Single Disc, ABS\nPrices: ₹2,20,000 / ₹2,35,000\nColors: Black, Grey, Blue";
                break;
            case "Bajaj Avenger Cruise 220":
                details = "Variants: Standard\nPrices: ₹1,45,000\nColors: Black, Maroon";
                break;
            case "Bajaj Platina 100":
                details = "Variants: Drum, Kick Start\nPrices: ₹70,000 / ₹68,000\nColors: Blue, Grey";
                break;
            case "Bajaj CT100":
                details = "Variants: Standard\nPrices: ₹65,000\nColors: Red, Black";
                break;
            case "Bajaj Pulsar 220F":
                details = "Variants: Single Disc, Dual Disc\nPrices: ₹1,20,000 / ₹1,25,000\nColors: Black, Red";
                break;
            case "Bajaj Chetak":
                details = "Variants: Electric\nPrices: ₹1,25,000\nColors: Blue, White";
                break;
            case "Bajaj Avenger Street 160":
                details = "Variants: Standard\nPrices: ₹1,05,000\nColors: Black, Grey";
                break;
        }

        bikeNameLabel.setText(bikeName);
        bikeDetailsArea.setText(details);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BajajPage().setVisible(true));
    }
}