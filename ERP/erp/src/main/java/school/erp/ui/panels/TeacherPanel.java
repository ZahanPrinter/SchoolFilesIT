package school.erp.ui.panels;

import school.erp.dao.TeacherDAO;
import school.erp.models.Teacher;
import school.erp.ui.components.StyledButton;
import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TeacherPanel extends JPanel {
    private TeacherDAO teacherDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    
    public TeacherPanel() {
        teacherDAO = new TeacherDAO();
        setLayout(new BorderLayout(20, 20));
        setBackground(UIConstants.SECONDARY);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.SECONDARY);
        
        JLabel header = new JLabel("Teachers");
        header.setFont(UIConstants.HEADER_FONT);
        header.setForeground(UIConstants.TEXT_DARK);
        
        StyledButton addBtn = new StyledButton("+ Add New", UIConstants.ACCENT);
        addBtn.addActionListener(e -> showAddDialog());
        
        headerPanel.add(header, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Name", "Employee ID", "Subject", "Email", "Phone"};
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
            List<Teacher> teachers = teacherDAO.getAllTeachers();
            for (Teacher t : teachers) {
                tableModel.addRow(new Object[]{
                    t.getId(), t.getName(), t.getEmployeeId(),
                    t.getSubject(), t.getEmail(), t.getPhone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                                     "Add New Teacher", true);
        dialog.setSize(400, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextField nameField = new JTextField();
        JTextField empIdField = new JTextField();
        JTextField subjectField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField qualField = new JTextField();
        
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Employee ID:"));
        formPanel.add(empIdField);
        formPanel.add(new JLabel("Subject:"));
        formPanel.add(subjectField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Qualification:"));
        formPanel.add(qualField);
        
        StyledButton saveBtn = new StyledButton("Save", UIConstants.ACCENT);
        saveBtn.addActionListener(e -> {
            try {
                Teacher teacher = new Teacher();
                teacher.setName(nameField.getText());
                teacher.setEmployeeId(empIdField.getText());
                teacher.setSubject(subjectField.getText());
                teacher.setEmail(emailField.getText());
                teacher.setPhone(phoneField.getText());
                teacher.setQualification(qualField.getText());
                
                teacherDAO.addTeacher(teacher);
                JOptionPane.showMessageDialog(dialog, "Teacher added successfully!");
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
                "Are you sure you want to delete this teacher?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    teacherDAO.deleteTeacher(id);
                    JOptionPane.showMessageDialog(this, "Teacher deleted successfully!");
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