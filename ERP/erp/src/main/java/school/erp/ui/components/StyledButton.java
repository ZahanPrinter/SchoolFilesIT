package school.erp.ui.components;

import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StyledButton extends JButton {
    private Color originalColor;
    
    public StyledButton(String text, Color bgColor) {
        super(text);
        this.originalColor = bgColor;
        setFont(UIConstants.BUTTON_FONT);
        setForeground(Color.WHITE);
        setBackground(bgColor);
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                setBackground(originalColor);
            }
        });
    }
}