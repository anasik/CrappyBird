/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyali;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author anas
 */
public class Score {

    static File file = new File("scores");
    static BufferedWriter writer;
    static Scanner input;
    static JFrame frame;
    static JTextArea text;
    static JScrollPane sp;

    static void updateFile(Player player, int score) {

        try {
            if (file.exists()) {
                writer = new BufferedWriter(new FileWriter(file, true));
            } else {
                writer = new BufferedWriter(new FileWriter(file));
            }
            writer.write(player.btype.toString() + "\t" + score + "\n");
            writer.close();
        } catch (IOException ex) {
            System.out.println("Unable to upon scores file.");
        }
    }

    static void display() {
        String data = "";
        try {
            input = new Scanner(file);
            while (input.hasNext()) {
                data += input.nextLine() + "\n";
            }
            frame = new JFrame();
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setSize(200, 200);
            text = new JTextArea();
            text.setForeground(Color.DARK_GRAY);
            sp = new JScrollPane(text);
            text.setFont(new Font("Arial", Font.PLAIN, 20));
            text.setOpaque(true);
            text.setVisible(true);
//            text.setEnabled(false);
            text.setSize(200, 200);
            text.setText(data);
            sp.setVisible(true);
            frame.add(sp);
            frame.validate();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void main(String[] args){
        display();
    }
}
