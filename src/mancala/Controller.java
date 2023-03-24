package mancala;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @title Controller.java
 * @author Ryan Johnson, Hank Rugg
 * @description Allows a user to play the Mancala program. The program begins with a main screen, giving
 * the player the option of beginning play immediately or reading the instructions. If choosing to read
 * the instructions, the user is taken to another screen before being returned to the main screen. Once
 * the user has chosen to begin the game, the user must choose whether to play the game against the 
 * computer or another human player using the same device. Once play has ended, the user is given the
 * option to play the game again or return to the main screen.
 */
public class Controller extends JFrame {	
	private JPanel playPanel;
	
	public Controller() throws IOException {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300,100);
		
		// Add all panels to this frame
		playPanel = new PlayPanel();
		this.add(playPanel);
		this.pack();
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) throws IOException {
		new Controller();
    }
	
	
}
