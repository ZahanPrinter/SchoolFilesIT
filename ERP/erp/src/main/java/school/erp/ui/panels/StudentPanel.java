package school.erp.ui.panels;


import school.erp.dao.StudentDAO;
import school.erp.models.Student;
import school.erp.ui.components.StyledButton;
import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class StudentPanel extends JPanel {
    private StudentDAO studentDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    
    public StudentPanel() {
        studentDAO = new StudentDAO();
        setLayout(new BorderLayout(20, 20));
        setBackground(UIConstants.SECONDARY);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.SECONDARY);
        
        JLabel header = new JLabel("Students");
        header.setFont(UIConstants.HEADER_FONT);
        header.setForeground(UIConstants.TEXT_DARK);
        
        StyledButton addBtn = new StyledButton("+ Add New", UIConstants.ACCENT);
        addBtn.addActionListener(e -> showAddDialog());
        
        headerPanel.add(header, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Name", "Roll No", "Class", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(UIConstants.TEXT_FONT);
        table.getTableHeader().setFont(UIConstants.BUTTON_FONT);
        table.getTableHeader().setBackground(UIConstants.PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(UIConstants.BORDER_COLOR));
        add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(UIConstants.SECONDARY);
        
        StyledButton refreshBtn = new StyledButton("Refresh", UIConstants.PRIMARY);
        refreshBtn.addActionListener(e -> loadData());
        
        StyledButton deleteBtn = new StyledButton("Delete", UIConstants.DANGER);
        deleteBtn.addActionListener(e -> deleteSelected());
        
        btnPanel.add(refreshBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);
        
        loadData();
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Student> students = studentDAO.getAllStudents();
            for (Student s : students) {
                tableModel.addRow(new Object[]{
                    s.getId(), s.getName(), s.getRollNo(), 
                    s.getClassName(), s.getEmail(), s.getPhone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                     "Add New Student", true);
        dialog.setSize(400, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextField nameField = new JTextField();
        JTextField rollNoField = new JTextField();
        JTextField classField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
        
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Roll No:"));
        formPanel.add(rollNoField);
        formPanel.add(new JLabel("Class:"));
        formPanel.add(classField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);
        
        StyledButton saveBtn = new StyledButton("Save", UIConstants.ACCENT);
        saveBtn.addActionListener(e -> {
            try {
                Student student = new Student();
                student.setName(nameField.getText());
                student.setRollNo(rollNoField.getText());
                student.setClassName(classField.getText());
                student.setEmail(emailField.getText());
                student.setPhone(phoneField.getText());
                student.setAddress(addressField.getText());
                
                studentDAO.addStudent(student);
                JOptionPane.showMessageDialog(dialog, "Student added successfully!");
                loadData();
                dialog.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveBtn);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this student?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    studentDAO.deleteStudent(id);
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                    loadData();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete");
        }
    }
}