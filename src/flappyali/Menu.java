/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyali;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Timer;

/**
 *
 * @author anas
 */
public class Menu extends JPanel {

    JLabel label = new JLabel("Choose your bird.");
    JLabel choiceText = new JLabel("Ali");
    JButton previous = new JButton("<");
    JButton select = new JButton("Select");
    JButton next = new JButton(">");
    JToggleButton soundToggle = new JToggleButton("🔊");
    String name = "Ali";
    boolean sound = true;
    GridBagConstraints c = new GridBagConstraints();
    ActionListener selectListener;
    ActionListener nextChListener = (e) -> {
        nextCh();
    };
    ActionListener prevChListener = (e) -> {
        prevCh();
    };
    ActionListener soundTListener = (e) -> {
        toggleSound();
    };
    BirdType[] choices = {BirdType.Senpai, BirdType.TopsyTurvy, BirdType.Headstart, BirdType.Freeze, BirdType.Dodger, BirdType.Confucius};
    int choice = 0;
    CharPics picture = new CharPics(choices);
    
    Menu() {
        this.setVisible(true);
        this.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.anchor = GridBagConstraints.PAGE_START;                
        this.add(label, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        this.add(choiceText, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx=1;
        c.weighty=1;
        this.add(picture,c);
        picture.validate();
        
        
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0.5;
        c.gridwidth = 1;
        previous.addActionListener(prevChListener);
        this.add(previous, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 1;
        c.gridwidth = 1;
        this.add(select, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        c.weightx = 0.5;
        next.addActionListener(nextChListener);
        this.add(next, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0.0;
        soundToggle.addActionListener(soundTListener);
        this.add(soundToggle, c);
        

    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        frame.pack();
        frame.setSize(250, 450);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Menu menu = new Menu();
        frame.add(menu);      
        menu.revalidate();
    }

    private void nextCh() {
        if(choice < choices.length-1)
            choice++;
        else
            choice=0;
        picture.currentImage=choice;
        picture.repaint();
        choiceText.setText(choices[choice].toString()); 
    }

    private void prevCh() {
        if(choice > 0)
            choice--;
        else
            choice=choices.length-1;
        picture.currentImage=choice;
        picture.repaint();
        choiceText.setText(choices[choice].toString()); 
    }

    private void toggleSound() {
        sound = !sound;
    }
}
