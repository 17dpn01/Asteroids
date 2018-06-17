/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList; 


import javax.imageio.ImageIO;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class Ship extends GameObj {
	
	public static final String IMG_FILE = "files/ship.gif";
	public static final int SIZE = 80;
    public static final int INIT_POS_X = 250;
    public static final int INIT_POS_Y = 500;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    
    private ArrayList<Laser> lasers;
    private int laserCount, life;

    private static BufferedImage img;

    public Ship(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
        
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        
        lasers = new ArrayList<Laser>();

        laserCount = 100;
        life = 5;
    }
    
   
    /*
     * Getter for lasers collection
     */
    public ArrayList<Laser> getLasers() {
    		return lasers;
    }
    
    /*
     * Determines the behavior of the ship associated with a keyEvent
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.setVx(-4);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.setVx(4);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.setVy(4);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.setVy(-4);
        }    
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        		shoot();
        		laserCount--; 
        		if (laserCount == -1) {
        			laserCount++;
        		}
        }
    }

    public void keyReleased(KeyEvent e) {
    		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (this.getVx() != 4)
    				this.setVx(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
           if (this.getVx() != -4)
        			this.setVx(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (this.getVy() != -4)
        			this.setVy(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
        		if (this.getVy() != 4)
        			this.setVy(0);
        	}    
    	}
        
    
    /*
     * Allowing the ship to release bullets 
     */
    public void shoot() {
    		lasers.add(new Laser(this));
    }
    
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
       
    }
    
    //Determines the behavior of ship and the object it collides with 
    public void collide(GameObj other) {
		if (other instanceof Asteroid) {
			life--; 
			other.setOnScreen(false);
		}
		if (other instanceof PowerUp) {
			if (other.getVx() == 3) {
				laserCount = laserCount + 10;
				other.setOnScreen(false);
			} else {
				life++; 
				other.setOnScreen(false);
				if (life == 6) {
					life--;
				}
			}			
		}
	}
    
    /**
     * Getters and setters 
     */
    	public void setLaserCount(int count) {
    		this.laserCount = count;
    	}
    	
    	public int getLaserCount() {
    		return laserCount; 
    	}
    	
    	public void setLife(int life) {
    		this.life = life;
    	}
    	
    	public int getLife() {
    		return this.life;
    	}
    	
    	

}