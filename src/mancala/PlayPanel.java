package mancala;

import java.awt.Cursor;
import java.awt.Dimension;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @title PlayPanel.java
 * @author Ryan Johnson, Hank Rugg
 * @description Contains the graphics for the gameplay of Mancala. The game
 *              begins with four marbles in each pit of the board (excluding the
 *              two stores). The user has the option to choose from which pit
 *              they move the marbles, which are moved to the subsequent pits.
 */
public class PlayPanel extends JPanel implements MouseListener {

	private Game game;
	private Random rand = new Random();

	public PlayPanel() {
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(null);
		addMouseListener(this);

		resetBoard();
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

	public void addButtonListeners(JButton pitButton) {
		MouseListener buttonListener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				RoundButton buttonClicked = (RoundButton) e.getSource();
				int selectedPitIndex = buttonClicked.getPitNumber();

				if (movePit(selectedPitIndex)) {
					game.switchPlayer();
				}
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		};

		pitButton.addMouseListener(buttonListener);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * @description Changes the shape of the cursor to a hand when a user hovers
	 *              over a pit
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * @description Changes the shape of the cursor to the default arrow when a user
	 *              stops hovering over a pit
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
