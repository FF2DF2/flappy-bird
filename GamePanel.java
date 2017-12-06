/*
Casey Vu, Kevin Su, Anthony Hou
Purpose: To render the pipes and set starting and game over message..
 */
package flappybird;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel {

    private Bird bird;
    private ArrayList<Rectangle> rects;
    private FlappyBird fb;
    private Font scoreFont, pauseFont;
    public static final int PIPE_W = 50, PIPE_H = 30;
    private Image pipeHead, pipeLength, background;

    //This constructor gets the font for the messages, and images for the pipes.
    public GamePanel(FlappyBird fb, Bird bird, ArrayList<Rectangle> rects) {
        this.fb = fb;
        this.bird = bird;
        this.rects = rects;
        scoreFont = new Font("Arial", Font.BOLD, 18);
        pauseFont = new Font("Arial", Font.BOLD, 30);

        try {
            pipeHead = ImageIO.read(new File("78px-Pipe.png"));
            pipeLength = ImageIO.read(new File("pipe_part.png"));
            background = ImageIO.read(new File("truebg.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    } // end constructor

    //This method fills the pipes with the images, and draws the string line.
    @Override
    public void paintComponent(Graphics g) {
        g.fillRect(0,0,FlappyBird.WIDTH,FlappyBird.HEIGHT);
        g.drawImage(background, -300, -480, null);

        bird.update(g);

        for(Rectangle r : rects) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GREEN);
            AffineTransform old = g2d.getTransform();
            g2d.translate(r.x+PIPE_W/2, r.y+PIPE_H/2);
            if(r.y < FlappyBird.HEIGHT/2) {
                g2d.translate(0, r.height);
                g2d.rotate(Math.PI);
            } // end if

            g2d.drawImage(pipeLength, -PIPE_W/2, PIPE_H/2, GamePanel.PIPE_W, r.height, null);
            g2d.drawImage(pipeHead, -PIPE_W/2, PIPE_H/2, GamePanel.PIPE_W, GamePanel.PIPE_H, null);
            g2d.setTransform(old);
        } // end for
        g.setFont(scoreFont);
        g.setColor(Color.WHITE);
        g.drawString("Score: "+fb.getScore(), 10, 20);

        if(fb.paused()) {
            g.setFont(pauseFont);
            g.setColor(Color.white);
            g.drawString("PAUSED", FlappyBird.WIDTH/2-60, FlappyBird.HEIGHT/2-100);
            g.drawString("CLICK OR PRESS SPACE TO BEGIN", FlappyBird.WIDTH/2-250, FlappyBird.HEIGHT/2);
            g.drawString("PLEASE DON'T VOTE FOR US", FlappyBird.WIDTH/2-210, FlappyBird.HEIGHT/2+100);
        } // end if
    } // end paintComponent()
} // end class GamePanel
