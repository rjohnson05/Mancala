package mancala;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public class Pit {
	private List<Marble> marblesContained = new ArrayList<>();
	private int boardSide;

	private JButton pitBoundary;

	/**
	 * This is a the constructor for the pit class
	 * 
	 * @param marbleCount
	 * @param boardSide
	 */
	public Pit(int marbleCount, int boardSide) {
		this.boardSide = boardSide;
		for (int i = 0; i < marbleCount; i++) {
			addMarble(new Marble());
		}
	}

	public Pit(int boardSide) {
		this.boardSide = boardSide;

		for (int i = 0; i < 4; i++) {
			addMarble(new Marble());
		}
	}

	/**
	 * 
	 * 
	 * @return the side of the board the pit is on
	 */
	public int getSide() {
		return boardSide;
	}

	/**
	 * 
	 * @return a list of the marbles contained in the pit
	 */
	public List<Marble> getMarbleList() {
		return marblesContained;
	}

	public void setBoundary(JButton pitBoundary) {
		this.pitBoundary = pitBoundary;
	}

	public JButton getBoundary() {
		return pitBoundary;
	}

	public void addMarble(Marble marble) {
		marblesContained.add(marble);
	}

	/**
	 * This method removes the specified marble from the pit
	 * 
	 * @param marble
	 */
	public void removeMarble(Marble marble) {
		marblesContained.remove(marble);
	}

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
