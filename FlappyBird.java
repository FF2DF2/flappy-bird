/*
Casey Vu, Kevin Su, Anthony Hou
Purpose: A recreation of the smash hit mobile game Flappy Bird. The objective is to simply avoid hitting the pipes
and to get the highest possible score.
 */

package flappybird;

import java.awt.Rectangle;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlappyBird implements ActionListener, KeyListener, MouseListener {

    // set resolution and fps of game
    public static final int FPS = 60, WIDTH = 640, HEIGHT = 480;

    // create objects
    private Bird bird;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> rects;
    private int time, scroll;
    private Timer t;

    private boolean paused;

    // Create constructor
    public void go() {

        frame = new JFrame("Flappy Bird by Casey Vu, Kevin Su, & Anthony Hou");
        bird = new Bird();
        rects = new ArrayList<Rectangle>();
        panel = new GamePanel(this, bird, rects);
        frame.add(panel);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.addMouseListener(this);

        paused = true;

        t = new Timer(1000/FPS, this);
        t.start();
    } // end go() constructor


    public static void main(String[] args) {
        new FlappyBird().go();
    } // end main

    @Override
    public void actionPerformed(ActionEvent e) {

        panel.repaint();

        // set width and height of pipes according to resolution of the screen
        if(!paused) {
            bird.physics();
            if(scroll % 90 == 0) {
                // set pipes of random heights
               Rectangle r = new Rectangle(WIDTH, 0, GamePanel.PIPE_W, (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT));

                int h2 = (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT);

                Rectangle r2 = new Rectangle(WIDTH, HEIGHT - h2, GamePanel.PIPE_W, h2);
                rects.add(r);
                rects.add(r2);
            }
            ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
            boolean game = true;

            // set pacing of pipes
            for(Rectangle r : rects) {
                // set speed of pipes moving across screen
                r.x-=3;
                if(r.x + r.width <= 0) {
                    toRemove.add(r);
                } // remove pipes after they leave the screen

                // set game over message if there is collision
                if(r.contains(bird.x, bird.y)) {
                    JOptionPane.showMessageDialog(frame, "You lose!\n"+"Your score was: "+time+".");
                    game = false;
                }
            } // end for
            rects.removeAll(toRemove);
            time++;
            scroll++;

            // set game to end if there is collision between the bird and the pipes
            if(bird.y > HEIGHT || bird.y+bird.RAD < 0) {
                game = false;
            } // end if

            // reset game
            if(!game) {
                rects.clear();
                bird.reset();
                time = 0;
                scroll = 0;
                paused = true;
            } // end if
        } // end if
    } // end method actionPerformed()

    // this method returns the score based on time milliseconds.
    public int getScore() {
        return time;
    } // return score

    // set key controls
    public void keyPressed(KeyEvent e) {

        // set space bar to start the game at the beginning or during pause.
        if(e.getKeyCode()==KeyEvent.VK_SPACE) {
            bird.jump();
            paused = false;
        }
        // set escape to exit the game
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
            paused = true;
        }
        // set key P to pause the game.
        if(e.getKeyCode()==KeyEvent.VK_P) {
            paused = true;
        }
    }
    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }

    public boolean paused() {
        return paused;
    }

    // set bird to jump if mouse is right or left clicked.
    @Override
    public void mouseClicked(MouseEvent e) {
        bird.jump();
        paused = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
