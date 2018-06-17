/**
 * An abstract class that has a collide method to determine that behavior of 
 * the powerUp when it intersects other objects
 */
import java.awt.*; 

public abstract class PowerUp extends GameObj {
	
	public PowerUp(int courtWidth, int courtHeight) {
		super(courtWidth, courtHeight);
	}
	
	public abstract void collide(GameObj other);
}


