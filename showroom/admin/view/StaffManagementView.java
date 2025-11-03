package showroom.admin.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import showroom.admin.controller.StaffController;
import showroom.admin.model.Staff;
import java.util.List;
import java.time.LocalDate;

public class StaffManagementView extends JFrame {
    private StaffController staffController;
    private JTable staffTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, emailField, phoneField, positionField, departmentField, salaryField;
    private JButton addButton, updateButton, deleteButton, refreshButton, clearButton;
    private final Color accentColor = new Color(0, 120, 215);
    private int selectedRow = -1;

    public StaffManagementView() {
        staffController = new StaffController();
        initializeUI();
        loadStaffData();
    }

    private void initializeUI() {
        setTitle("Staff Management - Bike Showroom Management");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Top Panel - Title
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Staff Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(accentColor);
        titlePanel.add(title);
        titlePanel.setBackground(new Color(240, 240, 240));
        add(titlePanel, BorderLayout.NORTH);

        // Center Panel - Table
        String[] columns = {"ID", "Name", "Email", "Phone", "Position", "Department", "Salary", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        staffTable = new JTable(tableModel);
        staffTable.setRowHeight(25);
        staffTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        staffTable.setSelectionBackground(accentColor);
        staffTable.setSelectionForeground(Color.WHITE);
        staffTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = staffTable.getSelectedRow();
                if (selectedRow >= 0) {
                    populateFields();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(staffTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel - Form and Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 7, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Staff Information"));
        formPanel.setBackground(new Color(240, 240, 240));

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        positionField = new JTextField();
        departmentField = new JTextField();
        salaryField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel(""));

        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionField);
        formPanel.add(new JLabel("Department:"));
        formPanel.add(departmentField);
        formPanel.add(new JLabel("Salary:"));
        formPanel.add(salaryField);
        formPanel.add(new JLabel(""));

        bottomPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(240, 240, 240));

        addButton = createButton("Add");
        updateButton = createButton("Update");
        deleteButton = createButton("Delete");
        refreshButton = createButton("Refresh");
        clearButton = createButton("Clear");

        addButton.addActionListener(e -> addStaff());
        updateButton.addActionListener(e -> updateStaff());
        deleteButton.addActionListener(e -> deleteStaff());
        refreshButton.addActionListener(e -> loadStaffData());
        clearButton.addActionListener(e -> clearFields());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    private void loadStaffData() {
        tableModel.setRowCount(0);
        List<Staff> staffList = staffController.getAllStaff();
        for (Staff staff : staffList) {
            tableModel.addRow(new Object[]{
                    staff.getStaffId(),
                    staff.getName(),
                    staff.getEmail(),
                    staff.getPhone(),
                    staff.getPosition(),
                    staff.getDepartment(),
                    staff.getSalary(),
                    staff.getStatus()
            });
        }
    }

    private void populateFields() {
        if (selectedRow >= 0) {
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            emailField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            phoneField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            positionField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            departmentField.setText(tableModel.getValueAt(selectedRow, 5).toString());
            salaryField.setText(tableModel.getValueAt(selectedRow, 6).toString());
        }
    }

    private void addStaff() {
        if (!validateFields()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String position = positionField.getText().trim();
        String department = departmentField.getText().trim();
        String salary = salaryField.getText().trim();

        Staff staff = new Staff(name, email, phone, position, department);
        staff.setSalary(salary);
        staff.setJoiningDate(LocalDate.now().toString());

        if (staffController.addStaff(staff)) {
            JOptionPane.showMessageDialog(this, "Staff added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadStaffData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add staff", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStaff() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to update", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateFields()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = tableModel.getValueAt(selectedRow, 2).toString();
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String position = positionField.getText().trim();
        String department = departmentField.getText().trim();
        String salary = salaryField.getText().trim();

        Staff staff = new Staff(name, email, phone, position, department);
        staff.setSalary(salary);
        staff.setStatus("Active");

        if (staffController.updateStaff(email, staff)) {
            JOptionPane.showMessageDialog(this, "Staff updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadStaffData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update staff", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStaff() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to delete", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String email = tableModel.getValueAt(selectedRow, 2).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this staff member?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (staffController.deleteStaff(email)) {
                JOptionPane.showMessageDialog(this, "Staff deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStaffData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete staff", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        positionField.setText("");
        departmentField.setText("");
        salaryField.setText("");
        selectedRow = -1;
        staffTable.clearSelection();
    }

    private boolean validateFields() {
        return !nameField.getText().trim().isEmpty() &&
                !emailField.getText().trim().isEmpty() &&
                !phoneField.getText().trim().isEmpty() &&
                !positionField.getText().trim().isEmpty() &&
                !departmentField.getText().trim().isEmpty() &&
                !salaryField.getText().trim().isEmpty();
    }
}
