package mancala;

import java.util.ArrayList;

import java.util.List;

import javax.swing.JButton;

/**
 * This class creates the pits that are used to store marbles in Mancala. 
 * Each pit has a side of the board it is on, and the number of marbles in the pit. 
 * 
 * 
 * @author hankrugg
 */

public class Pit {
	private List<Marble> marblesContained = new ArrayList<>();
	private int boardSide;

	private JButton pitBoundary;

	/**
	 * This is a the constructor for the pit class, it initializes the pit with 
	 * the amount of marbles specified in marbleCount, and sets the board side of the pit
	 * with boardSide. 
	 * 
	 * @param marbleCount is the amount of marbles
	 * @param boardSide is the side of the board
	 */
	public Pit(int marbleCount, int boardSide) {
		this.boardSide = boardSide;
		for (int i = 0; i < marbleCount; i++) {
			addMarble(new Marble());
		}
	}
	/**
	 * Constructor for a pit. This takes only the board side as a parameter.
	 * 
	 * @param boardSide is the side of the board
	 */
	public Pit(int boardSide) {
		this.boardSide = boardSide;

		for (int i = 0; i < 4; i++) {
			addMarble(new Marble());
		}
	}

	/**
	 * Returns the side of the board the pit is on. 
	 * 
	 * @return the side of the board the pit is on
	 */
	public int getSide() {
		return boardSide;
	}

	/**
	 * Returns a list of the marbles contained in the pit.
	 * 
	 * @return a list of the marbles contained in the pit
	 */
	public List<Marble> getMarbleList() {
		return marblesContained;
	}

	/**
	 * Sets the boundary for the pit button. Takes the pit boundary
	 * as a parameter.
	 * 
	 * @param pitBoundary JButton that represents the pit
	 */
	public void setBoundary(JButton pitBoundary) {
		this.pitBoundary = pitBoundary;
	}

	/**
	 * Returns the pit boundary in the form of a JButton.
	 * 
	 * @param pitBoundary JButton that represents the pit
	 */
	public JButton getBoundary() {
		return pitBoundary;
	}

	/**
	 * Adds a marble to the pit. Takes a marble as a parameter.
	 * 
	 * @param marble that is going to be added
	 */
	public void addMarble(Marble marble) {
		marblesContained.add(marble);
	}

	/**
	 * This method removes the specified marble from the pit
	 * 
	 * @param marble that is going to be removed
	 */
	public void removeMarble(Marble marble) {
		marblesContained.remove(marble);
	}

	/**
	 * To string method which returns the amount of marbles in the pit.
	 * 
	 * @return String of amount of marbles in the pit
	 */
	public String toString() {
		return String.valueOf(marblesContained.size());
	}

	/**
	 * This to array method takes the array list of marbles we have and turns it
	 * into an array
	 * 
	 * @return an array of Marbles
	 */
	public Marble[] toArray() {
		Marble[] arr = new Marble[getMarbleList().size()];
		for (int i = 0; i < getMarbleList().size(); i++) {
			arr[i] = marblesContained.get(i);
		}
		return arr;
	}

}
