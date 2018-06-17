/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random; 
import javax.imageio.ImageIO;


/**
 * A basic game object starting in the middle of the game court. It is displayed as a
 * space ship. The player can control the ship by arrow keys and shoot lasers by space key.
 */

public class Asteroid extends GameObj {
    private static BufferedImage img;
    private Random rand;
    private int lasersNeeded;

    public Asteroid(int courtWidth, int courtHeight, String image) {
        super(courtWidth, courtHeight);
        rand = new Random();
        this.setPy(0);
        this.setPx(rand.nextInt(courtWidth));
        this.setVx(rand.nextInt(3));
        this.setVy(3);
    		this.setSize(rand.nextInt(40)+30, rand.nextInt(40)+30);
    		
        //bigger asteroids are harder to kill
        if (this.getHeight() > 50 && this.getWidth() > 50) {
        		lasersNeeded = 3; 
        } else {
        		lasersNeeded = 1; 
        }
        try {
	        if (img == null) {
	            img = ImageIO.read(new File(image));
	        } 
	    } catch (IOException e) {
	        System.out.println("Internal Error:" + e.getMessage());
	    }
    }
    
    @Override
    public void move() {
    	   super.move();
    	   if (hitWall() == Direction.DOWN)
    		   bounce(Direction.DOWN);
    	   if (hitWall() == Direction.RIGHT) 
    		   bounce(Direction.RIGHT); 
    	   if (hitWall() == Direction.LEFT)
    		   bounce(Direction.LEFT);
    	   if (hitWall() == Direction.UP)
    		   bounce(Direction.UP);
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
    
    //return the number of lasers needed to kill this enemy object
    public int getLasersNeeded() {
    		return this.lasersNeeded;
    }
    
    public void decLasersNeeded() {
    		this.lasersNeeded--;
    }

   
}