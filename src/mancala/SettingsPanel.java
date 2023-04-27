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

/**
 * This class creates the settings panel for the mancala game. It has two
 * different settings options, single player or two player. By clicking one of
 * these buttons, the game will launch. This panel also has other buttons that
 * allow the user to switch between menus.
 *
 */
@SuppressWarnings("serial")
public class SettingsPanel extends JPanel {
	public JButton homeButton = new JButton();

	public JButton singlePlayerButton = new JButton();
	public JButton twoPlayerButton = new JButton();
	
	private Image resizedBackgroundImage;
	private Image resizedTitleImage;

	private ImageIcon homeIcon;
	private ImageIcon homeHoverIcon;
	private ImageIcon singlePlayerIcon;
	private ImageIcon singlePlayerHoverIcon;
	private ImageIcon twoPlayerIcon;
	private ImageIcon twoPlayerHoverIcon;

	/**
	 * Constructor for the settings panel
	 */
	public SettingsPanel() {
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);
		this.setBackground(new Color(228, 218, 199));

		JLabel buttonDesc = new JLabel("CHOOSE THE NUMBER OF PLAYERS:");
		buttonDesc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

		singlePlayerButton.setBounds(300, 200, 200, 100);
		twoPlayerButton.setBounds(300, 300, 200, 100);
		buttonDesc.setBounds(165, 140, 500, 100);

		// Create the "Home" Button

		homeButton.setBounds(325, 490, 150,50);
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
		
		
		// Create the "Single Player" Button
		singlePlayerButton.setBounds(240, 230, 320,90);
		singlePlayerButton.setBorderPainted(false);
		singlePlayerButton.setContentAreaFilled(false);
		singlePlayerButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				singlePlayerButton.setIcon(singlePlayerHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				singlePlayerButton.setIcon(singlePlayerIcon);
			}
		});
		
		// Create the "Two Player" Button
		twoPlayerButton.setBounds(240, 350, 320,90);
		twoPlayerButton.setBorderPainted(false);
		twoPlayerButton.setContentAreaFilled(false);
		twoPlayerButton.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				twoPlayerButton.setIcon(twoPlayerHoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				twoPlayerButton.setIcon(twoPlayerIcon);
			}
		});
		

		this.add(homeButton);
		this.add(singlePlayerButton);
		this.add(twoPlayerButton);
		this.add(buttonDesc);
		
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

			BufferedImage singlePlayerBufferedImage = ImageIO.read(new File("images/singlePlayer.png"));
			BufferedImage singlePlayerHoverBufferedImage = ImageIO.read(new File("images/singlePlayerHover.png"));
			BufferedImage twoPlayerBufferedImage = ImageIO.read(new File("images/twoPlayer.png"));
			BufferedImage twoPlayerHoverBufferedImage = ImageIO.read(new File("images/twoPlayerHover.png"));
			
			// Resize the images to correct sizes
			Image homeImage = new ImageIcon(homeBufferedImage).getImage();
			Image homeHoverImage = new ImageIcon(homeHoverBufferedImage).getImage();
			Image singlePlayerImage = new ImageIcon(singlePlayerBufferedImage).getImage();
			Image singlePlayerHoverImage = new ImageIcon(singlePlayerHoverBufferedImage).getImage();
			Image twoPlayerImage = new ImageIcon(twoPlayerBufferedImage).getImage();
			Image twoPlayerHoverImage = new ImageIcon(twoPlayerHoverBufferedImage).getImage();

			Image resizedHomeImage = homeImage.getScaledInstance(150, 50, Image.SCALE_SMOOTH);
			Image resizedHomeHoverImage = homeHoverImage.getScaledInstance(150, 50, Image.SCALE_SMOOTH);
			Image resizedSinglePlayerImage = singlePlayerImage.getScaledInstance(320, 90, Image.SCALE_SMOOTH);
			Image resizedSinglePlayerHoverImage = singlePlayerHoverImage.getScaledInstance(320, 90, Image.SCALE_SMOOTH);
			Image resizedTwoPlayerImage = twoPlayerImage.getScaledInstance(320, 90, Image.SCALE_SMOOTH);
			Image resizedTwoPlayerHoverImage = twoPlayerHoverImage.getScaledInstance(320, 90, Image.SCALE_SMOOTH);

			// Creating icons for the button images
			homeIcon = new ImageIcon(resizedHomeImage);
			homeHoverIcon = new ImageIcon(resizedHomeHoverImage);
			singlePlayerIcon = new ImageIcon(resizedSinglePlayerImage);
			singlePlayerHoverIcon = new ImageIcon(resizedSinglePlayerHoverImage);
			twoPlayerIcon = new ImageIcon(resizedTwoPlayerImage);
			twoPlayerHoverIcon = new ImageIcon(resizedTwoPlayerHoverImage);

			// Set the button images to the buttons
			homeButton.setIcon(homeIcon);
			singlePlayerButton.setIcon(singlePlayerIcon);
			twoPlayerButton.setIcon(twoPlayerIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the images to the screen.
	 * @param Graphics which allows images to be added to the JPanel
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		final Graphics2D g = (Graphics2D) graphics;

		g.drawImage(resizedBackgroundImage, 0,0, null);
		g.drawImage(resizedTitleImage, 140, 30, null);
	}
}