/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	

    // the state of the game logic
    private Ship ship; // the Black Square, keyboard control
    
    private Image bgImage; 
    private int counter;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<PowerUp> powerUps; 
   
    public boolean playing = false; // whether the game is running 
   

    // Game constants
    public static final int COURT_WIDTH = 1280;
    public static final int COURT_HEIGHT = 720;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    
    private int score, time;
    

    public GameCourt() {
    		
    		asteroids = new ArrayList<Asteroid>();
    		powerUps = new ArrayList<PowerUp>();
    		counter = 0; 
    		score = 0;
    		time = 0;
    		

    		
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //setBackground(new Color(20, 45, 94));
        bgImage = Toolkit.getDefaultToolkit().createImage("files/space.jpg");

        // The timer is an object which triggers an action periodically with the given INTERVAL
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); 
        

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        
     // This key listener allows the ship to move as long as an arrow key is pressed, by
        // changing the ship's velocity accordingly. (The tick method below actually moves the
        // ship.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                ship.keyPressed(e);
            }
            public void keyReleased(KeyEvent e) {
                ship.keyReleased(e);
            }
        });

    }
    
    
    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        ship = new Ship(COURT_WIDTH, COURT_HEIGHT);
        counter = 0; 
        asteroids = new ArrayList<Asteroid>();

        playing = true;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    /**
     * Quit the game so that everything no longer moves
     */
    public void quit() {
    		playing = false; 
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            
        	   //every 200 milliseconds, add a new asteroid to the screen
        	   if ((counter % 500) == 0) {
        		   Asteroid a = new Asteroid(COURT_WIDTH, COURT_HEIGHT, "files/Asteroid.png");
        		   asteroids.add(a);   
        	   }
        	   
        	   //Every 50,000 milliseconds, add a new laser power up to the screen
        	   if ((counter % 3000) == 0 && counter > 500) {
        		   PowerUp a = new LaserPowerUp(COURT_WIDTH, COURT_HEIGHT);
        		   powerUps.add(a);
        	   }
        	   
        	   /*Every 100,000 milliseconds, add a new life power up to the screen
        	    * Rarer than the laser ones
        	    */
        	   if ((counter % 5000) == 0 && counter > 800) {
        		   PowerUp b = new LifePowerUp(COURT_WIDTH, COURT_HEIGHT);
        		   powerUps.add(b);
        	   }
        	   
        	   moveObjects();
        	   collision();
        	   
           counter = counter + 5;
            // update the display
            
           repaint();  
           
           if ((counter % 180) == 0) {
        	   		time ++; 
           }   
        }
    }
      
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(bgImage, 0, 0, null);
        
        drawScoreBoard(g);
        
        if (playing) {
        		if (counter < 350) { 
        			drawLevel(g);
        		} else if (counter >=350 && counter < 50000){
        			drawObjects(g);
        		} else if (counter >= 50000 && counter < 50350) {
        			drawLevel(g);
        		}
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    
    
    /*--------------------------HELPER METHODS FOR tick() and paintComponent()-------------------------------*/
    
    /**
     * Draw the objects on the screen with the param graphics context
     * @param g
     */
    private void drawObjects(Graphics g) {
    		
    		if (ship.getOnScreen()) {
    			ship.draw(g);
    		}
    		
    		if (ship.getLaserCount() > 0) {
    		ArrayList<Laser> lasers = ship.getLasers();
        for (Laser a : lasers) {
            if (a.getOnScreen())
            	    a.draw(g);
        		}
    		}
        
        for (Asteroid a : asteroids) {
           if (a.getOnScreen())
        	       a.draw(g);
        }
        
        for (PowerUp a : powerUps) {
        		if (a.getOnScreen()) 
        			a.draw(g);
        }
    }
    
    
    /**
     * Advance all objects if their onScreen status is true
     */
    private void moveObjects() {
    		if (ship.getOnScreen())	{
 		   ship.move();
    		}
     
    		ArrayList<Laser> lasers = ship.getLasers();
    		if (ship.getLaserCount() > 0) {
    			for (int i = 0; i < lasers.size(); i++) {
    				Laser a = lasers.get(i);
    				if (a.getOnScreen()) {
    					a.move();
    				} else {
    					lasers.remove(i);
    				}
    			}
    		}
    		
    		for (int i = 0; i < asteroids.size(); i++) {
     		Asteroid a = asteroids.get(i);
     		if (a.getOnScreen()) {
     			a.move();
     		} else {
     			asteroids.remove(i);
     		}
    		}  
    		
    		for (int i = 0; i < powerUps.size(); i++) {
    			PowerUp a = powerUps.get(i);
    			if (a.getOnScreen()) {
    				a.move();
    			} else {
    				powerUps.remove(a); 
    			}
    		}
    		
    }
    
    
    /**
     * Check for object collisions and decide the appropriate behaviors
     * when two objects collide
     */
    private void collision() {
    		ArrayList<Laser> lasers = ship.getLasers();
    		
    		//if a Laser power up intersects with an asteroid, then both disappear
    		for (Asteroid b : asteroids) {
    			for (PowerUp a : powerUps) {
    				a.collide(b);
    			}
    		}
    		
    		//if a laser intersects with an asteroid, then both disappear from the screen
    		for (Laser a : lasers) {
    			for (Asteroid b : asteroids) {
    				if (a.intersects(b)) {
    					a.setOnScreen(false);
    					b.decLasersNeeded();
    					if (b.getLasersNeeded() == 0) {
    						b.setOnScreen(false);
    					}
    					score++; 
    				}
    			}
    		}
    		
    		//if an asteroid collides with ship, the ship loses a life
    		for (int i = 0; i < asteroids.size(); i++) {
    			Asteroid a = asteroids.get(i);
    			if (ship.intersects(a)) {
    				ship.collide(a);
    				if (ship.getLife() == 0) {
    					playing = false;
    				}
    			}
    		}
    		
    		//If the ship hits a power up, then its laser count or life increases
    		for (int i = 0; i < powerUps.size(); i++) {
    			PowerUp a = powerUps.get(i);
    			if (ship.intersects(a)) {
    				ship.collide(a);
    			}
    		}
    }
    
    /**
     * Set up the score board for the game 
     * Keeps track of game's state and displays it to user
     * @param g
     */
    private void drawScoreBoard(Graphics g) {
    		g.setColor(Color.WHITE);
    		g.drawRect(20, 20, 150, 80);
    		Font font = new Font("Courier", Font.PLAIN, 14);
    		g.setFont(font);
    		g.setColor(Color.PINK);
    		g.drawString("Score: " + score, 25, 35);
    		
    		//Setting up the timer
    		int minutes = time/60; 
    		int seconds = time - minutes*60; 
    		int hour = minutes/60; 
    		g.drawString("Time: " + hour + ":" + minutes + ":" + seconds, 25, 55);
    		g.drawString("Lasers: " + ship.getLaserCount(), 25, 75);   		
    		
    		g.drawString("Life: " + ship.getLife(), 25, 95);
    		
    }
   
    private void drawLevel(Graphics g) {
    		g.setColor(Color.PINK);
    		Font myFont = new Font("Courier", Font.BOLD, 80);
    		g.setFont(myFont);
    		if (counter < 350) {
    			g.drawString("LEVEL 1", COURT_WIDTH/2 - 150, COURT_HEIGHT/2);	
    		}
    		else {
    			g.drawString("LEVEL 2", COURT_WIDTH/2 - 150, COURT_HEIGHT/2); 
    		}
    }
   
}
    