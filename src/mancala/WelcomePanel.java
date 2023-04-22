package mancala;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class creates the welcome panel to open the program and displays the
 * game title and the vision statement for the game.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
@SuppressWarnings("serial")
public class WelcomePanel extends JPanel {

	public int showPage;
	public JButton playGameButton = new JButton();
	public JButton exitGameButton = new JButton();
	public JButton instructionsButton = new JButton();

	private Image resizedTitleImage;
	private Image resizedWelcomeToImage;
	
	private ImageIcon playGameIcon;
	private ImageIcon playGameHoverIcon;
	private ImageIcon exitGameIcon;
	private ImageIcon exitGameHoverIcon;
	private ImageIcon instructionsIcon;
	private ImageIcon instructionsHoverIcon;
	
	private List<Marble> marbleList = new ArrayList<Marble>();

	
	/**
	 * Constructor for the welcome panel.
	 */
	public WelcomePanel() {
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);
        this.setBounds(40,80,200,200);    
		this.setBackground(new Color(228, 218, 199));

		// Create the welcoming text
		//JLabel welcomeLabel = new JLabel("Welcome to");
//		JTextArea visionStatement = new JTextArea("Through the creation of a computer-based\nMancala game, we are bringing one of the oldest\n"
//				+ "board games to the latest generation.\nUnlike many modern mindless computer games, this\n"
//				+ "game seeks to improve the memory and\nstrategic abilities of all those seeking\nto better their mental skills.");
//		visionStatement.setEditable(false);
//		visionStatement.setBackground(new Color(228, 218, 199));
//		//welcomeLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
//		visionStatement.setFont(new Font("DialogInput", Font.BOLD, 20));

		//welcomeLabel.setBounds(325, 40, 200, 25);
		//visionStatement.setBounds(150, 200, 800, 300);
		
		// Create the "Play Game" Button
		playGameButton.setBounds(310, 320, 180,50);
		playGameButton.setBorderPainted(false);
		playGameButton.setContentAreaFilled(false);
		playGameButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				playGameButton.setIcon(playGameHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				playGameButton.setIcon(playGameIcon);
			}
		});
		
		// Create the "Instructions" Button
		instructionsButton.setBounds(290, 380, 220,50);
		instructionsButton.setBorderPainted(false);
		instructionsButton.setContentAreaFilled(false);
		instructionsButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				instructionsButton.setIcon(instructionsHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				instructionsButton.setIcon(instructionsIcon);
			}
		});
		
		// Create the "Exit Game" Button
		exitGameButton.setBounds(310, 440, 180,50);
		exitGameButton.setBorderPainted(false);
		exitGameButton.setContentAreaFilled(false);
		exitGameButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				exitGameButton.setIcon(exitGameHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				exitGameButton.setIcon(exitGameIcon);
			}
		});

		//this.add(welcomeLabel);
		//this.add(visionStatement);
		this.add(exitGameButton);
		this.add(playGameButton);
		this.add(instructionsButton);
		
		try {
			// Create the title images
			Image titleImage = ImageIO.read(new File("images/mancalaTitle.png"));
			Image titleImageIcon = new ImageIcon(titleImage).getImage();
			resizedTitleImage = titleImageIcon.getScaledInstance(500, 100, Image.SCALE_SMOOTH);
			
			Image welcomeToImage = ImageIO.read(new File("images/welcomeToTitle.png"));
			Image welcomeToImageIcon = new ImageIcon(welcomeToImage).getImage();
			resizedWelcomeToImage = welcomeToImageIcon.getScaledInstance(400, 80, Image.SCALE_SMOOTH);
			
			// Create the images, including the button images
			BufferedImage playGameBufferedImage = ImageIO.read(new File("images/playGame.png"));
			BufferedImage playGameHoverBufferedImage = ImageIO.read(new File("images/playGameHover.png"));
			BufferedImage exitGameBufferedImage = ImageIO.read(new File("images/exitGame.png"));
			BufferedImage exitGameHoverBufferedImage = ImageIO.read(new File("images/exitGameHover.png"));
			BufferedImage instructionsBufferedImage = ImageIO.read(new File("images/instructions.png"));
			BufferedImage instructionsHoverBufferedImage = ImageIO.read(new File("images/instructionsHover.png"));

			// Resize the images to correct sizes
			Image playGameImage = new ImageIcon(playGameBufferedImage).getImage();
			Image playGameHoverImage = new ImageIcon(playGameHoverBufferedImage).getImage();
			Image exitGameImage = new ImageIcon(exitGameBufferedImage).getImage();
			Image exitGameHoverImage = new ImageIcon(exitGameHoverBufferedImage).getImage();
			Image instructionsImage = new ImageIcon(instructionsBufferedImage).getImage();
			Image instructionsHoverImage = new ImageIcon(instructionsHoverBufferedImage).getImage();
		
			Image resizedPlayGameImage = playGameImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedPlayGameHoverImage = playGameHoverImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedExitGameImage = exitGameImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedExitGameHoverImage = exitGameHoverImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedInstructionsImage = instructionsImage.getScaledInstance(220, 50, Image.SCALE_SMOOTH);
			Image resizedInstructionsHoverImage = instructionsHoverImage.getScaledInstance(220, 50, Image.SCALE_SMOOTH);

			// Creating icons for the button images
			playGameIcon = new ImageIcon(resizedPlayGameImage);
			playGameHoverIcon = new ImageIcon(resizedPlayGameHoverImage);
			exitGameIcon = new ImageIcon(resizedExitGameImage);
			exitGameHoverIcon = new ImageIcon(resizedExitGameHoverImage);
			instructionsIcon = new ImageIcon(resizedInstructionsImage);
			instructionsHoverIcon = new ImageIcon(resizedInstructionsHoverImage);

			// Set the button images to the buttons
			playGameButton.setIcon(playGameIcon);
			exitGameButton.setIcon(exitGameIcon);
			instructionsButton.setIcon(instructionsIcon);
			
			for (int i = 0; i < 20; i++) {
				Marble newMarble = new Marble();
				newMarble.setXcord();
				newMarble.setYcord();
				marbleList.add(newMarble);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the images to the screen.
	 * 
	 * @param Graphics which allows images to be added to the JPanel
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		final Graphics2D g = (Graphics2D) graphics;
		
			g.drawImage(resizedTitleImage, 140, 130, null);
			g.drawImage(resizedWelcomeToImage, 190, 60, null);	
	}
}