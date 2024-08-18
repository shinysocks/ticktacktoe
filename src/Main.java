import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame implements ActionListener {
	JPanel panel;
    JFrame frame;
    ImageIcon titleIcon;
    Image tickEndImg;
    Image tackEndImg;
    Image toeEndImg;
    Overlay titleOverlay;
    Overlay endOverlay;
    Toe[] toes;
    public static ArrayList<ImageIcon> tickAnim = new ArrayList<ImageIcon>();
    public static ArrayList<ImageIcon> tackAnim = new ArrayList<ImageIcon>();
    public static int turn;

	public Main() {
		// import assets 
		try {
            titleIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/assets/title.jpg")));
            tickEndImg = ImageIO.read(this.getClass().getResourceAsStream("/assets/tick_end.jpg"));
            tackEndImg = ImageIO.read(this.getClass().getResourceAsStream("/assets/tack_end.jpg"));
            toeEndImg = ImageIO.read(this.getClass().getResourceAsStream("/assets/tie.jpg"));

            tickAnim = sort("assets/tick/toe");
            tackAnim = sort("assets/tack/toe");            

        } catch (IOException e) {}

        // overlay for title and end scene
        titleOverlay = new Overlay(this, titleIcon);

        // make jpanel
        panel = new JPanel();
        panel.setSize(500, 500);
        GridLayout grid = new GridLayout(3, 3);
        panel.setBackground(Color.BLACK);
        panel.setLayout(grid);

		// configure jframe
		setTitle("ticktacktoe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
        setPreferredSize(new Dimension(500, 500));
		
        toes = new Toe[9];
        for (int i = 0; i < toes.length; i++) {
            toes[i] = new Toe(this);
        }

        for (Toe toe : toes) {
            panel.add(toe);
        }

        add(titleOverlay);
        grid.setVgap(15);
        grid.setHgap(15);
		pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().toString() == "Overlay") {
                remove(titleOverlay);
                add(panel);
                pack();
                repaint();
            } else {
                ((Toe) e.getSource()).click();
            }
            
            if (turn >= 9 && !check()) {
                end(toeEndImg);
            } else if (check()) {
                if (turn % 2 == 0) {
                    end(tackEndImg);
                } else {
                    end(tickEndImg);
                }
            }

        } catch (Exception except) {}
    }

    ArrayList<ImageIcon> sort(String path) throws IOException {
        int index = 0;
        ArrayList<ImageIcon> list = new ArrayList<ImageIcon>();
        CodeSource src = Main.class.getProtectionDomain().getCodeSource();
        URL jar = src.getLocation();
        ZipInputStream zip = new ZipInputStream(jar.openStream());
        while (index < 19) {
            ZipEntry e = zip.getNextEntry();
            if (e == null) {
                jar = src.getLocation();
                zip = new ZipInputStream(jar.openStream());
                e = zip.getNextEntry();
            }
            
            String name = e.getName();

            if (name.startsWith(path)) {
                if (name.split(path)[1].split(".png")[0].equals(Integer.toString(index))) {
                    list.add(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream(name))));
                    index++;
                }
            }
        }
        return list;
    }

    public void end(Image image) throws InterruptedException {
        Thread.sleep(325);
        remove(panel);
        End end = new End(image);
        add(end);
        pack();
        repaint();
    }

    boolean check() {
        // build string of rows
        String sHoz = "";
        for (int i = 0; i < toes.length; i++) {
            sHoz += String.valueOf(toes[i].value);
        }

        // build string of columns
        String sVer = "";
        for (int i = 0; i < 3; i++) {
            for (int j = i; j < 9; j+=3) {
                sVer += String.valueOf(toes[j].value);
            }
        }

        // check if rows or colums contain win conditions
        if (checker(sVer)) {
            return true;
        } if (checker(sHoz)) {
            return true;
        }

        // check across
        String sX = sHoz.substring(0, 1) + sHoz.substring(4, 5) + sHoz.substring(8, 9);

        if (sX.equals("111") || sX.equals("222")) {
            return true;
        }

        sX = sHoz.substring(2, 3) + sHoz.substring(4, 5) + sHoz.substring(6, 7);

        if (sX.equals("111") || sX.equals("222")) {
            return true;
        }
        
        return false;
    }

    boolean checker(String s) {
        for (int i = 0; i < 9; i+=3) {
            if (s.substring(i, i+3).equals("111") || s.substring(i, i+3).equals("222")) {
                return true;
            }
        }
        return false;
    }

	public static void main(String[] args) {
		new Main();
	}
}
