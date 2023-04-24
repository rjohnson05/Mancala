package mancala;

import java.awt.Color;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This class creates the instructions panel for the mancala game. It displays
 * the game rules and how to play this version. It also has buttons that allow
 * the user to switch between each menu.
 *
 * @author Ryan Johnson, Hank Rugg
 */
@SuppressWarnings("serial")
public class InstructionsPanel extends JPanel {
	public JButton homeButton = new JButton();
	public JButton playGameButton = new JButton();
	public JButton exitGameButton = new JButton();
	
	private Image resizedBackgroundImage;
	private Image resizedTitleImage;
	
	private ImageIcon playGameIcon;
	private ImageIcon playGameHoverIcon;
	private ImageIcon exitGameIcon;
	private ImageIcon exitGameHoverIcon;
	private ImageIcon homeIcon;
	private ImageIcon homeHoverIcon;

	/**
	 * Constructor for the instructions panel.
	 */
	public InstructionsPanel() {
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);
		this.setBackground(new Color(228, 218, 199));

		JTextArea rules = new JTextArea("RULES:");
		JTextArea rulesListed = new JTextArea("1. Mancala is a two-player game. There are 6 pits on each side of the board \n"
				+ "     and 1 store on opposite ends of the board. All pits on the bottom half of\n"
				+ "     the board belong to Player 1, as well as the right-hand store. All of the \n"
				+ "     upper pits and the left-hand store belong to Player 2.\n\n"
				+ "2. The current player must click on one of their own pits. This will move\n"
				+ "     the marbles from that pit around the board in a counter-clockwise direction.\n\n"
				+ "3. If the last marble from your selected pit lands in your store, you get\n"
				+ "     another turn.\n\n"
				+ "4. If the last marble from your selected pit lands in an empty pit across from\n"
				+ "     an opponent’s occupied pit, you make a capture! All marbles in the pit \n"
				+ "     directly opposite the pit receiving your last marble are moved to your\n"
				+ "     store, as well as the marble making the capture.\n\n"
				+ "5. The game is over when all of the pits on one side of the board are empty.\n\n"
				+ "6. The player with the highest number of marbles in their store at the end\n"
				+ "     of the game is the winner!");

		rules.setEditable(false);
		rules.setBackground(new Color(228, 218, 199));
		rules.setOpaque(false);
		rules.setFont(new Font("Georgia", Font.BOLD, 20));
		rules.setBounds(350, 150, 100, 55);

		rulesListed.setEditable(false);
		rulesListed.setBackground(new Color(228, 218, 199));
		rulesListed.setOpaque(false);
		rulesListed.setFont(new Font("Georgia", Font.BOLD , 13));
		rulesListed.setBounds(130, 180, 800, 400);

		// Create the "Play Game" Button
		playGameButton.setBounds(130, 490, 180,50);
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
		
		// Create the "Home" Button
		homeButton.setBounds(320, 490, 150,50);
		homeButton.setBorderPainted(false);
		homeButton.setContentAreaFilled(false);
		homeButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				homeButton.setIcon(homeHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				homeButton.setIcon(homeIcon);
			}
		});
		
		// Create the "Exit Game" Button
		exitGameButton.setBounds(480, 490, 180,50);
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
		
		this.add(rules);
		this.add(exitGameButton);
		this.add(playGameButton);
		this.add(homeButton);
		this.add(rulesListed);

		try {
			// Create the background image
			Image backgroundImage = ImageIO.read(new File("images/wood.jpg"));
			Image backgroundImageIcon = new ImageIcon(backgroundImage).getImage();
			resizedBackgroundImage = backgroundImageIcon.getScaledInstance(800,565, Image.SCALE_SMOOTH);
			
			// Create the title images
			Image titleImage = ImageIO.read(new File("images/mancalaTitle.png"));
			Image titleImageIcon = new ImageIcon(titleImage).getImage();
			resizedTitleImage = titleImageIcon.getScaledInstance(500, 100, Image.SCALE_SMOOTH);
			
			// Create the button images
			BufferedImage homeBufferedImage = ImageIO.read(new File("images/home.png"));
			BufferedImage homeHoverBufferedImage = ImageIO.read(new File("images/homeHover.png"));
			BufferedImage playGameBufferedImage = ImageIO.read(new File("images/playGame.png"));
			BufferedImage playGameHoverBufferedImage = ImageIO.read(new File("images/playGameHover.png"));
			BufferedImage exitGameBufferedImage = ImageIO.read(new File("images/exitGame.png"));
			BufferedImage exitGameHoverBufferedImage = ImageIO.read(new File("images/exitGameHover.png"));
			
			// Resize the images to correct sizes
			Image playGameImage = new ImageIcon(playGameBufferedImage).getImage();
			Image playGameHoverImage = new ImageIcon(playGameHoverBufferedImage).getImage();
			Image exitGameImage = new ImageIcon(exitGameBufferedImage).getImage();
			Image exitGameHoverImage = new ImageIcon(exitGameHoverBufferedImage).getImage();
			Image homeImage = new ImageIcon(homeBufferedImage).getImage();
			Image homeHoverImage = new ImageIcon(homeHoverBufferedImage).getImage();
		
			Image resizedPlayGameImage = playGameImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedPlayGameHoverImage = playGameHoverImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedExitGameImage = exitGameImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedExitGameHoverImage = exitGameHoverImage.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
			Image resizedHomeImage = homeImage.getScaledInstance(150, 50, Image.SCALE_SMOOTH);
			Image resizedHomeHoverImage = homeHoverImage.getScaledInstance(150, 50, Image.SCALE_SMOOTH);

			// Creating icons for the button images
			playGameIcon = new ImageIcon(resizedPlayGameImage);
			playGameHoverIcon = new ImageIcon(resizedPlayGameHoverImage);
			exitGameIcon = new ImageIcon(resizedExitGameImage);
			exitGameHoverIcon = new ImageIcon(resizedExitGameHoverImage);
			homeIcon = new ImageIcon(resizedHomeImage);
			homeHoverIcon = new ImageIcon(resizedHomeHoverImage);

			// Set the button images to the buttons
			playGameButton.setIcon(playGameIcon);
			exitGameButton.setIcon(exitGameIcon);
			homeButton.setIcon(homeIcon);
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

		g.drawImage(resizedBackgroundImage, 0,0, null);
		g.drawImage(resizedTitleImage, 140, 30, null);
	}
}