package school.erp.ui.components;

import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class DarkTableHeaderRenderer extends DefaultTableCellRenderer {
    public DarkTableHeaderRenderer() {
        setHorizontalAlignment(JLabel.LEFT);
        setOpaque(true);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        setBackground(new Color(45, 45, 60));
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, UIConstants.BORDER_COLOR));
        
        return this;
    }
}