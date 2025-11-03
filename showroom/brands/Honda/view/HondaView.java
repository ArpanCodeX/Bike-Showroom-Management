package showroom.brands.Honda.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import showroom.brands.Honda.model.HondaBike;
import showroom.brands.Honda.controller.HondaController;

public class HondaView extends JFrame {
    private HondaController controller;
    private JPanel bikeListPanel;
    private JPanel bikeDetailsPanel;

    public HondaView() {
        this.controller = new HondaController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Honda Bikes Showroom");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(220, 128, 0));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel headerLabel = new JLabel("HONDA BIKES");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main content
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left: Bike List
        bikeListPanel = new JPanel();
        bikeListPanel.setLayout(new BoxLayout(bikeListPanel, BoxLayout.Y_AXIS));
        bikeListPanel.setBackground(Color.WHITE);
        
        List<HondaBike> bikes = controller.getAllBikes();
        for (HondaBike bike : bikes) {
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

    private JButton createBikeButton(HondaBike bike) {
        JButton button = new JButton(bike.getModelName());
        button.setBackground(new Color(220, 128, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setMaximumSize(new Dimension(300, 50));
        button.addActionListener(e -> displayBikeDetails(bike));
        return button;
    }

    private void displayBikeDetails(HondaBike bike) {
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
        SwingUtilities.invokeLater(() -> new HondaView().setVisible(true));
    }
}
