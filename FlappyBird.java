// Casey Vu, Anthony Hou, Kevin Su
// FlappyBird GUI
// Description: Creating rectangles to act as walls inside a giant Rectangle
// need to record highest score
// need to generate clouds
package flappyBird;

import java.awt.Color; // color class
import java.awt.Font; // to use different fonts
import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList; // for arraylist of rectangle
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random; // for random object

import javax.swing.JFrame; // for new instance of JFrame
import javax.swing.Timer;
import javax.swing.*;


// implement interface class that is abstract
public class FlappyBird extends JPanel implements ActionListener, MouseListener, KeyListener{

    // static instance of flappyBird
    public static FlappyBird flappyBird;

    // final instance variable for resolution 800 x 800 square
    public final int WIDTH = 800, HEIGHT = 800;

    // create instance variable of renderer
    public Renderer renderer;

    // create object square or "Flappy Bird"
    public Rectangle bird;

    // create column or wall arraylist of type Rectangle
    public ArrayList<Rectangle> columns;

    // create variables for space bar ticks and yMotion for the y motion of the bird
    public int ticks, yMotion, score, score2;
    public int highScore = 0;

    // create a boolean gameOver, and started so that bird move until it has started
    // set gameOver and started to false
    public boolean gameOver, started;

    // create a new random object
    public Random rand;

    // constructor
    public FlappyBird() {
        // create a new instance of  jframe
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this); // timer at 20, listener is this

        // create new renderer
        renderer = new Renderer();

        // create new rand
        rand = new Random();

        // add renderer
        jframe.add(renderer);

        // set title of program
        jframe.setTitle("Flappy Bird Clone in Java by Casey Vu");

        // set to terminate the program when when pressing X on the GUI
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame.EXIT_ON_CLOSE is an integer to close the window

        // set resolution of game screen
        jframe.setSize(WIDTH, HEIGHT);

        // add mouse listener
        jframe.addMouseListener(this);

        // add key listener
        jframe.addKeyListener(this);

        // prevent screen from resizing
        jframe.setResizable(false);

        // set screen visible
        jframe.setVisible(true);

        // create new Rectangle
        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        // X and Y coordinates are at the direct center of the screen
        // size is height 20 and width 20

        // create new columns arraylist
        columns = new ArrayList<Rectangle>();

        // add columns right at the beginning
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        // repaint more than twice
        timer.start();
    } // end constructor


    // create method repaint
    public void repaint(Graphics g) {

        // painting from top to bottom

        // set background color to cyan
        g.setColor(Color.CYAN);
        // fill up background with cyan
        g.fillRect(0,0, WIDTH, HEIGHT);

        // set color of ground to orange
        g.setColor(Color.orange);
        // set position of ground to take up the entire width of the screen at height 150
        g.fillRect(0, HEIGHT - 120, WIDTH, 150);

        // set color of grass on the ground
        g.setColor(Color.green);
        // set position of grass to take the take up the entire width of the screen just above the ground
        g.fillRect(0, HEIGHT - 120, WIDTH, 20);

        // set color of flappybird
        g.setColor(Color.red);
        // fill bird with the color red at the center of the screen
        g.fillRect(bird.x, bird.y, bird.width, bird.height);


        // iterator to paint columns
        for (Rectangle column : columns) {
            // fill in columns with colors
            paintColumn(g, column);
        } // end for

        // set font color
        g.setColor(Color.white);
        // set fount type and size
        g.setFont(new Font("Arial", 1, 100));

        // if game has not started
        if(!started){
            g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
        } // end if

        // if game is over, print Game Over Message
        if(gameOver){
            g.drawString("Game Over!", 100, HEIGHT / 2 - 50);

            // display last score
            g.drawString("Score: " + String.valueOf(score), 100, HEIGHT / 2 - 150);
            
            /*
            if(score > highScore) {
                score2 = highScore;
                highScore = score;
                // display last score
                g.drawString("Score: " + String.valueOf(score2), 100, HEIGHT / 2 - 150);
             } else if(score < score2) {
                score2 = score;
                g.drawString("Score: " + String.valueOf(score2), 100, HEIGHT / 2 - 150);
            }
            */


        } // end if

        // display the current score
        if(!gameOver && started) {

            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }

        // display the high score after game over
        if(!gameOver && started) {


            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }
        // test repaint will print twice due to double buffering

    } // end method repaint

    public void addColumn(boolean start) {
        int space = 300; // adding space between the columns
        int width = 100; // width of columns

        // create columns with random heights with minimum height 50 and maximum height 300
        int height = 50 + rand.nextInt(300);

        if (start) {
            // starting columns

            // ground  column
            // this x is all the way to the right of the screen then add the width 300
            // since java adds everything staring at (0, 0) "top left" moving over by width, you would need to add the width
            // if there are any other columns, the 300 will move over the columns
            // HEIGHT - height - 120 allows the column to be at the top of the grass
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));

            // ceiling column
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));

        } else { // to append this to the last column that is generated

            // getting column from the array list by getting the one at the poisiont of columns.size to become 0 starting at the position of 0 and the next one will start at position of 1
            //
            // ground column
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));

            // ceiling column
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
        } // end if else
    } // end method addColumn

    // create new method rendererColumn
    public void paintColumn(Graphics g, Rectangle column) {

        // set color of column to be a darker green
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    } // end paintColumn

    public void jump() {
        // reset game after game over
        if(gameOver) {
            // create new Rectangle
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            // X and Y coordinates are at the direct center of the screen
            // size is height 20 and width 20

            // clear columns
            columns.clear();

            // set yMotion to 0
            yMotion = 0;
            score = 0;

            // add columns right at the beginning
            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);
            gameOver = false;
        }

        if(!started) {
            started = true;
        } else if(!gameOver){
            if (yMotion > 0) {
                yMotion = 0;
            }
            yMotion -= 10; // jump by 10 pixels
        }


    } // end jump

    // add actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {

        // set speed
        int speed = 10;

        // ticks to add
        ticks++;

        // if started, everything begins/moves
        if(started) {
            // loop to move the columns over
            // add a column each time
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);

                // decrease rectangle speed
                column.x -= speed;
            } // end for

            // remainder of ticks = 0 and yMotion < 15, then call do yMotion +=2
            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            } // end if

            // loop to move the columns over
            // add a column each time
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);

                if (column.x + column.width < 0) {
                    // make sure that column is removed
                    columns.remove(column);

                    // if it's a top column, then add another column to have an infinite loop of walls
                    if(column.y == 0) {
                        addColumn(false);
                    } // end inner if
                } // end if
            } // end for

            bird.y += yMotion;

            // right after the bird has moved
            // check for collision
            for(Rectangle column : columns) {

                // add score if the bird is in the middle or in between the column once
                if(column.y == 0 && bird.x  + bird.width / 2 > column.x + column.width / 2 - 5 && bird.x + bird.width / 2 < column.x + column.width / 2 + 5) {
                    // add score
                    score++;

                }
                if(column.intersects(bird)) {
                    gameOver = true;

                    if(bird.x <= column.x) {
                        bird.x = column.x - bird.width;
                    } else {
                        if (column.y != 0) {
                            bird.y = column.y - bird.height;
                        } else if(bird.y < column.height) {
                            bird.y = column.height;

                        }
                    }

                    // when bird falls, the column will move the bird
                    bird.x = column.x - bird.width;
                } // end if
            } // end for

            // set to game over if bird has touched the ceiling
            if (bird.y > HEIGHT - 120 || bird.y < 0)
            {
                gameOver = true;
            } // end if

            // if the game has not started



            // set bird to stop falling on top of the grass and bird to fall when it hits the ceiling or wall
            if (bird.y + yMotion >= HEIGHT - 120) {
                // height right above the grass
                bird.y = HEIGHT - 120 - bird.height;

                // game over if bird touches the ground
                gameOver = true;
            }

        } // end outer if


        // call renderer repaint
        renderer.repaint();
    }

    // main
    public static void main(String[] args) {

        // setting variable flappyBird to a new FlappyBird
        // creating a new instance of flappyBird here
        flappyBird = new FlappyBird();

    } // end main

    @Override
    public void mouseClicked(MouseEvent e) {
        // to jump by clicking mouse
        jump();
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // jump with space bar
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }

        // exit by pressing the Escape key
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        } // end if
    }
} // end public class FlappyBird


