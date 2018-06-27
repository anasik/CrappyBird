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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author anas
 */
public class Game extends JPanel implements Runnable {
    
    private ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    };
    BufferedImage sprite;
    Timer repainter = new Timer(10, listener);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    boolean stopped = false;
    boolean paused = false;
    boolean sound;
    boolean spriteloaded = false;
    Player player;
    final int MIN_H = (int) (screenSize.getHeight() * (5 / 450.0));
    final int MAX_H = (int) (screenSize.getHeight() * (350 / 450.0));
    final int MIN_D = (int) (screenSize.getHeight() * (100 / 450.0));
    final int MAX_D = (int) (screenSize.getHeight() * (150 / 450.0));
    final int PWIDTH = (int) (screenSize.getWidth() * (30 / 250.0));
    final int PSEPARATION = (int) (screenSize.getWidth());
    final int SHEIGHT = (int) screenSize.getHeight();
    final int PLAYER_SIZE = (int) (screenSize.getHeight() * (40.0 / 450));
    int x = (int) (screenSize.getWidth() * (50 / 250.0));
    int y = (int) (screenSize.getHeight() * (100 / 450.0));
    int x11 = (int) (screenSize.getWidth() * (300 / 250.0));
//    int x12 = (int)(x11+PWIDTH+PSEPARATION)
    int h11 = (int) (MIN_H + Math.random() * (MAX_H - MIN_H));
    int h12 = (int) (MIN_H + Math.random() * (MAX_H - MIN_H));
    int diff = (int) (MIN_D + Math.random() * (MAX_D - MIN_D));
    int diff2 = (int) (MIN_D + Math.random() * (MAX_D - MIN_D));
    double score = 0;
    final String HITSOUND = "sounds/sfx_hit.wav";
    final String FLAPSOUND = "sounds/sfx_wing.wav";
    final String POINTSOUND = "sounds/sfx_point.wav";
    final String DIESOUND = "sounds/sfx_die.wav";
    final String BGSOUND = "sounds/bg.wav";
    AudioInputStream HITSTREAM;
    AudioInputStream FLAPSTREAM;
    AudioInputStream DIESTREAM;
    AudioInputStream POINTSTREAM;
    AudioInputStream BGSTREAM;
    Clip hitClip;
    Clip flapClip;
    Clip dieClip;
    Clip pointClip;
    Clip bgClip;
    
    JLabel scorelabel = new JLabel("Score: " + 0);
    JLabel message = new JLabel("Game Over!");
    
    {
//        this.setLayout(new GridBagLayout());
        //        GridBagConstraints c = new GridBagConstraints();
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 0.0;
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.anchor = GridBagConstraints.PAGE_START;                
//        this.add(message, c);
        message.setHorizontalAlignment(JLabel.CENTER);
        message.setFont(new Font("Arial", Font.PLAIN, 20));
        message.setVisible(false);
        message.setOpaque(true);
        message.setBackground(new Color(255, 255, 255, 255));
        scorelabel.setOpaque(true);
        scorelabel.setHorizontalAlignment(JLabel.CENTER);
//        this.add(scorelabel);
        scorelabel.setBackground(new Color(255, 255, 255, 255));
        
        try {
            HITSTREAM = AudioSystem.getAudioInputStream(new File(HITSOUND).toURI().toURL());
            FLAPSTREAM = AudioSystem.getAudioInputStream(new File(FLAPSOUND).toURI().toURL());
            DIESTREAM = AudioSystem.getAudioInputStream(new File(DIESOUND).toURI().toURL());
            POINTSTREAM = AudioSystem.getAudioInputStream(new File(POINTSOUND).toURI().toURL());
//            BGSTREAM = AudioSystem.getAudioInputStream(new File(BGSOUND).toURI().toURL());
            hitClip = AudioSystem.getClip();
            flapClip = AudioSystem.getClip();
            dieClip = AudioSystem.getClip();
            pointClip = AudioSystem.getClip();
//            bgClip = AudioSystem.getClip();
//            bgClip.loop(Clip.LOOP_CONTINUOUSLY);
            hitClip.open(HITSTREAM);
            flapClip.open(FLAPSTREAM);
            dieClip.open(DIESTREAM);
            pointClip.open(POINTSTREAM);
//            bgClip.open(BGSTREAM);
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to load sounds.");
        } catch (IOException ex) {
            System.out.println("Failed to load sounds.");
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void loadImage() throws IOException {
        File f = new File("images/sprites/" + player.btype.toString() + "-sprite.png");
        sprite = ImageIO.read(f);
    }

    void pause() {
        paused = !paused;
    }
    
    void playFlapSound() {
        flapClip.setFramePosition(0);
        flapClip.start();
    }
    
    void playDieSound() {
        dieClip.setFramePosition(0);
        dieClip.start();
    }
    
    void playHitSound() {
        hitClip.setFramePosition(0);
        hitClip.start();
    }
    
    void playSound(String soundFile) throws MalformedURLException, UnsupportedAudioFileException, IOException {
        File f = new File("./" + soundFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (LineUnavailableException ex) {
            
        }
        
    }
    
    public void moveBall(int x) {
        y = y + x;
    }
    
    public void jump() {
        moveBall(-40);
        playFlapSound();
    }
    
    public void moveObstacle(int speed) {
        if (x11 + PSEPARATION + (PWIDTH) == 0) {
            x11 = PSEPARATION;
            h11 = (int) (MIN_H + Math.random() * (MAX_H - MIN_H));
            h12 = (int) (MIN_H + Math.random() * (MAX_H - MIN_H));
            diff = (int) (MIN_D + Math.random() * (MAX_D - MIN_D));
            diff2 = (int) (MIN_D + Math.random() * (MAX_D - MIN_D));
        } else {
            x11 = x11 - speed;
        }
    }
    
    public boolean collision() {
        boolean touching1 = (x + PLAYER_SIZE > x11 && x < x11 + PWIDTH) && (y < h11);
        boolean touching2 = (x + PLAYER_SIZE > x11 && x < x11 + PWIDTH) && (y + PLAYER_SIZE > h11 + diff);
        boolean touching3 = (x + PLAYER_SIZE > x11 + PSEPARATION && x < x11 + PSEPARATION + PWIDTH) && (y < h12);
        boolean touching4 = (x + PLAYER_SIZE > x11 + PSEPARATION && x < x11 + PSEPARATION + PWIDTH) && (y + PLAYER_SIZE > h12 + diff2);
        if (touching1 || touching2 || touching3 || touching4) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
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
        if (spriteloaded) {
            g2d.drawImage(sprite, x, y, PLAYER_SIZE, PLAYER_SIZE, this);
        } else {
            g2d.fillOval(x, y, PLAYER_SIZE, PLAYER_SIZE);
        }
        this.setBackground(Color.CYAN);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x11, 0, PWIDTH, h11);
        g2d.fillRect(x11, h11 + diff, PWIDTH, SHEIGHT - h11 - diff);
        g2d.fillRect(x11 + PSEPARATION, 0, PWIDTH, h12);
        g2d.fillRect(x11 + PSEPARATION, h12 + diff2, PWIDTH, SHEIGHT - h12 - diff2);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.setColor(Color.BLACK);
    }
    
    void play() {
        moveBall(1);
        moveObstacle(1); // In progress
        score += 0.01;
        scorelabel.setText("Score: " + (int) score);
        if (y >= SHEIGHT || collision()) {
            stopped = true;
            playHitSound();
        }
    }
    
    @Override
    public void run() {
        try {
            loadImage();
            spriteloaded = true;
        } catch (IOException ex) {
            spriteloaded = false;
        }
        repainter.setRepeats(true);
        repainter.start();
//        if (sound) {
//            bgClip.setFramePosition(0);
//            bgClip.start();
//        }
        while (!stopped) {
            if (!paused) {
                play();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(CrappyBird.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        message.setVisible(true);
//        if (sound) {
//            bgClip.setFramePosition(0);
//            bgClip.stop();
//        }
        Score.updateFile(player, (int) score);
    }
}

class Sam extends Game {
    
    Sam() {
        super();
        message.setText("<html><div style='text-align:center'>" + jokes[(int) (Math.random() * jokes.length)] + "</div></html>");
    }
    String[] jokes = {
        "Zoo Wee Mama.",
        "Goku is faster than the flash.",
        "I am the coolest person.",
        "Bugatti Veyron is the best car in the world.",
        "I am humorous. If you don't find me funny then that's your problem",
        "The best thing about good old days is that we were neither good nor old.",
        "I don't get it.",
        "What did one planet say to the other?\n"
        + "\n"
        + "Itna mercury ho?",
        "If my father was a baker, I'd call him Abubakar.",
        "What did the accountant say when he wanted the mango at the dinner table?\n"
        + "Keri Forward.",
        "How to annoy artists starter pack:\n"
        + "\"Can you draw me?\"",};
}

class DeanLister extends Game {
    
    {
        score = 4.0;
    }
}

class Hammad extends Game {

//    boolean crossed1 = false;
//    boolean crossed2 = false;
//    boolean crossed3 = false;
//    boolean crossed4 = false;
//    boolean throughFirstset = false;
//    boolean throughSecondSet = false;
    boolean inGoldilocksZone = false;
    
    @Override
    public void moveObstacle(int speed) {
        super.moveObstacle(speed);
        if (x11 == x) {
//            crossed1 = crossed2 = false;
            inGoldilocksZone = false;
        }
        if (x11 + PSEPARATION == x) {
//            crossed3 = false;
//            crossed4 = false;
            inGoldilocksZone = false;
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.red);
//        g2d.fillRect(0, (int) (Math.random() * h11), x11, 2);
//        g2d.fillRect(0, (h11 + diff) + (int) (Math.random() * (SHEIGHT - h11 - diff)), x11, 2);
//        g2d.fillRect(x11 + PWIDTH, (int) (Math.random() * h12), 220, 2);
//        g2d.fillRect(x11 + PWIDTH, (h12 + diff2) + (int) (Math.random() * (SHEIGHT - h12 - diff2)), 220, 2);

        g2d.fillRect(0, h11, x11, 1);
        g2d.fillRect(0, (h11 + diff), x11, 1);
        g2d.fillRect(x11 + PWIDTH, h12, PSEPARATION, 1);
        g2d.fillRect(x11 + PWIDTH, (h12 + diff2), PSEPARATION, 1);
    }
    
    @Override
    public boolean collision() {
        boolean collision = super.collision();
//        return collision || (crossed1 && y < h11 && x < x11 + PWIDTH) || (crossed2 && y + PLAYER_SIZE > h11 + diff && x < x11 + PWIDTH) || (crossed3 && y < h12 && x < x11 + PSEPARATION + 2 * PWIDTH) || (crossed4 && y + 30 > h12 + diff2 && x < x11 + PSEPARATION + 2 * PWIDTH);
        return collision || crossing();
    }
    
    @Override
    void play() {
        super.play();
        if (!inGoldilocksZone) {
            if (x < x11) {
                if (y > h11 && y + PLAYER_SIZE < h11 + diff) {
                    inGoldilocksZone = true;
                }
            } else if (x > x11 + PWIDTH && x < x11 + PSEPARATION) {
                if (y > h12 && y + PLAYER_SIZE < h12 + diff2) {
                    inGoldilocksZone = true;
                }
            }
        }
    }
    
    boolean crossing() {
        if (inGoldilocksZone) //        checkLines();
        {
            if (x < x11) {
                if (y < h11 || y + PLAYER_SIZE > h11 + diff) {
                    playDieSound();
                    return true;
                }
            } else if (x > x11 + PWIDTH) {
                if (y < h12 || y + PLAYER_SIZE > h12 + diff2) {
                    playDieSound();
                    return true;
                }
            }
            
        }
        return false;
    }
}

//    void checkLines() {
//        if (y + PLAYER_SIZE > h11 && x < x11 + PWIDTH) {
//            crossed1 = true;
//        }
//        if (y < h11 + diff && x < x11 + PWIDTH) {
//            crossed2 = true;
//        }
//        if (y + PLAYER_SIZE > h12 && x < x11 + PSEPARATION + 2 * PWIDTH) {
//            crossed3 = true;
//        }
//        if (y < h12 + diff2 && x < x11 + PSEPARATION + 2 * PWIDTH) {
//            crossed4 = true;
//        }
//    }
//}
class BurkaAvenger extends Game {
    
    void play() {
        moveBall(-1);
        moveObstacle(1); // In progress
        score += 0.01;
        scorelabel.setText("Score: " + (int) score);
        if (y <= 0 || collision()) {
            stopped = true;
            playHitSound();
        }
    }
    
    public void jump() {
        moveBall(40);
        playFlapSound();
    }
}

class Dev extends Game {
    
    void play() {
//        moveBall(-1);
//        moveObstacle(1); // In progress
        score += 0.01;
        scorelabel.setText("Score: " + (int) score);
        if (y >= SHEIGHT || collision()) {
            stopped = true;
            playHitSound();
        }
    }
}
