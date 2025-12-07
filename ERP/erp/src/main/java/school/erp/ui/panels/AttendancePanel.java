package school.erp.ui.panels;

import school.erp.dao.StudentDAO;
import school.erp.dao.AttendanceDAO;
import school.erp.models.Student;
import school.erp.models.Attendance;
import school.erp.ui.components.DarkTableHeaderRenderer;
import school.erp.ui.components.StyledButton;
import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AttendancePanel extends JPanel {
    private AttendanceDAO attendanceDAO;
    private StudentDAO studentDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> studentCombo;
    
    public AttendancePanel() {
        attendanceDAO = new AttendanceDAO();
        studentDAO = new StudentDAO();
        
        setLayout(new BorderLayout(20, 20));
        setBackground(UIConstants.SECONDARY);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.SECONDARY);
        
        JLabel header = new JLabel("Attendance Management");
        header.setFont(UIConstants.HEADER_FONT);
        header.setForeground(UIConstants.TEXT_DARK);
        
        StyledButton markBtn = new StyledButton("Mark Attendance", UIConstants.SUCCESS);
        markBtn.addActionListener(e -> showMarkAttendanceDialog());
        
        headerPanel.add(header, BorderLayout.WEST);
        headerPanel.add(markBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Student ID", "Student Name", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(UIConstants.TEXT_FONT);
        table.setBackground(UIConstants.CARD_BG);
        table.setForeground(UIConstants.TEXT_DARK);
        table.setGridColor(UIConstants.BORDER_COLOR);
        table.setSelectionBackground(UIConstants.TABLE_SELECTION);
        table.setSelectionForeground(Color.WHITE);
        
        // Fix table header with custom renderer
        table.getTableHeader().setDefaultRenderer(new DarkTableHeaderRenderer());
        table.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(UIConstants.BORDER_COLOR));
        scrollPane.getViewport().setBackground(UIConstants.CARD_BG);
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(UIConstants.SECONDARY);
        
        StyledButton todayBtn = new StyledButton("Today's Attendance", UIConstants.ACCENT);
        todayBtn.addActionListener(e -> loadTodayAttendance());
        
        StyledButton allBtn = new StyledButton("All Records", UIConstants.ACCENT);
        allBtn.addActionListener(e -> loadAllAttendance());
        
        StyledButton deleteBtn = new StyledButton("Delete", UIConstants.DANGER);
        deleteBtn.addActionListener(e -> deleteSelected());
        
        btnPanel.add(todayBtn);
        btnPanel.add(allBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);
        
        loadTodayAttendance();
    }
    
    private void loadTodayAttendance() {
        tableModel.setRowCount(0);
        try {
            List<Attendance> records = attendanceDAO.getTodayAttendance();
            for (Attendance a : records) {
                tableModel.addRow(new Object[]{
                    a.getId(), a.getStudentId(), a.getStudentName(),
                    a.getDate(), a.getStatus()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void loadAllAttendance() {
        tableModel.setRowCount(0);
        try {
            List<Attendance> records = attendanceDAO.getAllAttendance();
            for (Attendance a : records) {
                tableModel.addRow(new Object[]{
                    a.getId(), a.getStudentId(), a.getStudentName(),
                    a.getDate(), a.getStatus()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void showMarkAttendanceDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                                     "Mark Attendance", true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(UIConstants.SECONDARY);
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(UIConstants.SECONDARY);
        
        // Load students
        studentCombo = new JComboBox<>();
        studentCombo.setBackground(UIConstants.CARD_BG);
        studentCombo.setForeground(UIConstants.TEXT_DARK);
        try {
            List<Student> students = studentDAO.getAllStudents();
            for (Student s : students) {
                studentCombo.addItem(s.getId() + " - " + s.getName());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dialog, "Error loading students: " + e.getMessage());
        }
        
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Present", "Absent", "Late"});
        statusCombo.setBackground(UIConstants.CARD_BG);
        statusCombo.setForeground(UIConstants.TEXT_DARK);
        
        JTextField dateField = createStyledTextField();
        dateField.setText(LocalDate.now().toString());
        
        formPanel.add(createLabel("Student:"));
        formPanel.add(studentCombo);
        formPanel.add(createLabel("Status:"));
        formPanel.add(statusCombo);
        formPanel.add(createLabel("Date (YYYY-MM-DD):"));
        formPanel.add(dateField);
        
        StyledButton saveBtn = new StyledButton("Mark Attendance", UIConstants.SUCCESS);
        saveBtn.addActionListener(e -> {
            try {
                String selected = (String) studentCombo.getSelectedItem();
                if (selected == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select a student");
                    return;
                }
                
                int studentId = Integer.parseInt(selected.split(" - ")[0]);
                String status = (String) statusCombo.getSelectedItem();
                Date date = Date.valueOf(dateField.getText());
                
                Attendance attendance = new Attendance();
                attendance.setStudentId(studentId);
                attendance.setStatus(status);
                attendance.setDate(date);
                
                attendanceDAO.markAttendance(attendance);
                JOptionPane.showMessageDialog(dialog, "Attendance marked successfully!");
                loadTodayAttendance();
                dialog.dispose();
            } catch (SQLException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(UIConstants.SECONDARY);
        btnPanel.add(saveBtn);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIConstants.TEXT_DARK);
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(UIConstants.CARD_BG);
        field.setForeground(UIConstants.TEXT_DARK);
        field.setCaretColor(UIConstants.TEXT_DARK);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConstants.BORDER_COLOR),
            new EmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }
    
    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this attendance record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    attendanceDAO.deleteAttendance(id);
                    JOptionPane.showMessageDialog(this, "Attendance record deleted!");
                    loadTodayAttendance();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete");
        }
    }
}