import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/*
     * Laser class that is an object that takes in the position of the ship.
     * Each time shoot() is called, a laser is released.
     */
    public class Laser extends GameObj {
    	
    		public static final String IMG_FILE = "files/laser1.png";
    		private static BufferedImage image;
    		
    		public Laser(GameObj a) {
    			super(a);
    			setVy(-5);
    			setSize(15,15);
    			setPx(a.getPx() + 20);
    			

    			try {
    				if (image == null) {
    					image = ImageIO.read(new File(IMG_FILE));
    				}
    			} catch (IOException e) {
    				System.out.println("Internal Error:" + e.getMessage());
    			}
    		}
    		
    		@Override
    		public void move() {
    			super.move();
    			
    			//if the laser moves off the top of the screen
    			if (hitWall() == Direction.UP) {
    				setOnScreen(false);
    			}
    		}
    	        
    		@Override
    		public void draw(Graphics g) {
    			g.drawImage(image, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    		}
    		
    		
    }
    