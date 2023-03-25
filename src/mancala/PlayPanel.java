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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * @title PlayPanel.java
 * @author Ryan Johnson, Hank Rugg
 * @description Contains the graphics for the gameplay of Mancala. The game
 *              begins with four marbles in each pit of the board (excluding the
 *              two stores). The user has the option to choose from which pit
 *              they move the marbles, which are moved to the subsequent pits.
 */
public class PlayPanel extends JPanel {

	private Game game;
	private Random rand = new Random();
	JTextPane instructionsPane = new JTextPane();
	Style style = instructionsPane.addStyle("", null);
	StyledDocument instructionsDoc = instructionsPane.getStyledDocument();
	JLabel p1ScoreNumber = new JLabel("0");
	JLabel p2ScoreNumber = new JLabel("0");

	public PlayPanel() {
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);

		resetBoard();

		// Create the instruction area underneath the game board
		instructionsPane.setBounds(250, 360, 300, 70);
		instructionsPane.setEditable(false);
		// Changes the border around the instruction area
		Border blackBorder = BorderFactory.createLineBorder(Color.black);
		instructionsPane.setBorder(blackBorder);

		// Centers the text
		SimpleAttributeSet centeredAttribute = new SimpleAttributeSet();
		StyleConstants.setAlignment(centeredAttribute, StyleConstants.ALIGN_CENTER);
		instructionsPane.setParagraphAttributes(centeredAttribute, false);
		// Sets the font for the text
		Font instructionsFont = new Font("Serif", Font.BOLD, 20);
		instructionsPane.setFont(instructionsFont);
		StyleConstants.setForeground(style, Color.black); // Changes the color of the text

		try {
			instructionsDoc.insertString(instructionsDoc.getLength(), "You're up first, Player 1!\nChoose a pit.",
					style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		this.add(instructionsPane);

		// Creating each player's score label
		JLabel p1ScoreLabel = new JLabel("Player 2 Score: ");
		JLabel p2ScoreLabel = new JLabel("Player 1 Score: ");
		p1ScoreLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
		p2ScoreLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
		p1ScoreNumber.setFont(new Font("DialogInput", Font.BOLD, 20));
		p2ScoreNumber.setFont(new Font("DialogInput", Font.BOLD, 20));
		p1ScoreLabel.setBounds(30, 460, 200, 25);
		p2ScoreLabel.setBounds(560, 460, 200, 25);
		p1ScoreNumber.setBounds(220, 460, 50, 25);
		p2ScoreNumber.setBounds(750, 460, 50, 25);
		this.add(p1ScoreLabel);
		this.add(p2ScoreLabel);
		this.add(p1ScoreNumber);
		this.add(p2ScoreNumber);
	}

	/**
	 * @description Renders the board and marbles to the screen
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		final Graphics2D g = (Graphics2D) graphics;

		try {
			// Create the board image
			BufferedImage mainImage = ImageIO.read(new File("mancalaImages.png"));
			BufferedImage boardImage = mainImage.getSubimage(15, 1400, 500, 150);

			// Display the board image
			Image boardDisplayImage = new ImageIcon(boardImage).getImage();
			Image resizedBoard = boardDisplayImage.getScaledInstance(800, 250, Image.SCALE_SMOOTH);
			g.drawImage(resizedBoard, 20, 100, null);

			// Draw every marble on the board
			for (Pit pit : game.getStoreList()) {
				for (Marble marble : pit.getMarbleList()) {
					g.drawImage(marble.getMarbleImage(), marble.getXcord(), marble.getYcord(), null);
				}
			}

			// Create the Mancala title image
			BufferedImage titleImage = ImageIO.read(new File("mancalaTitle.png"));
			Image titleImageIcon = new ImageIcon(titleImage).getImage();
			Image resizedTitleImage = titleImageIcon.getScaledInstance(400, 60, Image.SCALE_SMOOTH);
			g.drawImage(resizedTitleImage, 200, 20, null);

			// Set the background color
			this.setBackground(new Color(228, 218, 199));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description Creates buttons for each pit on the board, acting as the
	 *              boundary for the pit. These buttons allow for the determination
	 *              of min/max x and y placement positions for each pit. The marble
	 *              images are placed within the min/max coordinates for its
	 *              designated pit. The creation of these buttons also allows the
	 *              user to choose which pit to move by clicking on the desired pit.
	 */
	public void resetBoard() {
		game = new Game();
		// Assign coordinates to each of the pits for showing the marble images in the
		// correct pits
		List<Pit> storeList = game.getStoreList();
		// Adds a circular button as a boundary for each pit, becoming an area to place
		// all marbles images in the given pit. This also allows the user to click on
		// individual pits.
		for (int i = 0; i < storeList.size(); i++) {
			Pit currentPit = storeList.get(i);
			if (i < 3) {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setBounds(125 + (84 * i), 245 - (i), 73, 74);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				this.add(pitButton);
			} else if (i < 6) {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setBounds(427 + (83 * (i - 3)), 250 - (2 * i), 71, 70);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				this.add(pitButton);
			} else if (i == 6) {
				// This is a rectangular button instead of a circular button
				// This button still needs rotated for greater precision
				JButton pitButton = new JButton();
				pitButton.setBounds(666, 130, 84, 180);
				currentPit.setBoundary(pitButton);
				pitButton.setOpaque(false);
				pitButton.setContentAreaFilled(false);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
			} else if (i > 6 && i < 10) {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setBounds((584 - (-81 * (7 - i))), 133 + (-2 * (7 - i)), 69, 67);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				this.add(pitButton);
			} else if (i < 13) {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setBounds(294 - (-81 * (10 - i)), 135 + (-1 * (10 - i)), 70, 70);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				this.add(pitButton);
			} else {
				JButton pitButton = new JButton();
				pitButton.setBounds(43, 136, 75, 183);
				currentPit.setBoundary(pitButton);
				pitButton.setOpaque(false);
				pitButton.setContentAreaFilled(false);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
			}
		}

		// Setting the initial coordinates of each marble.
		// The coordinates of the marbles are random within the coordinates of their
		// initial pit
		for (Pit pit : game.getStoreList()) {
			JButton pitBounds = pit.getBoundary();
			for (Marble marble : pit.getMarbleList()) {
				marble.setXcord(rand.nextInt(((pitBounds.getBounds().x + pitBounds.getBounds().width
						- marble.getMarbleImage().getWidth(getFocusCycleRootAncestor()) - 5))
						- (pitBounds.getBounds().x + 5)) + (pitBounds.getBounds().x + 5));
				marble.setYcord(rand.nextInt(((pitBounds.getBounds().y + pitBounds.getBounds().height
						- marble.getMarbleImage().getHeight(getFocusCycleRootAncestor()) - 5))
						- (pitBounds.getBounds().y + 5)) + (pitBounds.getBounds().y + 5));
			}
		}
	}

	public boolean movePit(int selectedPitIndex) {
		Pit selectedPit = game.getStoreList().get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleList().size();
		// Each marble within the selected pit is moved to the subsequent pits
		for (int i = 1; i < marbleCount + 1; i++) {
			Marble marble = selectedPit.getMarbleList().get(i - 1);
			int nextPitIndex = selectedPitIndex + i;

			// If the player selects a pit that places marbles beyond the end of the marble
			// list,
			// the marble will be placed into the pits at the beginning of the list
			if (nextPitIndex > 13) {
				nextPitIndex = Math.abs(13 - (nextPitIndex - 1));
			}

			// Changes the coordinates of each marble in the selected pit to a random
			// coordinate
			// within the pit the marble is being moved to
			Pit nextPit = game.getStoreList().get(nextPitIndex);
			marble.setXcord(rand.nextInt(((nextPit.getBoundary().getBounds().x + nextPit.getBoundary().getBounds().width
					- marble.getMarbleImage().getWidth(getFocusCycleRootAncestor()) - 5))
					- (nextPit.getBoundary().getBounds().x + 5)) + (nextPit.getBoundary().getBounds().x + 5));
			marble.setYcord(
					rand.nextInt(((nextPit.getBoundary().getBounds().y + nextPit.getBoundary().getBounds().height
							- marble.getMarbleImage().getHeight(getFocusCycleRootAncestor()) - 5))
							- (nextPit.getBoundary().getBounds().y + 5)) + (nextPit.getBoundary().getBounds().y + 5));
		}

		return game.move(selectedPitIndex);
	}

	/**
	 * 
	 * @param selectedPitIndex An integer representing the index of the chosen pit
	 *                         within storeList (the list of pits contained by the
	 *                         board)
	 * @description Changes the text in the instruction area, alerting the player to
	 *              whose turn it is and when they receive an extra move, as well as
	 *              the score labels for each player
	 */
	public void changeInstructionText(boolean getsAnotherTurn) {
		if (getsAnotherTurn) {
			if (game.getCurrentPlayer() == 0) {
				instructionsPane.setText("Player 1 gets an extra move!\nChoose a pit.");
			} else {
				instructionsPane.setText("Player 2 gets an extra move!\nChoose a pit.");
			}
		} else if (game.getCurrentPlayer() == 0) {
			instructionsPane.setText("Player 2, it's your turn.\nChoose a pit.");
		} else {
			instructionsPane.setText("Player 1, it's your turn.\nChoose a pit.");
		}

		p1ScoreNumber.setText(String.valueOf(game.getStoreList().get(13).getMarbleList().size()));
		p2ScoreNumber.setText(String.valueOf(game.getStoreList().get(6).getMarbleList().size()));
	}

	public void addButtonListeners(JButton pitButton) {
		MouseListener buttonListener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				// On a mouse click, the marbles are moved and the player is changed
				RoundButton buttonClicked = (RoundButton) e.getSource();
				Pit currentPit = game.getStoreList().get(buttonClicked.getPitNumber());
				int selectedPitIndex = buttonClicked.getPitNumber();

				// Only allows player to choose a pit on their side of the board
				if (currentPit.getSide() == game.getCurrentPlayer()) {
					// After a player chooses a pit, play moves to the other player
					boolean getsAnotherTurn = game.getsAnotherMove(selectedPitIndex);
					if (movePit(selectedPitIndex)) {
						changeInstructionText(getsAnotherTurn);
						if (!getsAnotherTurn) {
							game.switchPlayer();
						}
					}
					repaint();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Changes the cursor to a hand if the mouse hovers over one of their pits
				RoundButton buttonClicked = (RoundButton) e.getSource();
				Pit currentPit = game.getStoreList().get(buttonClicked.getPitNumber());

				if (currentPit.getSide() == game.getCurrentPlayer()) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		};

		pitButton.addMouseListener(buttonListener);
	}
}
