package mancala;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

/**
 * Contains all information necessary for a pit in the Mancala game, including
 * the marbles contained within and the side of the board it lies on.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
public class Pit {
	private List<Marble> marblesContained = new ArrayList<>();
	private int boardSide;

	private JButton pitBoundary;

	/**
	 * Creates a new pit for the Mancala game.
	 * 
	 * @param marbleCount an integer detailing the number of marbles to be placed
	 *                    into the pit
	 * @param boardSide   an integer detailing the side of the board the pit is on
	 *                    (0 for Player 1, 1 for Player 2)
	 */
	public Pit(int marbleCount, int boardSide) {
		this.boardSide = boardSide;
		for (int i = 0; i < marbleCount; i++) {
			addMarble(new Marble());
		}
	}

	/**
	 * Creates a new pit for the Mancala game. Without specifying the number of
	 * marbles to be placed into the pit, the default marble count is 4.
	 * 
	 * @param boardSide an integer detailing the side of the board the pit is on (0
	 *                  for Player 1, 1 for Player 2)
	 */
	public Pit(int boardSide) {
		this.boardSide = boardSide;

		for (int i = 0; i < 4; i++) {
			addMarble(new Marble());
		}
	}

	/**
	 * Removes the specified marble from the pit.
	 * 
	 * @param marble the specified marble object to be removed from the pit
	 */
	public void removeMarble(Marble marble) {
		marblesContained.remove(marble);
	}

	/**
	 * Takes the list of marbles contained by the pit and turns it into an array.
	 * 
	 * @return arr an array of Marble objects
	 */
	public Marble[] toArray() {
		Marble[] arr = new Marble[getMarbleList().size()];
		for (int i = 0; i < getMarbleList().size(); i++) {
			arr[i] = marblesContained.get(i);
		}
		return arr;
	}

	/**
	 * Returns an integer representing the side of the board the pit belongs to.
	 * 
	 * @return int returns 0 if the pit is on Player 1's side and 1 if the pit is on
	 *         Player 2's side
	 */
	public int getSide() {
		return boardSide;
	}

	/**
	 * Returns a list of all Marble objects held by the pit.
	 * 
	 * @return List<Marble> returns a list of all Marble objects held by the pit
	 */
	public List<Marble> getMarbleList() {
		return marblesContained;
	}

	/**
	 * Sets a JButton to the pit.
	 * 
	 * @param pitBoundary sets a JButton to the pit
	 */
	public void setBoundary(JButton pitBoundary) {
		/*
		 * The marbles within the pit are drawn within the coordinates of this JButton.
		 */
		this.pitBoundary = pitBoundary;
	}

	/**
	 * Returns the JButton belonging to the pit.
	 * 
	 * @return JButton returns the JButton belonging to the pit
	 */
	public JButton getBoundary() {
		return pitBoundary;
	}

	/**
	 * Adds a Marble object to the pit.
	 * 
	 * @param marble the Marble object to be added to the list
	 */
	public void addMarble(Marble marble) {
		marblesContained.add(marble);
	}

	public String toString() {
		return String.valueOf(marblesContained.size());
	}

}
