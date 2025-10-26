package showroom.core;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("unused")
public class BikeDetailsFrame extends JFrame {

    public BikeDetailsFrame(String bikeName, JFrame parentFrame) {
        setTitle(bikeName + " - Details");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        // Hide parent page when opening this
        parentFrame.setVisible(false);

        // Title
        JLabel titleLabel = new JLabel(bikeName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(33, 150, 243));
        add(titleLabel, BorderLayout.NORTH);

        // Center panel for bike info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout(20, 20));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        infoPanel.setBackground(Color.WHITE);

        // Image placeholder
        JLabel imageLabel = new JLabel("Bike Image Here", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(400, 250));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(230, 230, 230));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(imageLabel, BorderLayout.NORTH);

        // Specs area
        JTextArea specsArea = new JTextArea();
        specsArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        specsArea.setEditable(false);
        specsArea.setLineWrap(true);
        specsArea.setWrapStyleWord(true);

        String specs = getBikeSpecs(bikeName);
        specsArea.setText(specs);
        infoPanel.add(new JScrollPane(specsArea), BorderLayout.CENTER);

        add(infoPanel, BorderLayout.CENTER);

        // Quit button
        JButton quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        quitBtn.setBackground(new Color(220, 53, 69));
        quitBtn.setForeground(Color.WHITE);
        quitBtn.addActionListener(e -> {
            dispose();
            parentFrame.setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(quitBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String getBikeSpecs(String bikeName) {
        switch (bikeName) {
            case "Honda Shine":
                return "Model: Honda Shine 125\n"
                     + "Variants: Drum, Disc\n"
                     + "Price: ₹79,800 - ₹83,200\n"
                     + "Colors: Black, Red, Grey, Blue\n"
                     + "Engine: 124cc Air-cooled\n"
                     + "Mileage: 55 km/l\n"
                     + "Top Speed: 100 km/h\n"
                     + "Transmission: 5-speed\n"
                     + "Fuel Tank: 10.5 L";
            case "Honda Activa":
                return "Model: Honda Activa 6G / 125\n"
                     + "Variants: Standard, Deluxe\n"
                     + "Price: ₹76,000 - ₹82,000\n"
                     + "Colors: White, Blue, Grey\n"
                     + "Engine: 109cc / 124cc Air-cooled\n"
                     + "Mileage: 50 km/l\n"
                     + "Top Speed: 85 km/h\n"
                     + "Transmission: Automatic (CVT)";
            // Add more bike specs here
            default:
                return "Details not available.";
        }
    }
}