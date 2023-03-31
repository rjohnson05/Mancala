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

	private RoundButton pitBoundary;

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

	public int getSide() {
		return boardSide;
	}

	public List<Marble> getMarbleList() {
		return marblesContained;
	}

	public void setBoundary(RoundButton pitButton) {
		this.pitBoundary = pitButton;
	}

	public RoundButton getBoundary() {
		return pitBoundary;
	}

	public void addMarble(Marble marble) {
		marblesContained.add(marble);
	}

	public String toString() {
		return String.valueOf(marblesContained.size());
	}

}
