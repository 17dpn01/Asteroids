/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



/**
 * StartScreen
 * 
 * Menu screen with options for instruction, starting the game, and viewing high scores.
 */
@SuppressWarnings("serial")
public class StartScreen extends JPanel {
	
	//bg image of the screen
    private BufferedImage bgImage; 
   
    public static final int COURT_WIDTH = 1000;
    public static final int COURT_HEIGHT = 550;

    
    public StartScreen () {
    		try {
    		bgImage = ImageIO.read(new File("files/Asteroids_screen1.png"));
    		JLabel picLabel = new JLabel(new ImageIcon(bgImage));
    		add(picLabel);
    		} catch (IOException e) {
    			//do nothing
    		}

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        
        
    }
    //instructions screen
    public StartScreen (boolean a) {
    		try {
    			bgImage = ImageIO.read(new File("files/Instructions.png"));
    			JLabel picLabel = new JLabel(new ImageIcon(bgImage));
        		add(picLabel);
         } catch (IOException e) {
        		//do nothing
        	}
    		setFocusable(a);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    
    
    
}