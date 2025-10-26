package showroom.admin.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class StockManagementView extends JFrame {
    private JTable stockTable;
    private DefaultTableModel tableModel;
    private JTextField bikeNameField, modelField, quantityField, priceField;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private final Color accentColor = new Color(0, 120, 215);

    public StockManagementView() {
        setTitle("Stock Management admin - Bike Showroom Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Stock Management", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(accentColor);
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(header, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Bike Name", "Model", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columns, 0);
        stockTable = new JTable(tableModel);
        stockTable.setRowHeight(25);
        stockTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stockTable.setSelectionBackground(accentColor);
        stockTable.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(stockTable);
        add(scrollPane, BorderLayout.CENTER);

        // Dummy data
        tableModel.addRow(new Object[]{"1", "Yamaha MT-15", "2024", "8", "165000"});
        tableModel.addRow(new Object[]{"2", "Royal Enfield Classic 350", "2023", "5", "198000"});
        tableModel.addRow(new Object[]{"3", "TVS Apache RTR 160", "2024", "10", "130000"});

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bikeNameField = new JTextField();
        modelField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();

        formPanel.add(new JLabel("Bike Name:"));
        formPanel.add(bikeNameField);
        formPanel.add(new JLabel("Model:"));
        formPanel.add(modelField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);

        add(formPanel, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        addButton = createButton("Add");
        updateButton = createButton("Update");
        deleteButton = createButton("Delete");
        clearButton = createButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.PAGE_END);

        // Button actions
        addButton.addActionListener(e -> addStock());
        updateButton.addActionListener(e -> updateStock());
        deleteButton.addActionListener(e -> deleteStock());
        clearButton.addActionListener(e -> clearFields());

        stockTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = stockTable.getSelectedRow();
                if (row >= 0) {
                    bikeNameField.setText(tableModel.getValueAt(row, 1).toString());
                    modelField.setText(tableModel.getValueAt(row, 2).toString());
                    quantityField.setText(tableModel.getValueAt(row, 3).toString());
                    priceField.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 35));
        return button;
    }

    private void addStock() {
        String bikeName = bikeNameField.getText().trim();
        String model = modelField.getText().trim();
        String quantity = quantityField.getText().trim();
        String price = priceField.getText().trim();

        if (bikeName.isEmpty() || model.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int newId = tableModel.getRowCount() + 1;
        tableModel.addRow(new Object[]{String.valueOf(newId), bikeName, model, quantity, price});
        clearFields();
        JOptionPane.showMessageDialog(this, "Stock added successfully");
    }

    private void updateStock() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to update", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setValueAt(bikeNameField.getText(), selectedRow, 1);
        tableModel.setValueAt(modelField.getText(), selectedRow, 2);
        tableModel.setValueAt(quantityField.getText(), selectedRow, 3);
        tableModel.setValueAt(priceField.getText(), selectedRow, 4);

        clearFields();
        JOptionPane.showMessageDialog(this, "Stock updated successfully");
    }

    private void deleteStock() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected stock?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            clearFields();
        }
    }

    private void clearFields() {
        bikeNameField.setText("");
        modelField.setText("");
        quantityField.setText("");
        priceField.setText("");
        stockTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StockManagementView::new);
    }
}
