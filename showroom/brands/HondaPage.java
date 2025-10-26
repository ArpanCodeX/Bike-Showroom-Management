package showroom.brands;

import java.awt.*;
import javax.swing.*;
import showroom.MainPage;

public class HondaPage extends JFrame {

    private JPanel detailPanel;
    private JLabel bikeNameLabel;
    private JLabel bikeImageLabel;
    private JTextArea bikeDetailsArea;

    public HondaPage() {
        setTitle("Honda Bikes Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Top navigation panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(33, 150, 243)); // Honda Blue
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        String[] navButtons = {"Home", "New Bikes", "Used Bikes", "Offers", "Contact"};
        for (String text : navButtons) {
            JButton btn = new JButton(text);
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(30, 136, 229));
            topPanel.add(btn);
        }

        add(topPanel, BorderLayout.NORTH);

        // Main container: left = bike list, right = details
        JPanel container = new JPanel(new GridLayout(1, 2));

        // Left panel: bike buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setLayout(new GridLayout(0, 2, 20, 20)); // 2 columns

        String[] bikeModels = {"Honda Shine", "Honda Activa", "Honda CB Hornet", 
                               "Honda Dio", "Honda CBR", "Honda X-Blade", 
                               "Honda H\u2019ness CB350", "Honda Grazia", "Honda Unicorn"};

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

        // Right panel: bike details
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        detailPanel.setBackground(Color.WHITE);

        bikeNameLabel = new JLabel("Select a bike to see details");
        bikeNameLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        bikeNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bikeImageLabel = new JLabel();
        bikeImageLabel.setPreferredSize(new Dimension(400, 250)); // placeholder for image
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
        quitButton.setBackground(new Color(33, 150, 243));
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
            case "Honda Shine":
                details = "Variants: Drum, Disc\nPrices: \u20b979,800 / \u20b983,200\nColors: Red, Black, Blue";
                break;
            case "Honda Activa":
                details = "Variants: 6G, 125\nPrices: \u20b976,000 / \u20b982,000\nColors: White, Grey, Blue";
                break;
            case "Honda CB Hornet":
                details = "Variants: 160R, 2.0\nPrices: \u20b91,10,000 / \u20b91,25,000\nColors: Blue, Black, Red";
                break;
            case "Honda Dio":
                details = "Variants: Standard, Deluxe\nPrices: \u20b970,000 / \u20b974,000\nColors: Yellow, Red, Grey";
                break;
            case "Honda CBR":
                details = "Variants: 250R, 650R\nPrices: \u20b91,90,000 / \u20b99,15,000\nColors: Red, Black";
                break;
            case "Honda X-Blade":
                details = "Variants: Single\nPrices: \u20b91,15,000\nColors: Blue, Red, Silver";
                break;
            case "Honda H\u2019ness CB350":
                details = "Variants: DLX, DLX Pro\nPrices: \u20b92,10,000 / \u20b92,15,000\nColors: Maroon, Green, Black";
                break;
            case "Honda Grazia":
                details = "Variants: Drum, Disc\nPrices: \u20b982,000 / \u20b986,000\nColors: Red, Grey, Yellow";
                break;
            case "Honda Unicorn":
                details = "Variants: Single\nPrices: \u20b91,09,000\nColors: Black, Grey, Red";
                break;
        }

        bikeNameLabel.setText(bikeName);
        bikeDetailsArea.setText(details);
        // bikeImageLabel.setIcon(new ImageIcon("path/to/image.jpg")); // add image if available
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HondaPage().setVisible(true));
    }
}
