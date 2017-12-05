/*
Casey Vu, Kevin Su, Anthony Hou
Purpose: To render the "flappy bird" and set position on the screen.
 */
package flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Bird {
    public float x, y, vx, vy;
    public static final int RAD = 25;
    private Image img;

    // create constructor
    public Bird() {
        x = FlappyBird.WIDTH/2;
        y = FlappyBird.HEIGHT/2;
        try {
            img = ImageIO.read(new File("sticker,375x360.u2.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    } // end constructor
    public void physics() {
        x+=vx;
        y+=vy;
        vy+=0.5f;
    } // end physics()
    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(img, Math.round(x-RAD),Math.round(y-RAD),2*RAD,2*RAD, null);
    } // end update()

    /*
    This method sets the pixels the bird will move when jumping.
     */
    public void jump() {
        vy = -8;
    } // end jump()

    // This method sets the initial position of the bird when starting the game.
    public void reset() {
        x = 640/2;
        y = 480/2;
        vx = vy = 0;
    } // end reset()
} // end class Bird
