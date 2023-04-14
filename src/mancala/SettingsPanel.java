package mancala;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 * This class creates the settings panel for the mancala game. It has two different settings options, 
 * single player or two player. By clicking one of these buttons, the game will launch. This panel
 * also has other buttons that allow the user to switch between menus.
 *
 */
public class SettingsPanel extends JPanel {

	private Game game = new Game();
	private Random rand = new Random();
	public JButton home = new JButton("Home");
	public JButton quitGame = new JButton("Quit");
	public JButton singlePlayer = new JButton("Single Player");
	public JButton twoPlayer = new JButton("Two Player");
	


	/**
	 * Constructor for the settings panel
	 */
	public SettingsPanel() {
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);
        JPanel panel=new JPanel();  
        panel.setBounds(40,80,200,200);    
		this.setBackground(new Color(228, 218, 199));
		
        

		JLabel welcomeLabel = new JLabel("Welcome to");
		JLabel buttonDesc = new JLabel("Choose the amount of players:");

        

		welcomeLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
		quitGame.setFont(new Font("DialogInput", Font.BOLD, 10));
		home.setFont(new Font("DialogInput", Font.BOLD, 10));
		singlePlayer.setFont(new Font("DialogInput", Font.BOLD, 25));
		twoPlayer.setFont(new Font("DialogInput", Font.BOLD, 25));
		buttonDesc.setFont(new Font("DialogInput", Font.BOLD, 12));
		
		
		welcomeLabel.setBounds(325, 40, 200, 25);
		quitGame.setBounds(400, 450, 100, 35);
		home.setBounds(300, 450, 100, 35);
		singlePlayer.setBounds(300, 200, 200, 100);
		twoPlayer.setBounds(300, 300, 200, 100);
		buttonDesc.setBounds(300, 125, 250, 100);

		
		this.add(welcomeLabel);
		this.add(quitGame);
		this.add(home);		
		this.add(singlePlayer);
		this.add(twoPlayer);
		this.add(buttonDesc);
		


	}

	/** 
	 * Adds the actual images to the screen
	 * 
	 * @param Graphics which allows images to be added to the JPanel
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		final Graphics2D g = (Graphics2D) graphics;
		
		// Create the Mancala title image
		BufferedImage titleImage;
		try {
			titleImage = ImageIO.read(new File("mancalaTitle.png"));
			Image titleImageIcon = new ImageIcon(titleImage).getImage();
			Image resizedTitleImage = titleImageIcon.getScaledInstance(400, 60, Image.SCALE_SMOOTH);
			g.drawImage(resizedTitleImage, 200, 100, null);

		} catch (IOException e) {
			e.printStackTrace();
		}

 
	}



}