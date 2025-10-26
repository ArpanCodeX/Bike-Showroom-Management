package showroom.admin.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StaffManagementView extends JFrame {
    private JTable staffTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, emailField, phoneField, roleField;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private final Color accentColor = new Color(0, 120, 215);

    public StaffManagementView() {
        setTitle("Staff Management - Bike Showroom Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title
        JLabel header = new JLabel("Staff Management", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(accentColor);
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(header, BorderLayout.NORTH);

        // Table model
        String[] columns = {"ID", "Name", "Email", "Phone", "Role"};
        tableModel = new DefaultTableModel(columns, 0);
        staffTable = new JTable(tableModel);
        staffTable.setRowHeight(25);
        staffTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        staffTable.setSelectionBackground(accentColor);
        staffTable.setSelectionForeground(Color.WHITE);

        JScrollPane tableScroll = new JScrollPane(staffTable);
        add(tableScroll, BorderLayout.CENTER);

        // Dummy data
        tableModel.addRow(new Object[]{"1", "Rahul Sen", "rahul@example.com", "9876543210", "Sales"});
        tableModel.addRow(new Object[]{"2", "Priya Das", "priya@example.com", "9876501234", "Mechanic"});
        tableModel.addRow(new Object[]{"3", "Rohit Sharma", "rohit@example.com", "9876549987", "Inventory"});

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        roleField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleField);

        add(formPanel, BorderLayout.SOUTH);

        // Buttons panel
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
        addButton.addActionListener(e -> addStaff());
        updateButton.addActionListener(e -> updateStaff());
        deleteButton.addActionListener(e -> deleteStaff());
        clearButton.addActionListener(e -> clearFields());

        staffTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = staffTable.getSelectedRow();
                if (row >= 0) {
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    emailField.setText(tableModel.getValueAt(row, 2).toString());
                    phoneField.setText(tableModel.getValueAt(row, 3).toString());
                    roleField.setText(tableModel.getValueAt(row, 4).toString());
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

    private void addStaff() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String role = roleField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int newId = tableModel.getRowCount() + 1;
        tableModel.addRow(new Object[]{String.valueOf(newId), name, email, phone, role});
        clearFields();
        JOptionPane.showMessageDialog(this, "Staff added successfully");
    }

    private void updateStaff() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to update", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setValueAt(nameField.getText(), selectedRow, 1);
        tableModel.setValueAt(emailField.getText(), selectedRow, 2);
        tableModel.setValueAt(phoneField.getText(), selectedRow, 3);
        tableModel.setValueAt(roleField.getText(), selectedRow, 4);

        clearFields();
        JOptionPane.showMessageDialog(this, "Staff updated successfully");
    }

    private void deleteStaff() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected staff?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            clearFields();
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        roleField.setText("");
        staffTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StaffManagementView::new);
    }
}
