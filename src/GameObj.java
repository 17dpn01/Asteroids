/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Graphics;


/** 
 * An object in the game. 
 *
 * Game objects exist in the game court. They have a position, velocity, size and bounds. Their
 * velocity controls how they move; their position should always be within their bounds.
 */
public abstract class GameObj {
    /*
     * Current position of the object (in terms of graphics coordinates)
     *  
     * Coordinates are given by the upper-left hand corner of the object. This position should
     * always be within bounds.
     *  0 <= px <= maxX 
     *  0 <= py <= maxY 
     */
	
    private int px, py;

    /* Size of object, in pixels. */
    private int width, height;

    /* Velocity: number of pixels to move every time move() is called. */
    private int vx, vy;

    /* 
     * Upper bounds of the area in which the object can be positioned. Maximum permissible x, y
     * positions for the upper-left hand corner of the object.
     */
    private int maxX, maxY;


    /*
     * Decide whether or not to remove the object from the screen.
     */
    private boolean onScreen;
    
    /**
     * Constructor
     */
    
    //Create a GameObj with the @param courtWidth and courtHeight
    public GameObj(int courtWidth, int courtHeight) {
    		onScreen = true;
    		this.maxX = courtWidth - width;
    	    this.maxY = courtHeight - height;
    	    this.setVx(0);
    	    this.setVx(0);
    }
    
    //Create a GameObj with the @param velocities, positions, size, and court dimensions
    public GameObj(int vx, int vy, int px, int py, int width, int height, int courtWidth,
        int courtHeight) {
        this.vx = vx;
        this.vy = vy;
        this.px = px;
        this.py = py;
        this.width  = width;
        this.height = height;
        // take the width and height into account when setting the bounds for the upper left corner
        // of the object.
        this.maxX = courtWidth - width;
        this.maxY = courtHeight - height;
        onScreen = true;
    }
    
    
    /*
     * Create an object that takes in the position of the @param GameObj. 
     * This is useful for making objects appear out of other objects (aka the lasers for ship)
     */
    public GameObj(GameObj a) {
    		super();
    		this.vx = 0;
    		this.vy = 0;
    		this.px = a.px;
    		this.py = a.py;
    		this.width = a.width;
    		this.height = a.height;
    		this.maxX = a.maxX;
    		this.maxY = a.maxY;
    		onScreen = true;
    }
    

    /*** GETTERS **********************************************************************************/
   public int getMaxX() {
	   return this.maxX;
   }
   
   public int getMaxY() {
	   return this.maxY;
   }
   
   public boolean getOnScreen() {
	   return this.onScreen;
   }
    
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }
    
    public int getVx() {
        return this.vx;
    }
    
    public int getVy() {
        return this.vy;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    

    /*** SETTERS **********************************************************************************/
    public void setOnScreen(boolean onScreen) {
    		this.onScreen = onScreen;
    }
    
    public void setPx(int px) {
        this.px = px;
        clip();
    }

    public void setPy(int py) {
        this.py = py;
        clip();
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    
    public void setSize(int width, int height) {
    	    this.width = width;
    	    this.height = height;
    }
    
    
    /*** UPDATES AND OTHER METHODS ****************************************************************/

    /**
     * Prevents the object from going outside of the bounds of the area designated for the object.
     * (i.e. Object cannot go outside of the active area the user defines for it).
     */ 
    private void clip() {
        this.px = Math.min(Math.max(this.px, 0), this.maxX);
        this.py = Math.min(Math.max(this.py, 0), this.maxY);
    }

    /**
     * Moves the object by its velocity.  Ensures that the object does not go outside its bounds by
     * clipping.
     */
    public void move() {
        this.px += this.vx;
        this.py += this.vy;

        clip();
    }

    /**
     * Determine whether this game object is currently intersecting another object.
     * 
     * Intersection is determined by comparing bounding boxes. If the bounding boxes overlap, then
     * an intersection is considered to occur.
     * 
     * @param that The other object
     * @return Whether this object intersects the other object.
     */
    public boolean intersects(GameObj that) {
        return (this.px + this.width >= that.px
            && this.py + this.height >= that.py
            && that.px + that.width >= this.px
            && that.py + that.height >= this.py);
    }


    /**
     * Determine whether this game object will intersect another in the next time step, assuming
     * that both objects continue with their current velocity.
     * 
     * Intersection is determined by comparing bounding boxes. If the  bounding boxes (for the next
     * time step) overlap, then an intersection is considered to occur.
     * 
     * @param that The other object
     * @return Whether an intersection will occur.
     */
    public boolean willIntersect(GameObj that) {
        double thisNextX = this.px + this.vx;
        double thisNextY = this.py + this.vy;
        double thatNextX = that.px + that.vx;
        double thatNextY = that.py + that.vy;
    
        return (thisNextX + this.width >= thatNextX
            && thisNextY + this.height >= thatNextY
            && thatNextX + that.width >= thisNextX 
            && thatNextY + that.height >= thisNextY);
    }
    
    /**
     * Update the velocity of the object in response to hitting an obstacle in the given direction.
     * If the direction is null, this method has no effect on the object.
     *
     * @param d The direction in which this object hit an obstacle
     */
    public void bounce(Direction d) {
        if (d == null) return;
        
        switch (d) {
        case UP:
            this.vy = Math.abs(this.vy);
            break;  
        case DOWN:
            this.vy = -Math.abs(this.vy);
            break;
        case LEFT:
            this.vx = Math.abs(this.vx);
            break;
        case RIGHT:
            this.vx = -Math.abs(this.vx);
            break;
        }
    }

    /**
     * Determine whether the game object will hit a wall in the next time step. If so, return the
     * direction of the wall in relation to this game object.
     *  
     * @return Direction of impending wall, null if all clear.
     */
    public Direction hitWall() {
        if (this.px + this.vx < 0) {
            return Direction.LEFT;
        } else if (this.px + this.vx > this.maxX) {
           return Direction.RIGHT;
        }

        if (this.py + this.vy < 0) {
            return Direction.UP;
        } else if (this.py + this.vy > this.maxY) {
            return Direction.DOWN;
        } else {
            return null;
        }
    }

    /**
     * Determine whether the game object will hit another object in the next time step. If so,
     * return the direction of the other object in relation to this game object.
     * 
     * @param that The other object
     * @return Direction of impending object, null if all clear.
     */
    public Direction hitObj(GameObj that) {
        if (this.willIntersect(that)) {
            double dx = that.px + that.width / 2 - (this.px + this.width / 2);
            double dy = that.py + that.height / 2 - (this.py + this.height / 2);

            double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy *dy)));
            double diagTheta = Math.atan2(this.height / 2, this.width / 2);

            if (theta <= diagTheta) {
                return Direction.RIGHT;
            } else if (theta > diagTheta && theta <= Math.PI - diagTheta) {
                // Coordinate system for GUIs is switched
                if (dy > 0) {
                    return Direction.DOWN;
                } else {
                    return Direction.UP;
                }
            } else {
                return Direction.LEFT;
            }
        } else {
            return null;
        }
    }
    
    
    /**
     * Default draw method that provides how the object should be drawn in the GUI. This method does
     * not draw anything. Subclass should override this method based on how their object should
     * appear.
     * 
     * @param g The <code>Graphics</code> context used for drawing the object. Remember graphics
     * contexts that we used in OCaml, it gives the context in which the object should be drawn (a
     * canvas, a frame, etc.)
     */
    public abstract void draw(Graphics g);
}