/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyali;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author anas
 */
public class CharPics extends JPanel {

    BirdType[] choices;
    BufferedImage[] img;
    int currentImage = 0;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    CharPics(BirdType[] choices) {
        this.choices = choices;
        img = new BufferedImage[choices.length];
        for (int i = 0; i < choices.length; i++) {
            try {
                img[i] = ImageIO.read(new File("images/" + choices[i].toString() + ".jpg"));
            } catch (IOException ex) {
                try {
                    img[i] = ImageIO.read(new File("images/404image.jpg"));
                } catch (IOException ex1) {
                    Logger.getLogger(CharPics.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.setBackground(Color.BLACK);
        Dimension size = this.getSize();
        g2d.drawImage(img[currentImage], 0, 0,(int) size.getWidth(), (int) size.getHeight(), this);
    }

}
