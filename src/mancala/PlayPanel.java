package mancala;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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
 * Contains the graphics for the gameplay of Mancala.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
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
	private List<String> keysTyped = new ArrayList<String>();

	/**
	 * Creates a new gameplay panel for the Mancala game.
	 */
	public PlayPanel() {
		/*
		 * Once initialized, this panel is run until either player has won the Mancala
		 * game. This initialization ensures that the game board is reset, and that all
		 * score labels are created. The textbox for the game instruction text is also
		 * created.
		 */
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);

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
			System.out.println("You input an invalid location for inserting a string into the instructions JTextPane");
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

		// Add the marble count display box to the panel
		this.add(marbleCountDisplayLabel);
		this.add(marbleCountLabel);

		resetBoardGraphics();

		KeyListener cheatListener = new KeyListener() {
			public void keyPressed(KeyEvent e) {
				String keyPressed = KeyEvent.getKeyText(e.getKeyCode());
				// Remove the first recorded key if longer than 'cheat'
				if (keysTyped.size() >= 5) {
					keysTyped.remove(0);
				}
				// Add the pressed key to the list of pressed keys
				keysTyped.add(keyPressed);

				// Adds an extra miss is the word 'cheat' has been typed
				String cheatWinArray = "[W, I, N, P, 1]";
				if (keysTyped.toString().equals(cheatWinArray)) {
					for (int i = 0; i < 6; i++) {
						Pit pit = game.getStoreList().get(i);
						for (Marble marble : pit.getMarbleList()) {
							Pit store = game.getStoreList().get(6);
							store.getMarbleList().add(marble);
						}
					}
					for (int i = 0; i < 6; i++) {
						Pit pit = game.getStoreList().get(i);
						pit.getMarbleList().clear();
					}
				}

				String cheatLoseArray = "[W, I, N, P, 2]";
				if (keysTyped.toString().equals(cheatLoseArray)) {
					for (int i = 0; i < 6; i++) {
						Pit pit = game.getStoreList().get(i);
						for (Marble marble : pit.getMarbleList()) {
							Pit store = game.getStoreList().get(13);
							store.getMarbleList().add(marble);
						}
					}
					for (int i = 0; i < 6; i++) {
						Pit pit = game.getStoreList().get(i);
						pit.getMarbleList().clear();
					}
				}

				if (game.hasWinner()) {
					game.setWinner();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		};
		addKeyListener(cheatListener);
		this.setFocusable(true);
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
			System.out.println("An specified image file cannot be found");
		} catch (IOException e) {
			e.printStackTrace();
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
				pitButton.setBounds(125 + (84 * i), 245 - (i), 73, 74);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				pitButton.setFocusable(false);
				this.add(pitButton);
			} else if (i < 6) {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setBounds(427 + (83 * (i - 3)), 250 - (2 * i), 71, 70);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				pitButton.setFocusable(false);
				this.add(pitButton);
			} else if (i == 6) {
				// This is a rectangular button instead of a circular button
				// This button still needs rotated for greater precision
				RoundButton pitButton = new RoundButton(i);
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
				pitButton.setFocusable(false);
				this.add(pitButton);
			} else if (i < 13) {
				RoundButton pitButton = new RoundButton(i);
				pitButton.setBounds(294 - (-81 * (10 - i)), 135 + (-1 * (10 - i)), 70, 70);
				currentPit.setBoundary(pitButton);
				pitButton.setBorderPainted(false);
				addButtonListeners(pitButton);
				pitButton.setFocusable(false);
				this.add(pitButton);
			} else {
				RoundButton pitButton = new RoundButton(i);
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

		repaint();
	}

	/**
	 * Moves all of the marble images in a specified pit to the following pits.
	 * 
	 * @param selectedPitIndex an integer designating the index (within the game's
	 *                         list of pits) of the pit to be moved
	 * @return the boolean true if the marbles images from the designated pit are
	 *         moved successfully; false if there are no marbles in the pit to move
	 */
	public int movePit(int selectedPitIndex) {
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
			if ((nextPitIndex == 13 && game.getCurrentPlayer() == 0) || 
					(nextPitIndex == 6 && game.getCurrentPlayer() == 1)) {
				nextPitIndex = nextPitIndex + 1;
			}
			
			// If the player selects a pit that places marbles beyond the end of the marble
			// list, the marble will be placed into the pits at the beginning of the list
			if (nextPitIndex > 13) {
				nextPitIndex = Math.abs(13 - (nextPitIndex - 1));
			}
			System.out.println("Next Pit: " + nextPitIndex);
			// Changes the coordinates of each marble in the selected pit to a random
			// coordinate within the pit the marble is being moved to
			Pit nextPit = game.getStoreList().get(nextPitIndex);
			marble.setXcord(rand.nextInt(((nextPit.getBoundary().getBounds().x + nextPit.getBoundary().getBounds().width
					- marble.getMarbleImage().getWidth(getFocusCycleRootAncestor()) - 5))
					- (nextPit.getBoundary().getBounds().x + 5)) + (nextPit.getBoundary().getBounds().x + 5));
			marble.setYcord(
					rand.nextInt(((nextPit.getBoundary().getBounds().y + nextPit.getBoundary().getBounds().height
							- marble.getMarbleImage().getHeight(getFocusCycleRootAncestor()) - 5))
							- (nextPit.getBoundary().getBounds().y + 5)) + (nextPit.getBoundary().getBounds().y + 5));
		}
		
		int endPitIndex = nextPitIndex;
		System.out.println("End Pit Index: " + endPitIndex);
		
		game.move(selectedPitIndex);
		
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
		capturingMarble.setXcord(rand.nextInt(((store.getBoundary().getBounds().x + store.getBoundary().getBounds().width
				- capturingMarble.getMarbleImage().getWidth(getFocusCycleRootAncestor()) - 5))
				- (store.getBoundary().getBounds().x + 5)) + (store.getBoundary().getBounds().x + 5));
		capturingMarble.setYcord(rand.nextInt(((store.getBoundary().getBounds().y + store.getBoundary().getBounds().height
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
			instructionsPane.setText("Player 2, it's your turn.\nChoose a pit.");
		} else {
			instructionsPane.setText("Player 1, it's your turn.\nChoose a pit.");
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
	 * Adds a MouseListener to a button. This is used for the pit buttons to
	 * indicate when which pit has been clicked on, as well as specifying a range of
	 * coordinates for a particular pit.
	 * 
	 * @param pitButton a JButton object that specifies which button the mouse
	 *                  listener should be added to
	 */
	public void addButtonListeners(JButton pitButton) {
		MouseListener buttonListener = new MouseListener() {
			/*
			 * Specifies the actions taken when a pit button is clicked. The button must lie
			 * on the current player's side for the click to have an impact. If a pit button
			 * on the current player's side is clicked, the marbles in the clicked pit are
			 * moved as necessary. The panel is then repainted to show the updated positions
			 * of the moved marbles.
			 */
			public void mouseClicked(MouseEvent e) {
				// On a mouse click, the marbles are moved and the player is changed
				RoundButton buttonClicked = (RoundButton) e.getSource();
				Pit currentPit = game.getStoreList().get(buttonClicked.getPitNumber());
				int selectedPitIndex = buttonClicked.getPitNumber();

				// Only allows player to choose a pit on their side of the board
				if (currentPit.getSide() == game.getCurrentPlayer()) {
					// After a player chooses a pit, play moves to the other player
					boolean getsAnotherTurn = game.getsAnotherMove(selectedPitIndex);
					int endPitIndex = movePit(selectedPitIndex);
					if (endPitIndex != selectedPitIndex) {
						if (game.checkCapture(endPitIndex)) {
							moveCapturedMarbles(endPitIndex);
							game.moveCapturedMarbles(endPitIndex);
						}
						if (!getsAnotherTurn) {
							game.switchPlayer();
						}
						changeInstructionText(getsAnotherTurn);
					}

					if (game.hasWinner()) {
						game.setWinner();
					}
					repaint();
				}
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
	}
