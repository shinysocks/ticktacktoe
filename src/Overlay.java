import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Overlay extends JButton {
    public Overlay(ActionListener l, ImageIcon icon) {
        addActionListener(l);
        setSize(750, 750);
        setIcon(icon);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    public String toString() {
        return "Overlay";
    }
}
