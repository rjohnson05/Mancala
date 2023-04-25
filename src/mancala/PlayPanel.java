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
import java.util.ArrayList;
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
 * Contains the graphics for the gameplay of Mancala. This is the main panel
 * that the user will be on while using the program, and is the panel the game
 * is played on.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
@SuppressWarnings("serial")
public class PlayPanel extends JPanel {
	/*
	 * The game begins with four marbles in each pit of the board (excluding the two
	 * stores). The user has the option to choose which pit to move by clicking on
	 * the desired pit. This pit must be on the player's side of the board. Clicking
	 * on a pit from the other player's side of the board does not cause any
	 * movement. If the movement resulting from a pit's movement results in a
	 * capture of the other player's marbles, the marbles are automatically moved to
	 * the capturing player's store. Play alternates between Player 1 and Player 2,
	 * with an instruction label indicating whose turn it is at all times throughout
	 * the game. Once there are no marbles left on either side of the board, the
	 * program is changed to an EndGame JPanel.
	 */
	private Game game = new Game();
	private final Random rand = new Random();
	private final JTextPane instructionsPane = new JTextPane();
	private final Style style = instructionsPane.addStyle("", null);
	private final StyledDocument instructionsDoc = instructionsPane.getStyledDocument();
	private JLabel p1ScoreNumber = new JLabel("0");
	private JLabel p2ScoreNumber = new JLabel("0");
	private JLabel marbleCountDisplayLabel = new JLabel("Number of Marbles:");
	private JLabel marbleCountLabel = new JLabel();

	public List<RoundButton> pitButtons = new ArrayList<>();
	public boolean singlePlayer = false;
	private boolean highlightHintsP1 = true;
	private boolean highlightHintsP2 = false;

	public JButton homeButton = new JButton();
	public JButton helpButton = new JButton();
	public JButton exitGameButton = new JButton();

	private Image resizedBackgroundImage;
	private Image resizedTitleImage;
	private Image resizedBoard;
	private Image resizedHighlightImage;
	
	private ImageIcon homeIcon;
	private ImageIcon homeHoverIcon;
	private ImageIcon helpIcon;
	private ImageIcon helpHoverIcon;
	private ImageIcon exitGameIcon;
	private ImageIcon exitGameHoverIcon;

	/**
	 * Creates a new gameplay panel for the Mancala game.
	 */
	public PlayPanel(boolean singlePlayer) {
		/*
		 * Once initialized, this panel is run until either player has won the Mancala
		 * game. This initialization ensures that the game board is reset and that all
		 * score labels are created. The textbox for the game instruction text is also
		 * created.
		 */
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);

		this.singlePlayer = singlePlayer;

		// Create the instruction area underneath the game board
		instructionsPane.setBounds(250, 410, 300, 70);
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
			System.out.println("You input an invalid location for inserting a string into the instructions JTextPane");
		}

		// Creating each player's score label
		JLabel p1ScoreLabel = new JLabel("Player 2 Score: ");
		JLabel p2ScoreLabel = new JLabel("Player 1 Score: ");
		p1ScoreLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
		p2ScoreLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
		p1ScoreLabel.setForeground(Color.WHITE);
		p2ScoreLabel.setForeground(Color.WHITE);
		p1ScoreNumber.setFont(new Font("DialogInput", Font.BOLD, 20));
		p2ScoreNumber.setFont(new Font("DialogInput", Font.BOLD, 20));
		p1ScoreNumber.setForeground(Color.WHITE);
		p2ScoreNumber.setForeground(Color.WHITE);
		p1ScoreLabel.setBounds(30, 510, 200, 25);
		p2ScoreLabel.setBounds(560, 510, 200, 25);
		p1ScoreNumber.setBounds(220, 510, 50, 25);
		p2ScoreNumber.setBounds(750, 510, 50, 25);

		this.add(instructionsPane);
		this.add(p1ScoreLabel);
		this.add(p2ScoreLabel);
		this.add(p1ScoreNumber);
		this.add(p2ScoreNumber);

		// Add the marble count display box to the panel
		this.add(marbleCountDisplayLabel);
		this.add(marbleCountLabel);

		resetBoardGraphics();

		// Create the "Home" Button
		homeButton.setBounds(10, 10, 120, 40);
		homeButton.setBorderPainted(false);
		homeButton.setContentAreaFilled(false);
		homeButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				homeButton.setIcon(homeHoverIcon);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				homeButton.setIcon(homeIcon);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// Create the "Help" Button
		helpButton.setBounds(340, 500, 90, 40);
		helpButton.setBorderPainted(false);
		helpButton.setContentAreaFilled(false);
		helpButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				helpButton.setIcon(helpHoverIcon);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				helpButton.setIcon(helpIcon);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// Create the "Exit Game" Button
		exitGameButton.setBounds(620, 10, 150, 40);
		exitGameButton.setBorderPainted(false);
		exitGameButton.setContentAreaFilled(false);
		exitGameButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				exitGameButton.setIcon(exitGameHoverIcon);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				exitGameButton.setIcon(exitGameIcon);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		this.add(helpButton);
		this.add(homeButton);
		this.add(exitGameButton);

		try {
			// Create the background image
			Image backgroundImage = ImageIO.read(new File("images/playWood.jpg"));
			Image backgroundImageIcon = new ImageIcon(backgroundImage).getImage();
			resizedBackgroundImage = backgroundImageIcon.getScaledInstance(800, 565, Image.SCALE_SMOOTH);

			// Create the title images
			Image titleImage = ImageIO.read(new File("images/mancalaTitle.png"));
			Image titleImageIcon = new ImageIcon(titleImage).getImage();
			resizedTitleImage = titleImageIcon.getScaledInstance(500, 100, Image.SCALE_SMOOTH);

			// Create the board image
			BufferedImage mainImage = ImageIO.read(new File("images/mancalaImages.png"));
			BufferedImage boardImage = mainImage.getSubimage(15, 1400, 500, 150);

			// Display the board image
			Image boardDisplayImage = new ImageIcon(boardImage).getImage();
			resizedBoard = boardDisplayImage.getScaledInstance(800, 250, Image.SCALE_SMOOTH);
			
			// Create the background image
			Image highlightImage = ImageIO.read(new File("images/highlight.png"));
			Image highlightImageIcon = new ImageIcon(highlightImage).getImage();
			resizedHighlightImage = highlightImageIcon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

			// Create the button images
			BufferedImage homeBufferedImage = ImageIO.read(new File("images/home.png"));
			BufferedImage homeHoverBufferedImage = ImageIO.read(new File("images/homeHover.png"));
			BufferedImage helpBufferedImage = ImageIO.read(new File("images/help.png"));
			BufferedImage helpHoverBufferedImage = ImageIO.read(new File("images/helpHover.png"));
			BufferedImage exitGameBufferedImage = ImageIO.read(new File("images/exitGame.png"));
			BufferedImage exitGameHoverBufferedImage = ImageIO.read(new File("images/exitGameHover.png"));

			// Resize the images to correct sizes
			Image helpImage = new ImageIcon(helpBufferedImage).getImage();
			Image helpHoverImage = new ImageIcon(helpHoverBufferedImage).getImage();
			Image exitGameImage = new ImageIcon(exitGameBufferedImage).getImage();
			Image exitGameHoverImage = new ImageIcon(exitGameHoverBufferedImage).getImage();
			Image homeImage = new ImageIcon(homeBufferedImage).getImage();
			Image homeHoverImage = new ImageIcon(homeHoverBufferedImage).getImage();

			Image resizedHelpImage = helpImage.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
			Image resizedHelpHoverImage = helpHoverImage.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
			Image resizedExitGameImage = exitGameImage.getScaledInstance(150, 40, Image.SCALE_SMOOTH);
			Image resizedExitGameHoverImage = exitGameHoverImage.getScaledInstance(150, 40, Image.SCALE_SMOOTH);
			Image resizedHomeImage = homeImage.getScaledInstance(120, 40, Image.SCALE_SMOOTH);
			Image resizedHomeHoverImage = homeHoverImage.getScaledInstance(120, 40, Image.SCALE_SMOOTH);

			// Creating icons for the button images
			helpIcon = new ImageIcon(resizedHelpImage);
			helpHoverIcon = new ImageIcon(resizedHelpHoverImage);
			exitGameIcon = new ImageIcon(resizedExitGameImage);
			exitGameHoverIcon = new ImageIcon(resizedExitGameHoverImage);
			homeIcon = new ImageIcon(resizedHomeImage);
			homeHoverIcon = new ImageIcon(resizedHomeHoverImage);

			// Set the button images to the buttons
			helpButton.setIcon(helpIcon);
			exitGameButton.setIcon(exitGameIcon);
			homeButton.setIcon(homeIcon);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Renders the game board and marbles to the screen.
	 * 
	 * @param graphics a Graphics object that draws the board and marble images to
	 *                 the JPanel
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		final Graphics2D g = (Graphics2D) graphics;

		g.drawImage(resizedTitleImage, 200, 70, null);
		g.drawImage(resizedBackgroundImage, 0, 0, null);
		g.drawImage(resizedBoard, 20, 150, null);
		

		// Draw every marble on the board
		for (Pit pit : game.getStoreList()) {
			for (Marble marble : pit.getMarbleList()) {
				g.drawImage(marble.getMarbleImage(), marble.getXcord(), marble.getYcord(), null);
			}
		}
		
		if (highlightHintsP1) {
			g.drawImage(resizedHighlightImage, 112, 282, null);
			g.drawImage(resizedHighlightImage, 197, 282, null);
			g.drawImage(resizedHighlightImage, 279, 281, null);
			g.drawImage(resizedHighlightImage, 411, 282, null);
			g.drawImage(resizedHighlightImage, 495, 279, null);
			g.drawImage(resizedHighlightImage, 580, 278, null);
		} else if (highlightHintsP2) {
			g.drawImage(resizedHighlightImage, 116, 173, null);
			g.drawImage(resizedHighlightImage, 198, 173, null);
			g.drawImage(resizedHighlightImage, 279, 173, null);
			g.drawImage(resizedHighlightImage, 408, 171, null);
			g.drawImage(resizedHighlightImage, 488, 169, null);
			g.drawImage(resizedHighlightImage, 569, 168, null);
		}
	}

	/**
	 * Resets the game board graphics to the initial Mancala game state.
	 */
	public void resetBoardGraphics() {
		/*
		 * Creates buttons for each pit on the board, acting as the boundary for the
		 * pit. These buttons allow for the determination of min/max x and y placement
		 * positions for each pit. The creation of these buttons also allows the user to
		 * choose which pit to move by clicking on the desired pit. The coordinates for
		 * each marble is set to a random value within its starting pit.
		 */
		game.resetBoard();
		pitButtons.clear();
		changeInstructionText(false);
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
				pitButton.setText(" ");
				pitButton.setBounds(125 + (84 * i), 295 - (i), 73, 74);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				pitButton.setFocusable(false);
				this.add(pitButton);
				pitButtons.add(pitButton);
			} else if (i < 6) {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setText(" ");
				pitButton.setBounds(427 + (83 * (i - 3)), 300 - (2 * i), 71, 70);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				pitButton.setFocusable(false);
				this.add(pitButton);
				pitButtons.add(pitButton);
			} else if (i == 6) {
				// This is a rectangular button instead of a circular button
				// This button still needs rotated for greater precision
				RoundButton pitButton = new RoundButton(i);
				pitButton.setText(" ");
				pitButton.setBounds(666, 180, 84, 180);
				currentPit.setBoundary(pitButton);
				pitButton.setOpaque(false);
				pitButton.setContentAreaFilled(false);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				pitButtons.add(pitButton);
			} else if (i > 6 && i < 10) {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setText(" ");
				pitButton.setBounds((584 - (-81 * (7 - i))), 183 + (-2 * (7 - i)), 69, 67);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				if (!singlePlayer) {
					addButtonListeners(pitButton);
					pitButtons.add(pitButton);
				}
				pitButton.setFocusable(false);
				this.add(pitButton);
			} else if (i < 13) {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setText(" ");
				pitButton.setBounds(294 - (-81 * (10 - i)), 185 + (-1 * (10 - i)), 70, 70);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				if (!singlePlayer) {
					addButtonListeners(pitButton);
					pitButtons.add(pitButton);
				}
				pitButton.setFocusable(false);
				this.add(pitButton);
			} else {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setText(" ");
				pitButton.setBounds(43, 186, 75, 183);
				currentPit.setBoundary(pitButton);
				pitButton.setOpaque(false);
				pitButton.setContentAreaFilled(false);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				pitButtons.add(pitButton);
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

		repaint();
	}

	/**
	 * Moves all of the marble images in a specified pit to the appropriate pits.
	 * 
	 * @param selectedPitIndex an integer designating the index (within the game's
	 *                         list of pits) of the pit to be moved
	 * @return the boolean true if the marbles images from the designated pit are
	 *         moved successfully; false if there are no marbles in the pit to move
	 */
	public int movePit(int selectedPitIndex, boolean simulated) {
		/*
		 * All marble images from a single pit are placed into the appropriate pits. If
		 * the designated pit is moved successfully, the boolean true is returned. If
		 * there are no marbles in the pit to move, false is returned
		 */

		Pit selectedPit = game.getStoreList().get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleList().size();
		// Each marble within the selected pit is moved to the subsequent pits
		int nextPitIndex = selectedPitIndex;

		for (int i = 0; i < marbleCount; i++) {
			Marble marble = selectedPit.getMarbleList().get(i);
			nextPitIndex = nextPitIndex + 1;

			// If Player 1 is moving, the marbles skip past Player 2's store
			if ((nextPitIndex == 13 && game.getCurrentPlayer() == 0)
					|| (nextPitIndex == 6 && game.getCurrentPlayer() == 1)) {
				nextPitIndex = nextPitIndex + 1;
			}

			// If the player selects a pit that places marbles beyond the end of the marble
			// list, the marble will be placed into the pits at the beginning of the list
			if (nextPitIndex > 13) {
				nextPitIndex = Math.abs(13 - (nextPitIndex - 1));
			}
			if (!simulated) {
				// Changes the coordinates of each marble in the selected pit to a random
				// coordinate within the pit the marble is being moved to
				Pit nextPit = game.getStoreList().get(nextPitIndex);
				marble.setXcord(rand
						.nextInt(((nextPit.getBoundary().getBounds().x + nextPit.getBoundary().getBounds().width
								- marble.getMarbleImage().getWidth(getFocusCycleRootAncestor()) - 5))
								- (nextPit.getBoundary().getBounds().x + 5))
						+ (nextPit.getBoundary().getBounds().x + 5));
				marble.setYcord(rand
						.nextInt(((nextPit.getBoundary().getBounds().y + nextPit.getBoundary().getBounds().height
								- marble.getMarbleImage().getHeight(getFocusCycleRootAncestor()) - 5))
								- (nextPit.getBoundary().getBounds().y + 5))
						+ (nextPit.getBoundary().getBounds().y + 5));
			}

		}

		int endPitIndex = nextPitIndex;

		if (!simulated) {
			game.move(selectedPitIndex);
		}

		return endPitIndex;
	}

	/**
	 * Sets the coordinates of all marbles in a captured pit to random coordinates
	 * within the capturing player's store.
	 * 
	 * @param selectedPitIndex an integer designating the index (within the game's
	 *                         list of pits) of the pit to be moved
	 */
	public void moveCapturedMarbles(int endPitIndex) {
		/*
		 * A pit is considered captured if it lies across from an empty pit that
		 * receives the last marble on a player's side of the board. All marbles from
		 * this captured pit are moved into the store of the player that most recently
		 * moved.
		 */

		// Determine which store the marbles should be moved into
		Pit store = game.getStoreList().get(6);
		if (game.getCurrentPlayer() == 1) {
			store = game.getStoreList().get(13);
		}

		// Move the marbles in the captured pit into the player's store
		Pit capturedPit = game.getStoreList().get(Math.abs(12 - endPitIndex));

		for (Marble marble : capturedPit.getMarbleList()) {
			marble.setXcord(rand.nextInt(((store.getBoundary().getBounds().x + store.getBoundary().getBounds().width
					- marble.getMarbleImage().getWidth(getFocusCycleRootAncestor()) - 5))
					- (store.getBoundary().getBounds().x + 5)) + (store.getBoundary().getBounds().x + 5));
			marble.setYcord(rand.nextInt(((store.getBoundary().getBounds().y + store.getBoundary().getBounds().height
					- marble.getMarbleImage().getHeight(getFocusCycleRootAncestor()) - 5))
					- (store.getBoundary().getBounds().y + 5)) + (store.getBoundary().getBounds().y + 5));
		}

		// Move the marble in the capturing pit into the player's store
		Marble capturingMarble = game.getStoreList().get(endPitIndex).getMarbleList().get(0);
		capturingMarble
				.setXcord(rand.nextInt(((store.getBoundary().getBounds().x + store.getBoundary().getBounds().width
						- capturingMarble.getMarbleImage().getWidth(getFocusCycleRootAncestor()) - 5))
						- (store.getBoundary().getBounds().x + 5)) + (store.getBoundary().getBounds().x + 5));
		capturingMarble
				.setYcord(rand.nextInt(((store.getBoundary().getBounds().y + store.getBoundary().getBounds().height
						- capturingMarble.getMarbleImage().getHeight(getFocusCycleRootAncestor()) - 5))
						- (store.getBoundary().getBounds().y + 5)) + (store.getBoundary().getBounds().y + 5));

		repaint();
	}

	/**
	 * Sets the coordinates of the marble in a capturing pit to random coordinates
	 * within the capturing player's store.
	 * 
	 * @param selectedPitIndex an integer designating the index (within the game's
	 *                         list of pits) of the pit to be moved
	 */
	public void moveCapturingMarbles(int endPitIndex) {
		/*
		 * A capturing pit is an empty pit on a player's side of the board that receives
		 * the last marble after moving a pit and also lies across from a pit containing
		 * marbles.
		 */

		Pit currentPit = game.getStoreList().get(endPitIndex);

		// Determine which store the marbles should be moved into
		Pit store = game.getStoreList().get(6);
		if (game.getCurrentPlayer() == 1) {
			store = game.getStoreList().get(13);
		}

		// Move the marbles across from the captured pit into the player's store
		Marble capturingMarble = currentPit.getMarbleList().get(0);
		capturingMarble
				.setXcord(rand.nextInt(((store.getBoundary().getBounds().x + store.getBoundary().getBounds().width
						- capturingMarble.getMarbleImage().getWidth(getFocusCycleRootAncestor()) - 5))
						- (store.getBoundary().getBounds().x + 5)) + (store.getBoundary().getBounds().x + 5));
		capturingMarble
				.setYcord(rand.nextInt(((store.getBoundary().getBounds().y + store.getBoundary().getBounds().height
						- capturingMarble.getMarbleImage().getHeight(getFocusCycleRootAncestor()) - 5))
						- (store.getBoundary().getBounds().y + 5)) + (store.getBoundary().getBounds().y + 5));
	}

	/**
	 * Changes the text in the instruction area, alerting the player to whose turn
	 * it is and whether they receive an extra move, as well as the score labels for
	 * each player.
	 * 
	 * @param getsAnotherTurn a boolean indicating whether the player should be
	 *                        allowed another move (true if receiving another turn;
	 *                        false otherwise)
	 */
	public void changeInstructionText(boolean getsAnotherTurn) {
		if (getsAnotherTurn) {
			if (game.getCurrentPlayer() == 0) {
				instructionsPane.setText("Player 1 gets an extra move!\nChoose a pit.");
			} else {
				instructionsPane.setText("Player 2 gets an extra move!\nChoose a pit.");
			}
		} else if (game.getCurrentPlayer() == 1) {
			if (highlightHintsP2) {
				instructionsPane.setText("Player 2, it's your turn.\nSelect one of your highlighted pits.");
			} else {
				instructionsPane.setText("Player 2, it's your turn.\nChoose a pit.");
			}
		} else {
			if (highlightHintsP1) {
				instructionsPane.setText("Player 1, it's your turn.\nSelect one of your highlighted pits.");
			} else {
				instructionsPane.setText("Player 1, it's your turn.\nChoose a pit.");
			}
		}

		p1ScoreNumber.setText(String.valueOf(game.getStoreList().get(13).getMarbleList().size()));
		p2ScoreNumber.setText(String.valueOf(game.getStoreList().get(6).getMarbleList().size()));
	}

	/**
	 * Returns an integer designating the winning player for the game.
	 * 
	 * @return int returns 0 if Player 1 won and 1 if Player 2 won
	 */
	public int getWinner() {
		return game.getWinner();
	}

	/**
	 * Contains the logic for the computer opponent, deciding which pit would gain
	 * the most number of marbes for itself.
	 * 
	 * @return int an integer representing the index of the best pit to be moved by
	 *         the computer opponent
	 */
	public int chooseOpponentPit() {
		// Chooses a random index in case no better move is found
		int bestPitIndex = rand.nextInt(13 - 7) + 7;
		while (game.getStoreList().get(bestPitIndex).getMarbleList().size() == 0) {
			bestPitIndex = rand.nextInt(13 - 7) + 7;
		}

		// Determine if there are any opportunities for the other player to gain a
		// capture
		int greatestMarbleLossPitIndex = 0;
		for (int i = 0; i < 6; i++) {
			game.setCurrentPlayer(0);

			int endPitIndex = movePit(i, true);
			Marble marble = new Marble();
			if (game.getStoreList().get(i).getMarbleList().size() > 0) {
				game.getStoreList().get(endPitIndex).getMarbleList().add(marble);
			}

			if (game.checkCapture(endPitIndex)) {
				int possibleMarbleLoss = game.getStoreList().get(12 - endPitIndex).getMarbleList().size();
				if (possibleMarbleLoss > game.getStoreList().get(greatestMarbleLossPitIndex).getMarbleList().size()) {
					greatestMarbleLossPitIndex = 12 - endPitIndex;
				}
			}

			if (game.getStoreList().get(i).getMarbleList().size() > 0) {
				game.getStoreList().get(endPitIndex).getMarbleList().remove(marble);
			}
			game.setCurrentPlayer(1);
		}

		// Determine if there are any opportunities to gain a capture
		int greatestMarbleGainPitIndex = 0;
		for (int i = 7; i < 13; i++) {
			int endPitIndex = movePit(i, true);
			Marble marble = new Marble();
			if (game.getStoreList().get(i).getMarbleList().size() > 0) {
				game.getStoreList().get(endPitIndex).getMarbleList().add(marble);
			}
			if (game.checkCapture(endPitIndex)) {
				int possibleMarbleGain = game.getStoreList().get(12 - i).getMarbleList().size() + 1;
				if (possibleMarbleGain > game.getStoreList().get(greatestMarbleGainPitIndex).getMarbleList().size()) {
					greatestMarbleGainPitIndex = i;
				}
			}
			if (game.getStoreList().get(i).getMarbleList().size() > 0) {
				game.getStoreList().get(endPitIndex).getMarbleList().remove(marble);
			}
		}

		if (greatestMarbleGainPitIndex > 0) {
		}

		// Make whichever move will result in a greater number of marbles gained:
		// blocking the other player's capture or gaining a capture for yourself
		if (greatestMarbleLossPitIndex > greatestMarbleGainPitIndex) {
			bestPitIndex = greatestMarbleLossPitIndex;
			return bestPitIndex;
		} else if (greatestMarbleGainPitIndex > greatestMarbleLossPitIndex) {
			bestPitIndex = greatestMarbleGainPitIndex;
			return bestPitIndex;
		}

		// If there's a move that will allow for another turn, make it
		for (int i = 7; i < 13; i++) {
			if (movePit(i, true) == 6 || movePit(i, true) == 13) {
				bestPitIndex = i;
				return bestPitIndex;
			}
		}

		return bestPitIndex;
	}

	/**
	 * Moves the marbles in the selected pit to the appropriate spaces, in both the
	 * list storing pit information and the screen graphics.
	 * 
	 * @param selectedPitIndex an integer representing the location of the desired
	 *                         pit within the list storing pit information
	 */
	public void playerMove(int selectedPitIndex) {
		// After a player chooses a pit, play moves to the other player
		game.setsAnotherMove(selectedPitIndex);
		boolean getsAnotherTurn = game.getsAnotherMove();
		int endPitIndex = movePit(selectedPitIndex, false);
		if (endPitIndex != selectedPitIndex) {
			if (game.checkCapture(endPitIndex)) {
				moveCapturedMarbles(endPitIndex);
				game.moveCapturedMarbles(endPitIndex);

			}
			if (!getsAnotherTurn) {
				game.switchPlayer();
				// Turns off the highlighting hints after each player's first turn
				if (highlightHintsP1) {
					highlightHintsP2 = true;
				} else {
					highlightHintsP2 = false;
				}
				highlightHintsP1 = false;
			}
			changeInstructionText(getsAnotherTurn);
		}

		if (game.hasWinner()) {
			game.setWinner();
			repaint();

		}
		repaint();

	}

	/**
	 * Adds a button listener to a button.
	 * 
	 * @param pitButton a JButton object that specifies which button the mouse
	 *                  listener should be added to
	 */
	public void addButtonListeners(JButton pitButton) {
		MouseListener buttonListener = new MouseListener() {
			/*
			 * This is used for the pit buttons to indicate which pit has been clicked on,
			 * as well as specifying a range of coordinates for a particular pit. Specifies
			 * the actions taken when a pit button is clicked. The button must lie on the
			 * current player's side for the click to have an impact. If a pit button on the
			 * current player's side is clicked, the marbles in the clicked pit are moved as
			 * necessary. The panel is then repainted to show the updated positions of the
			 * moved marbles.
			 */
			public void mouseClicked(MouseEvent e) {
				// On a mouse click, the marbles are moved and the player is changed
				RoundButton buttonClicked = (RoundButton) e.getSource();
				Pit currentPit = game.getStoreList().get(buttonClicked.getPitNumber());
				int selectedPitIndex = buttonClicked.getPitNumber();

				// Only allows player to choose a pit on their side of the board
				if (currentPit.getSide() == game.getCurrentPlayer()) {
					playerMove(selectedPitIndex);
				}

				repaint();
			}

			/*
			 * Specifies the actions to be taken when the mouse hovers over a pit button.
			 * When hovering over a pit, a pop up window is displayed, showing the number of
			 * marbles in the pit. If the pit is on the current player's side of the board,
			 * the mouse cursor is changed to indicate that the pit is a valid move.
			 */
			public void mouseEntered(MouseEvent e) {
				// Changes the cursor to a hand if the mouse hovers over one of their pits
				RoundButton buttonClicked = (RoundButton) e.getSource();
				Pit currentPit = game.getStoreList().get(buttonClicked.getPitNumber());

				if (currentPit.getSide() == game.getCurrentPlayer()) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				// Creates pop-up text to display the number of marbles in the pit being hovered
				// over
				buttonClicked.setToolTipText("Marble Count: " + currentPit.getMarbleList().size());
			}

			/*
			 * Specifies the actions to be taken when the mouse stops hovering over a pit
			 * button. The mouse is changed back to the default pointer cursor.
			 */
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		};

		pitButton.addMouseListener(buttonListener);
	}

	/**
	 * Returns the instance of the Game
	 * 
	 * @return the instance of the Game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Returns whether the game is a single-player game
	 * 
	 * @return true if it is a single-player game and false if it is a two-player
	 *         game
	 */
	public boolean getSinglePlayer() {
		return singlePlayer;
	}
	
	/**
	 * Sets the boolean specifying whether the highlight hints should be shown on Player 2's side.
	 * 
	 * @param highlightHint true if hightlight hints should be shown over Player 2's pits and false otherwise
	 */
	public void setHighlightHintsP2(boolean highlightHint) {
		highlightHintsP2 = highlightHint;
	}
	
	public boolean getHighlightHintsP2() {
		return highlightHintsP2;
	}
}