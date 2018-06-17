import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.Random;

public class LifePowerUp extends PowerUp {
	private static final String IMG_FILE = "files/life.png";
	private BufferedImage img; 
	
	public LifePowerUp (int courtWidth, int courtHeight) {
		super (courtWidth, courtHeight); 
		this.setSize(30, 30);
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
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
	}
	
	//if intersects an asteroid, then it dies
	@Override
	public void collide(GameObj other) {
		if (this.intersects(other)) {
			if (other instanceof Asteroid) {
				other.setOnScreen(false);
				this.setOnScreen(false);
			}
		}
	}
}
