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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WelcomePanel extends JPanel {

	private Game game = new Game();
	private Random rand = new Random();

	public WelcomePanel() {
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);
        JPanel panel=new JPanel();  
        panel.setBounds(40,80,200,200);    
		this.setBackground(new Color(228, 218, 199));
		
        
		JButton instructions = new JButton("Instructions");
		JButton playGame = new JButton("Play Game");
        JButton quitGame = new JButton("Quit");
		JLabel welcomeLabel = new JLabel("Welcome to");
		JTextArea visionStatement = new JTextArea("Through the creation of a computer-based Mancala game, we are bringing one of the oldest\n"
				+ "board games to the latest generation. Unlike many modern mindless computer games, this\n"
				+ "game seeks to improve the memory and strategic abilities of all those seeking to better their\n"
				+ "mental skills.");
		
		visionStatement.setEditable(false);
		visionStatement.setBackground(new Color(228, 218, 199));

		welcomeLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
		visionStatement.setFont(new Font("DialogInput", Font.BOLD, 10));
		quitGame.setFont(new Font("DialogInput", Font.BOLD, 10));
		playGame.setFont(new Font("DialogInput", Font.BOLD, 10));
		instructions.setFont(new Font("DialogInput", Font.BOLD, 10));
		
		

		welcomeLabel.setBounds(325, 40, 200, 25);
		visionStatement.setBounds(150, 200, 500, 55);
		quitGame.setBounds(450, 450, 100, 35);
		playGame.setBounds(350, 450, 100, 35);
		instructions.setBounds(250, 450, 100, 35);

		
		
		this.add(welcomeLabel);
		this.add(visionStatement);
		this.add(quitGame);
		this.add(playGame);
		this.add(instructions);
  
		addButtonListeners(quitGame);
		addButtonListeners(playGame);
		addButtonListeners(instructions);

	}

	/**
	 * 
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
			
			// Create the board image
			BufferedImage mainImage = ImageIO.read(new File("mancalaImages.png"));
			BufferedImage boardImage = mainImage.getSubimage(15, 1400, 500, 150);

			// Display the board image
			Image boardDisplayImage = new ImageIcon(boardImage).getImage();
			Image resizedBoard = boardDisplayImage.getScaledInstance(400, 125, Image.SCALE_SMOOTH);
			g.drawImage(resizedBoard, 200, 300, null);

		} catch (IOException e) {
			e.printStackTrace();
		}

 
	}


	public void addButtonListeners(JButton button) {
		MouseListener buttonListener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		};

		button.addMouseListener(buttonListener);
	}

}
