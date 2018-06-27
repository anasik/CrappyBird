/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyali;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.image.BufferedImage;

/**
 *
 * @author ali
 */
public class CrappyBird {

    /**
     * @param args the command line arguments
     */
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static ActionListener getMenuListener;
    static JFrame frame;
    static Menu menu;
    static Game game;
    static Player player;
    static Splash splash;
    static Thread gameThread;
    static JPanel glass;
    static boolean skipped = false;
    

    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
        frame = buildFrame();

        splash = new Splash();
        frame.add(splash);
        splash.revalidate();
        getMenuListener = (ActionEvent e) -> {
            if (!skipped) {
                createMenu();
            }
        };
        Timer tmer = new Timer(15000, getMenuListener);
        tmer.setRepeats(false);
        tmer.start();

    }

    static JFrame buildFrame() {
        JFrame frame = new JFrame("Flappy Ali");
        frame.setSize(screenSize);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        menuBar.add(menu);
        JMenuItem MainMenu = new JMenuItem("Main menu",
                KeyEvent.VK_M);
        MainMenu.getAccessibleContext().setAccessibleDescription(
                "Go back to the player selection screen");
        menu.add(MainMenu);

        JMenuItem DispScore = new JMenuItem("View Scores",
                KeyEvent.VK_M);
        MainMenu.getAccessibleContext().setAccessibleDescription(
                "Pretty self-explanatory.");
        menu.add(DispScore);
        frame.setJMenuBar(menuBar);
        DispScore.addActionListener((ActionEvent e) -> {
            Score.display();
        });
        MainMenu.addActionListener((ActionEvent e) -> {
            if (game != null) {
                if (!game.stopped) {
                    game.stopped = true;
                }
            }
            createMenu();
            menu.revalidate();
        });
        return frame;
    }

    static void createMenu() {
        if (!skipped) {
            skipped = true;
        }
        menu = new Menu();
        menu.selectListener = (e) -> {
            player = new Player(menu.choices[menu.choice]);
            game = createGame();
            frame.setSize(screenSize);
            game.sound = menu.sound;
            gameThread = new Thread(game);
            gameThread.start();
        };
        menu.select.addActionListener(menu.selectListener);
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(menu);
        frame.setSize(350, 500);
        menu.revalidate();
        splash = null;
    }

    static Game createGame() {
        Game game;
        switch (player.btype) {
            case Confucius:
                game = new Sam();
                break;
            case Headstart:
                game = new DeanLister();
                break;
            case TopsyTurvy:
                game = new BurkaAvenger();
                break;
            case Dodger:
                game = new Hammad();
                break;
            case Freeze:
                game = new Dev();
                break;
            default:
                game = new Game();
                break;

        }
        frame.getContentPane().removeAll();
        frame.setLayout(new GridBagLayout());
//        glass = new JPanel();
//        glass.add(game.message);glass.setLayout(new GridBagLayout());
//        glass.setVisible(true);
//        frame.setGlassPane(glass);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;

        frame.add(game.scorelabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;

        frame.add(game.message, c);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        frame.add(game, c);
        game.validate();
        KeyListener l;
        if (player.btype == BirdType.Freeze) {
            l = new DControls(game, frame, player);
        } else {
            l = new Controls(game, frame, player);
        }
        game.addKeyListener(l);
        game.grabFocus();
        game.player = player;
        return game;
    }

    static void resetGame() {
        boolean sound = game.sound;
        game = createGame();
        game.sound = sound;
        frame.getContentPane().removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.anchor = GridBagConstraints.PAGE_START;                
        frame.add(game.scorelabel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;

        frame.add(game.message, c);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        frame.add(game, c);
        game.validate();
        (new Thread(game)).start();
    }
}

/*
Players?
Senpai - Normal as can be.
Confucius - Words of wisdom appears on the screen on his death.
Headstart - Get a 4.0 point headstart everytime you play as her.
Dodger - Red lines predicting safe zones for all pipes. (You can cross them only once for each pipe.)
TopsyTurvy - Inverted game. 
Freeze - Total control over vertical position. Everything moves when he does. 
 */
