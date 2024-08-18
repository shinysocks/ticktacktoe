import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Toe extends JButton {
    public int value = 0;

    public Toe(ActionListener l) {
        setSize(188, 188);
        setIcon(Main.tackAnim.get(0));
        setContentAreaFilled(false);
        setBorderPainted(false);
        addActionListener(l);
    }

    public void click() {
        if (value < 1) {
            if (Main.turn % 2 == 0) {
                animate(Main.tickAnim);
                value += 1;
            } else {
                animate(Main.tackAnim);
                value += 2;
            }
        }
    }

    public void animate(ArrayList<ImageIcon> list) {
        for (ImageIcon frame : list) {
            setIcon(frame);
            update(getGraphics());

            try {
                Thread.sleep(35);
            } catch (Exception e) {}
        }
        Main.turn++;
    }

    public String toString() {
        return "Toe";
    }
}
