/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public Game() {
	}
	
    public void run() {
    		frame1();
    }
    
    
    /**
     * Set up the starting screen
     */
    private void frame1() {
    		final JFrame frame1 = new JFrame("ASTEROIDS");
        frame1.setLocation(0, 0);

        //Sets up start screen
        final StartScreen start_screen = new StartScreen();
        frame1.add(start_screen, BorderLayout.CENTER);

        final JPanel options_panel = new JPanel();
        
        //Play Game button goes to game court on click.
        final JButton play = new JButton("PLAY GAME");
        play.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			frame1.setVisible(false);
        			frame3();
        		}
        });
        
        //Instruction button goes to instruction screen on click.
        final JButton instructions = new JButton("INSTRUCTIONS");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame1.setVisible(false);
                frame2();
            }
        });
        
        //Leader board button goes to leaderboard on click.
        final JButton leaderboard = new JButton("LEADERBOARD");
        options_panel.add(play);
        options_panel.add(instructions);
        options_panel.add(leaderboard);
        frame1.add(options_panel, BorderLayout.NORTH);
        
        
        frame1.pack();
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
    }
    
    //Sets up instruction screen
    private void frame2() {
    		final JFrame frame2 = new JFrame("INSTRUCTIONS");
        frame2.setLocation(0, 0);
        frame2.setSize(1000, 550);
        
        //instruction screen
        final StartScreen instruction_screen = new StartScreen(true);
        frame2.add(instruction_screen);
        
        final JPanel back_panel = new JPanel();
        final JButton back = new JButton("BACK");
        back.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			frame2.setVisible(false);
        			frame1();
        		}
        });
        
        back_panel.add(back);
        frame2.add(back_panel, BorderLayout.NORTH);
        frame2.pack();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame2.setVisible(true);
    }
    
    
    //Calling Game court
    public void frame3() {
    		final JFrame frame3 = new JFrame();
        frame3.setLocation(0, 0);
        
    		// Status panel
        

        // Main playing area
        final GameCourt court = new GameCourt();
        frame3.add(court, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame3.add(control_panel, BorderLayout.NORTH);
        
        //Reset button
        final JButton reset = new JButton("RESET");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);
        
        final JButton quit = new JButton("QUIT AND SAVE");
        quit.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			court.quit();
        			SaveScore save = new SaveScore();
        		}
        });
        control_panel.add(quit);
        
        final JButton back = new JButton("BACK");
        back.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			frame3.setVisible(false);
        			frame1(); 
        		}
        });
        control_panel.add(back);
        
        // Put the frame on the screen
        frame3.pack();
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setVisible(true);

        // Start game
        court.reset();
    }
    
 

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}