package school.erp.ui.panels;

import school.erp.dao.CourseDAO;
import school.erp.models.Course;
import school.erp.ui.components.StyledButton;
import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CoursePanel extends JPanel {
    private CourseDAO courseDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    
    public CoursePanel() {
        courseDAO = new CourseDAO();
        setLayout(new BorderLayout(20, 20));
        setBackground(UIConstants.SECONDARY);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.SECONDARY);
        
        JLabel header = new JLabel("Courses");
        header.setFont(UIConstants.HEADER_FONT);
        header.setForeground(UIConstants.TEXT_DARK);
        
        StyledButton addBtn = new StyledButton("+ Add New", UIConstants.ACCENT);
        addBtn.addActionListener(e -> showAddDialog());
        
        headerPanel.add(header, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Course Name", "Course Code", "Teacher ID", "Credits"};
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
            List<Course> courses = courseDAO.getAllCourses();
            for (Course c : courses) {
                tableModel.addRow(new Object[]{
                    c.getId(), c.getCourseName(), c.getCourseCode(),
                    c.getTeacherId(), c.getCredits()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                                     "Add New Course", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextField nameField = new JTextField();
        JTextField codeField = new JTextField();
        JTextField teacherIdField = new JTextField();
        JTextField creditsField = new JTextField();
        
        formPanel.add(new JLabel("Course Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Course Code:"));
        formPanel.add(codeField);
        formPanel.add(new JLabel("Teacher ID:"));
        formPanel.add(teacherIdField);
        formPanel.add(new JLabel("Credits:"));
        formPanel.add(creditsField);
        
        StyledButton saveBtn = new StyledButton("Save", UIConstants.ACCENT);
        saveBtn.addActionListener(e -> {
            try {
                Course course = new Course();
                course.setCourseName(nameField.getText());
                course.setCourseCode(codeField.getText());
                course.setTeacherId(Integer.parseInt(teacherIdField.getText()));
                course.setCredits(Integer.parseInt(creditsField.getText()));
                
                courseDAO.addCourse(course);
                JOptionPane.showMessageDialog(dialog, "Course added successfully!");
                loadData();
                dialog.dispose();
            } catch (SQLException | NumberFormatException ex) {
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
                "Are you sure you want to delete this course?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    courseDAO.deleteCourse(id);
                    JOptionPane.showMessageDialog(this, "Course deleted successfully!");
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