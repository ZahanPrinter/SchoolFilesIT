package school.erp.ui.components;

import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class StatCard extends JPanel {
    public StatCard(String title, int value, String icon) {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConstants.BORDER_COLOR, 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(UIConstants.ICON_FONT);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.TEXT_FONT);
        titleLabel.setForeground(UIConstants.TEXT_GRAY);
        
        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(UIConstants.STAT_VALUE_FONT);
        valueLabel.setForeground(UIConstants.TEXT_DARK);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(valueLabel);
        
        add(iconLabel, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
    }
}