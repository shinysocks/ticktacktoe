import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class End extends JPanel {
    Image image;

    public End(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
