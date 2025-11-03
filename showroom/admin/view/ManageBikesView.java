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
    
    // Color scheme
    private final Color PRIMARY_COLOR = new Color(255, 103, 31);     // Orange
    private final Color SECONDARY_COLOR = new Color(26, 26, 26);    // Dark Black
    private final Color BACKGROUND_COLOR = new Color(20, 20, 20);   // Very Dark

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
        setTitle("🏍 Manage Bikes - Admin Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 15));
        headerPanel.setPreferredSize(new Dimension(0, 70));

        JLabel titleLabel = new JLabel("🏍 Manage Bikes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);

        JLabel brandLabel = new JLabel("Select Brand:");
        brandLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        brandLabel.setForeground(Color.WHITE);

        String[] brands = {"Bajaj", "Hero", "Honda", "KTM", "Royal Enfield"};
        brandComboBox = new JComboBox<>(brands);
        brandComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        brandComboBox.setBackground(SECONDARY_COLOR);
        brandComboBox.setForeground(Color.WHITE);
        brandComboBox.setPreferredSize(new Dimension(150, 35));
        brandComboBox.addActionListener(e -> {
            selectedBrand = (String) brandComboBox.getSelectedItem();
            loadBikes();
            clearFields();
            selectedRow = -1;
        });

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalStrut(50));
        headerPanel.add(brandLabel);
        headerPanel.add(brandComboBox);

        add(headerPanel, BorderLayout.NORTH);

        // Middle Panel - Table
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(BACKGROUND_COLOR);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] columnNames = {"Model Name", "Price", "Engine", "Fuel Type", "Mileage", "Color"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        bikeTable = new JTable(tableModel);
        bikeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bikeTable.setBackground(SECONDARY_COLOR);
        bikeTable.setForeground(Color.WHITE);
        bikeTable.setGridColor(new Color(50, 50, 50));
        bikeTable.setRowHeight(30);
        bikeTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bikeTable.setSelectionBackground(PRIMARY_COLOR);
        bikeTable.getTableHeader().setBackground(PRIMARY_COLOR);
        bikeTable.getTableHeader().setForeground(Color.BLACK);
        bikeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        bikeTable.getSelectionModel().addListSelectionListener(e -> {
            selectedRow = bikeTable.getSelectedRow();
            if (selectedRow >= 0) {
                loadSelectedBikeData();
            }
        });

        JScrollPane scrollPane = new JScrollPane(bikeTable);
        scrollPane.setBackground(SECONDARY_COLOR);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        add(middlePanel, BorderLayout.CENTER);

        // Bottom Panel - Input Fields and Buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(SECONDARY_COLOR);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, PRIMARY_COLOR),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        // Input Fields Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        inputPanel.setBackground(SECONDARY_COLOR);
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            "Bike Details",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            PRIMARY_COLOR
        ));

        modelNameField = createStyledTextField();
        priceField = createStyledTextField();
        engineField = createStyledTextField();
        fuelField = createStyledTextField();
        mileageField = createStyledTextField();
        colorField = createStyledTextField();

        inputPanel.add(createFieldWithLabel("Model Name:", modelNameField, PRIMARY_COLOR));
        inputPanel.add(createFieldWithLabel("Price:", priceField, PRIMARY_COLOR));
        inputPanel.add(createFieldWithLabel("Engine:", engineField, PRIMARY_COLOR));
        inputPanel.add(createFieldWithLabel("Fuel Type:", fuelField, PRIMARY_COLOR));
        inputPanel.add(createFieldWithLabel("Mileage:", mileageField, PRIMARY_COLOR));
        inputPanel.add(createFieldWithLabel("Color:", colorField, PRIMARY_COLOR));

        bottomPanel.add(inputPanel);
        bottomPanel.add(Box.createVerticalStrut(15));

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonsPanel.setBackground(SECONDARY_COLOR);

        addButton = createStyledButton("➕ Add Bike", PRIMARY_COLOR);
        addButton.addActionListener(e -> addBike());

        updateButton = createStyledButton("✏️ Update", new Color(255, 140, 60));
        updateButton.addActionListener(e -> updateBike());

        deleteButton = createStyledButton("🗑️ Delete", new Color(220, 100, 60));
        deleteButton.addActionListener(e -> deleteBike());

        refreshButton = createStyledButton("🔄 Refresh", new Color(100, 150, 255));
        refreshButton.addActionListener(e -> loadBikes());

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);

        bottomPanel.add(buttonsPanel);

        JScrollPane bottomScroll = new JScrollPane(bottomPanel);
        bottomScroll.setBackground(BACKGROUND_COLOR);
        bottomScroll.setBorder(null);
        add(bottomScroll, BorderLayout.SOUTH);

        loadBikes();
        setVisible(true);
    }

    private JPanel createFieldWithLabel(String labelText, JTextField field, Color color) {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.setBackground(SECONDARY_COLOR);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(color);
        label.setPreferredSize(new Dimension(100, 30));
        
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(new Color(40, 40, 40));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        field.setCaretColor(Color.WHITE);
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 40));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(Math.min(255, bgColor.getRed() + 30),
                                              Math.min(255, bgColor.getGreen() + 30),
                                              Math.min(255, bgColor.getBlue() + 30)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
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
