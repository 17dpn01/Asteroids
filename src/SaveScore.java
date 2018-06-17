import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter; 
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.util.Set;
import java.util.TreeSet;


@SuppressWarnings("serial")
public class SaveScore extends JFrame {
	public static Set<String> names;
	
	public SaveScore() {
		super ("Save Your Score");
		
		names = new TreeSet<String>();
		
		setSize(260, 140);
		setLocation(200,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel name = new JLabel("Player's Name: ");
		name.setLocation(20, 20);
		name.setSize(name.getPreferredSize());
		
		JTextField nameText = new JTextField("Enter Name Here...");
		nameText.setBounds(110, 20, 140, 20);
		
		JLabel score = new JLabel("Score: ");
		score.setLocation(20, 45);
		score.setSize(score.getPreferredSize());
		
		JTextField scoreText = new JTextField("Enter Score Here...");
		scoreText.setBounds(60, 45, 190, 20);
		
		//When the submit button is clicked, the player's information is written to a file
		
		JButton submit = new JButton("Submit");
		submit.setBounds(50, 80, 150, 20);
		submit.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					writeToFile(nameText.getText(), scoreText.getText());
					names.add(nameText.getText());
					setVisible(false);
				} catch (IllegalArgumentException err) {
					System.out.println("Invalid score!");
				}
			}
		});
		
		Container layout = getContentPane();
		
		layout.add(name);
		layout.add(nameText);
		layout.add(score);
		layout.add(scoreText);
		layout.add(submit);
		
		setVisible(true);
	}
	/**
	 * Checks to see if the String is a valid number
	 * @param num
	 */
	private boolean isNum(String num) {
		if (num == null || num.equals("")) {
			return false;
		}
		for (char a : num.toCharArray()) {
			if (!Character.isDigit(a)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Save the player's inputs of name and score to a file 
	 * @param name
	 * @param score
	 */
	private void writeToFile(String name, String score) {
		if (!isNum(score) || name == null 
				|| score == null || names.contains(name)) {
			throw new IllegalArgumentException();
		}
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter("src/High_Scores.txt", true));
	        out.write(name + ":" + score);
	        out.newLine();
	        out.close();
	    } catch(IOException e) {
	        System.out.println("Problem in reading file");
	    }
	}
	
}
