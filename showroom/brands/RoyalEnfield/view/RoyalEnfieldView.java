package showroom.brands.RoyalEnfield.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import showroom.brands.RoyalEnfield.model.RoyalEnfieldBike;
import showroom.brands.RoyalEnfield.controller.RoyalEnfieldController;

public class RoyalEnfieldView extends JFrame {
    private RoyalEnfieldController controller;
    private JPanel bikeListPanel;
    private JPanel bikeDetailsPanel;

    public RoyalEnfieldView() {
        this.controller = new RoyalEnfieldController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Royal Enfield Bikes Showroom");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(34, 34, 34));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel headerLabel = new JLabel("ROYAL ENFIELD BIKES");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(new Color(255, 140, 0));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main content
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left: Bike List
        bikeListPanel = new JPanel();
        bikeListPanel.setLayout(new BoxLayout(bikeListPanel, BoxLayout.Y_AXIS));
        bikeListPanel.setBackground(Color.WHITE);
        
        List<RoyalEnfieldBike> bikes = controller.getAllBikes();
        for (RoyalEnfieldBike bike : bikes) {
            bikeListPanel.add(createBikeButton(bike));
        }

        JScrollPane listScroll = new JScrollPane(bikeListPanel);
        mainPanel.add(listScroll);

        // Right: Bike Details
        bikeDetailsPanel = new JPanel();
        bikeDetailsPanel.setBackground(Color.WHITE);
        bikeDetailsPanel.setLayout(new BorderLayout());
        
        JLabel detailsLabel = new JLabel("Select a bike to view details");
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bikeDetailsPanel.add(detailsLabel, BorderLayout.CENTER);

        mainPanel.add(bikeDetailsPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createBikeButton(RoyalEnfieldBike bike) {
        JButton button = new JButton(bike.getModelName());
        button.setBackground(new Color(34, 34, 34));
        button.setForeground(new Color(255, 140, 0));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setMaximumSize(new Dimension(300, 50));
        button.addActionListener(e -> displayBikeDetails(bike));
        return button;
    }

    private void displayBikeDetails(RoyalEnfieldBike bike) {
        bikeDetailsPanel.removeAll();
        
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addDetailLabel(detailsPanel, "Model: " + bike.getModelName());
        addDetailLabel(detailsPanel, "Price: ₹" + bike.getPrice());
        addDetailLabel(detailsPanel, "Engine: " + bike.getEngineCapacity());
        addDetailLabel(detailsPanel, "Fuel Type: " + bike.getFuelType());
        addDetailLabel(detailsPanel, "Mileage: " + bike.getMileage() + " km/l");
        addDetailLabel(detailsPanel, "Color: " + bike.getColor());

        bikeDetailsPanel.add(detailsPanel, BorderLayout.NORTH);
        bikeDetailsPanel.revalidate();
        bikeDetailsPanel.repaint();
    }

    private void addDetailLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(label);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RoyalEnfieldView().setVisible(true));
    }
}
