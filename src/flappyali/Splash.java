/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyali;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author anas
 */
public class Splash extends JPanel {

    int textCounter = 0;
    int alpha = 255;
    boolean increasing = true;
    Color transWhite = new Color(255, 255, 255, alpha);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    Splash() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.anchor = GridBagConstraints.PAGE_START;                
        this.add(label, c);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setForeground(transWhite);
        
        ActionListener alphaListener = (ActionEvent e) -> {
            changeAlpha();
            transWhite = new Color(255, 255, 255, alpha);
            label.setForeground(transWhite);
//            Splash.this.repaint();
            if (textCounter == 2 && alpha == 255) {
                ((Timer) e.getSource()).stop();
            }
        };
        Timer talpha = new Timer(10, alphaListener);
        talpha.setRepeats(true);
        talpha.start();
        ActionListener listener = (ActionEvent e) -> {
            changeText(textCounter++);
            label.setText(text);
            resetAlpha();
//            Splash.this.repaint();
            if (textCounter == 2) {
                ((Timer) e.getSource()).stop();
            }
        };
        Timer t = new Timer(5000, listener);
        t.setRepeats(true);
        t.start();
    }
    String text = "<html><div style='text-align:center'>The characters in this game "
            + "are entirely fictitious. <br>"
            + "Any resemblance to anyone "
            + "dead or alive, <br>"
            + "is purely coincidental.</div></html>";
    JLabel label = new JLabel(text);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

//        BufferedImage img;
//        try {
//            img = ImageIO.read(new File("fa.png")).getSubimage(0, 0, 299, 440);
//            //img = ImageIO.read(new File("Background.png")).getSubimage(0, 0, 299,168);
//            g2d.drawImage(img, 0, 0, this);
//        } catch (IOException ex) {
//            //Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
//        }
        this.setBackground(Color.BLACK);
//        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
//        g2d.setColor(transWhite);
//        g2d.drawString(text, 20, 200);
        // Wanna add a transition to this shit
    }

    void changeAlpha() {
//        if (alpha == 255 || alpha == 0) {
//            increasing = !increasing;
//        }
        if (alpha < 255) {
            alpha++;
        }
//        } else {
//            alpha--;
//        }

    }

    void changeText(int x) {
        switch (x) {
            case 0:
                text = "Anas Ismail Khan Presents";
                break;
            case 1:
                text = "Crappy Bird";
                break;
        }
    }

    void resetAlpha() {
        alpha = 0;
    }
}
