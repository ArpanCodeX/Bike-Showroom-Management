package showroom.admin.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import showroom.brands.Bajaj.controller.BajajController;
import showroom.brands.Hero.controller.HeroController;
import showroom.brands.Honda.controller.HondaController;
import showroom.brands.KTM.controller.KTMController;
import showroom.brands.RoyalEnfield.controller.RoyalEnfieldController;

public class ManageBikesView extends JFrame {
    private BajajController bajajController;
    private HeroController heroController;
    private HondaController hondaController;
    private KTMController ktmController;
    private RoyalEnfieldController reController;
    
    private JComboBox<String> brandComboBox;
    private JTable bikeTable;
    private DefaultTableModel tableModel;
    private JTextField modelNameField, priceField, engineField, fuelField, mileageField, colorField;
    private JButton addButton, updateButton, deleteButton, refreshButton;
    private String selectedBrand = "Bajaj";
    private int selectedRow = -1;

    public ManageBikesView() {
        initializeControllers();
        initializeUI();
    }

    private void initializeControllers() {
        this.bajajController = new BajajController();
        this.heroController = new HeroController();
        this.hondaController = new HondaController();
        this.ktmController = new KTMController();
        this.reController = new RoyalEnfieldController();
    }

    private void initializeUI() {
        setTitle("Manage Bikes - Admin Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Top Panel - Brand Selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setBackground(new Color(30, 30, 30));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel brandLabel = new JLabel("Select Brand:");
        brandLabel.setForeground(Color.WHITE);
        brandLabel.setFont(new Font("Arial", Font.BOLD, 14));

        String[] brands = {"Bajaj", "Hero", "Honda", "KTM", "Royal Enfield"};
        brandComboBox = new JComboBox<>(brands);
        brandComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        brandComboBox.addActionListener(e -> {
            selectedBrand = (String) brandComboBox.getSelectedItem();
            loadBikes();
            clearFields();
            selectedRow = -1;
        });

        topPanel.add(brandLabel);
        topPanel.add(brandComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Middle Panel - Table
        JPanel middlePanel = new JPanel(new BorderLayout(10, 10));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Model Name", "Price", "Engine", "Fuel Type", "Mileage", "Color"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bikeTable = new JTable(tableModel);
        bikeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bikeTable.getSelectionModel().addListSelectionListener(e -> {
            selectedRow = bikeTable.getSelectedRow();
            if (selectedRow >= 0) {
                loadSelectedBikeData();
            }
        });

        JScrollPane scrollPane = new JScrollPane(bikeTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        add(middlePanel, BorderLayout.CENTER);

        // Bottom Panel - Input Fields and Buttons
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Bike Details"));
        bottomPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Model Name
        addLabelAndField(bottomPanel, "Model Name:", modelNameField = new JTextField(20), gbc, 0);
        // Price
        addLabelAndField(bottomPanel, "Price:", priceField = new JTextField(20), gbc, 1);
        // Engine
        addLabelAndField(bottomPanel, "Engine:", engineField = new JTextField(20), gbc, 2);
        // Fuel Type
        addLabelAndField(bottomPanel, "Fuel Type:", fuelField = new JTextField(20), gbc, 3);
        // Mileage
        addLabelAndField(bottomPanel, "Mileage:", mileageField = new JTextField(20), gbc, 4);
        // Color
        addLabelAndField(bottomPanel, "Color:", colorField = new JTextField(20), gbc, 5);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(Color.WHITE);

        addButton = createButton("Add Bike", new Color(76, 175, 80));
        addButton.addActionListener(e -> addBike());

        updateButton = createButton("Update Bike", new Color(33, 150, 243));
        updateButton.addActionListener(e -> updateBike());

        deleteButton = createButton("Delete Bike", new Color(244, 67, 54));
        deleteButton.addActionListener(e -> deleteBike());

        refreshButton = createButton("Refresh", new Color(156, 39, 176));
        refreshButton.addActionListener(e -> loadBikes());

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        bottomPanel.add(buttonsPanel, gbc);

        JScrollPane bottomScroll = new JScrollPane(bottomPanel);
        add(bottomScroll, BorderLayout.SOUTH);

        loadBikes();
        setVisible(true);
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = (row % 3) * 2;
        gbc.gridy = row / 3;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(label, gbc);

        gbc.gridx = (row % 3) * 2 + 1;
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(field, gbc);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadBikes() {
        tableModel.setRowCount(0);

        switch (selectedBrand) {
            case "Bajaj":
                bajajController.getAllBikes().forEach(bike ->
                        tableModel.addRow(new Object[]{
                                bike.getModelName(),
                                bike.getPrice(),
                                bike.getEngineCapacity(),
                                bike.getFuelType(),
                                bike.getMileage(),
                                bike.getColor()
                        })
                );
                break;
            case "Hero":
                heroController.getAllBikes().forEach(bike ->
                        tableModel.addRow(new Object[]{
                                bike.getModelName(),
                                bike.getPrice(),
                                bike.getEngineCapacity(),
                                bike.getFuelType(),
                                bike.getMileage(),
                                bike.getColor()
                        })
                );
                break;
            case "Honda":
                hondaController.getAllBikes().forEach(bike ->
                        tableModel.addRow(new Object[]{
                                bike.getModelName(),
                                bike.getPrice(),
                                bike.getEngineCapacity(),
                                bike.getFuelType(),
                                bike.getMileage(),
                                bike.getColor()
                        })
                );
                break;
            case "KTM":
                ktmController.getAllBikes().forEach(bike ->
                        tableModel.addRow(new Object[]{
                                bike.getModelName(),
                                bike.getPrice(),
                                bike.getEngineCapacity(),
                                bike.getFuelType(),
                                bike.getMileage(),
                                bike.getColor()
                        })
                );
                break;
            case "Royal Enfield":
                reController.getAllBikes().forEach(bike ->
                        tableModel.addRow(new Object[]{
                                bike.getModelName(),
                                bike.getPrice(),
                                bike.getEngineCapacity(),
                                bike.getFuelType(),
                                bike.getMileage(),
                                bike.getColor()
                        })
                );
                break;
        }
    }

    private void loadSelectedBikeData() {
        if (selectedRow >= 0) {
            modelNameField.setText((String) tableModel.getValueAt(selectedRow, 0));
            priceField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            engineField.setText((String) tableModel.getValueAt(selectedRow, 2));
            fuelField.setText((String) tableModel.getValueAt(selectedRow, 3));
            mileageField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            colorField.setText((String) tableModel.getValueAt(selectedRow, 5));
        }
    }

    private void addBike() {
        if (validateFields()) {
            String modelName = modelNameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String engine = engineField.getText();
            String fuel = fuelField.getText();
            double mileage = Double.parseDouble(mileageField.getText());
            String color = colorField.getText();

            switch (selectedBrand) {
                case "Bajaj":
                    bajajController.addBike(new showroom.brands.Bajaj.model.BajajBike(modelName, price, engine, fuel, mileage, color));
                    break;
                case "Hero":
                    heroController.addBike(new showroom.brands.Hero.model.HeroBike(modelName, price, engine, fuel, mileage, color));
                    break;
                case "Honda":
                    hondaController.addBike(new showroom.brands.Honda.model.HondaBike(modelName, price, engine, fuel, mileage, color));
                    break;
                case "KTM":
                    ktmController.addBike(new showroom.brands.KTM.model.KTMBike(modelName, price, engine, fuel, mileage, color));
                    break;
                case "Royal Enfield":
                    reController.addBike(new showroom.brands.RoyalEnfield.model.RoyalEnfieldBike(modelName, price, engine, fuel, mileage, color));
                    break;
            }

            JOptionPane.showMessageDialog(this, "Bike added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadBikes();
        }
    }

    private void updateBike() {
        if (selectedRow >= 0 && validateFields()) {
            String oldModelName = (String) tableModel.getValueAt(selectedRow, 0);
            String newModelName = modelNameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String engine = engineField.getText();
            String fuel = fuelField.getText();
            double mileage = Double.parseDouble(mileageField.getText());
            String color = colorField.getText();

            switch (selectedBrand) {
                case "Bajaj":
                    bajajController.updateBike(oldModelName, new showroom.brands.Bajaj.model.BajajBike(newModelName, price, engine, fuel, mileage, color));
                    break;
                case "Hero":
                    heroController.updateBike(oldModelName, new showroom.brands.Hero.model.HeroBike(newModelName, price, engine, fuel, mileage, color));
                    break;
                case "Honda":
                    hondaController.updateBike(oldModelName, new showroom.brands.Honda.model.HondaBike(newModelName, price, engine, fuel, mileage, color));
                    break;
                case "KTM":
                    ktmController.updateBike(oldModelName, new showroom.brands.KTM.model.KTMBike(newModelName, price, engine, fuel, mileage, color));
                    break;
                case "Royal Enfield":
                    reController.updateBike(oldModelName, new showroom.brands.RoyalEnfield.model.RoyalEnfieldBike(newModelName, price, engine, fuel, mileage, color));
                    break;
            }

            JOptionPane.showMessageDialog(this, "Bike updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadBikes();
            selectedRow = -1;
        } else {
            JOptionPane.showMessageDialog(this, "Please select a bike to update!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBike() {
        if (selectedRow >= 0) {
            String modelName = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this bike?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                switch (selectedBrand) {
                    case "Bajaj":
                        bajajController.removeBike(modelName);
                        break;
                    case "Hero":
                        heroController.removeBike(modelName);
                        break;
                    case "Honda":
                        hondaController.removeBike(modelName);
                        break;
                    case "KTM":
                        ktmController.removeBike(modelName);
                        break;
                    case "Royal Enfield":
                        reController.removeBike(modelName);
                        break;
                }

                JOptionPane.showMessageDialog(this, "Bike deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadBikes();
                selectedRow = -1;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a bike to delete!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateFields() {
        if (modelNameField.getText().isEmpty() || priceField.getText().isEmpty() ||
                engineField.getText().isEmpty() || fuelField.getText().isEmpty() ||
                mileageField.getText().isEmpty() || colorField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Double.parseDouble(priceField.getText());
            Double.parseDouble(mileageField.getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price and Mileage must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void clearFields() {
        modelNameField.setText("");
        priceField.setText("");
        engineField.setText("");
        fuelField.setText("");
        mileageField.setText("");
        colorField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageBikesView());
    }
}
