/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappyali;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author anas
 */
public class Controls implements KeyListener {

    Game game;
    JFrame frame;
    Player player;

    Controls(Game game, JFrame frame, Player player) {
        this.game = game;
        this.frame = frame;
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F) {
            if (!game.stopped) {
                game.jump();
            } else {
                CrappyBird.resetGame();
                frame.removeKeyListener(this);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            game.pause();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}

class DControls extends Controls {

    public DControls(Game game, JFrame frame, Player player) {
        super(game, frame, player);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!game.stopped) {
            if (e.getKeyCode()==KeyEvent.VK_UP) {
                game.moveBall(-2);
                game.moveObstacle(2);
            } else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
                game.moveBall(2);
                game.moveObstacle(2);
            }
        } else {
            CrappyBird.resetGame();
            frame.removeKeyListener(this);
        }

    }
}
