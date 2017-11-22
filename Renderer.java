
package flappyBird;

import javax.swing.JPanel;
import java.awt.Graphics;

// renderer for the class, to double buffer
// extends JPanel will extend everything in this class
public class Renderer extends JPanel{

    // add generated serial version ID to supress the serialVersionUID warning
    //private static final long serialVersionUID = 8095379131479840912L;

    // edit a method to retain the super of that method
    @Override
    protected void paintComponent(Graphics g) { // Graphics is a built-in class
        // call the code in the parent class of JPanel
        super.paintComponent(g);

        // pass graphics g into here
        FlappyBird.flappyBird.repaint(g);
    }
}
