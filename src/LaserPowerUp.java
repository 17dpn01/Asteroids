import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.Random;

public class LaserPowerUp extends PowerUp {
	private static final String IMG_FILE = "files/laser.png";
	private BufferedImage img; 
	
	public LaserPowerUp (int courtWidth, int courtHeight) {
		super (courtWidth, courtHeight); 
		this.setSize(30, 30);
		this.setVx(3);
		this.setVy(3);
		this.setOnScreen(true);
		this.setPx((new Random()).nextInt(courtWidth));
		this.setPy((new Random()).nextInt(courtHeight));
		
		try {
	        if (img == null) {
	            img = ImageIO.read(new File(IMG_FILE));
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
	
	//If intersect with an asteroid, then it bounces 
	@Override
	public void collide (GameObj other) {
		if (this.intersects(other)) {
			Direction d1 = hitObj(other);
			Direction d2 = other.hitObj(this);
			if (other instanceof Asteroid) {
				this.bounce(d1);
				other.bounce(d2);
			}
		}
	}
	
}
